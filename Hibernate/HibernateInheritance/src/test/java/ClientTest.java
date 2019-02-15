import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Test;
import pojos.*;
import util.EMFactory;
import util.SFactory;

import javax.persistence.Embedded;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientTest {

    @Test
    public void saveClientEmbeddedTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        Client testClient = new Client("Bobby", "Lee", "+375295674321", new Address("Minsk", "Main", "52B", "121"), LocalDate.of(2001, 04, 25), "moonLoght", "notRepeat");
        Client testClient2 = new Client("Alan", "Walker", "+375447654321", new Address("Los Soligorsk", "Shaxterov", "1", "52"), LocalDate.of(1976, 01, 21), "login7", "password8");
        Client testClient3 = new Client("Jack", "Nikolson", "+375297162534", new Address("Svetlcity", "Molodejnui", "23", "21"), LocalDate.now(), "login77", "password88");
        entityManager.getTransaction().begin();
        entityManager.persist(testClient);
        entityManager.persist(testClient2);
        entityManager.persist(testClient3);
        entityManager.getTransaction().commit();
        Client loadedClient = entityManager.find(Client.class, testClient.getClientID());
        Assert.assertEquals(testClient, loadedClient);
        entityManager.close();
    }

    @Test
    public void saveIheritanceTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        GoldClient testClient = new GoldClient("Bobby", "Lee", "+375295674321", new Address("Minsk", "Main", "52B", "121"), LocalDate.of(2001, 04, 25), "moonLoght", "notRepeat", true, false);
        GoldClient testClient2 = new GoldClient("Alan", "Walker", "+375447654321", new Address("Los Soligorsk", "Shaxterov", "1", "52"), LocalDate.of(1976, 01, 21), "login7", "password8", true, true);
        PrivilegedClient testClient3 = new PrivilegedClient("Jack", "Nikolson", "+375297162534", new Address("Svetlcity", "Molodejnui", "23", "21"), LocalDate.now(), "login77", "password88", false);
        List<GoldClient> list = Arrays.asList(testClient, testClient2);
        entityManager.getTransaction().begin();
        entityManager.persist(testClient);
        entityManager.persist(testClient2);
        entityManager.persist(testClient3);
        entityManager.getTransaction().commit();
        PrivilegedClient clientFromDb = new PrivilegedClient();
        entityManager.getTransaction().begin();
        clientFromDb = entityManager.find(PrivilegedClient.class, 3l);
        entityManager.close();
        Assert.assertEquals("Jack", clientFromDb.getFirstName());
    }

    @Test
    public void readInheritanceTest() {
        GoldClient testClient = new GoldClient("Bobby", "Lee", "+375295674321", new Address("Minsk", "Main", "52B", "121"), LocalDate.of(2001, 04, 25), "moonLoght", "notRepeat", true, false);
        GoldClient testClient2 = new GoldClient("Alan", "Walker", "+375447654321", new Address("Los Soligorsk", "Shaxterov", "1", "52"), LocalDate.of(1976, 01, 21), "login7", "password8", true, true);
        Session session = SFactory.getSession();
        Transaction tx = session.beginTransaction();
        session.save(testClient);
        session.save(testClient2);
        tx.commit();
        tx.begin();
        List clients = session.createQuery("FROM GoldClient ").list();
        tx.commit();
        session.close();
        Assert.assertEquals(2, clients.size());
        System.out.println(clients);
    }

    @Test
    public void updateInheritanceTest() {
        PrivilegedClient testClient3 = new PrivilegedClient("Jack", "Nikolson", "+375297162534", new Address("Svetlcity", "Molodejnui", "23", "21"), LocalDate.now(), "login77", "password88", false);
        Session session = SFactory.getSession();
        Transaction tx = session.beginTransaction();
        session.save(testClient3);
        tx.commit();
        tx.begin();
        testClient3.setLastName("Rubinovich");
        session.update(testClient3);
        tx.commit();
        tx.begin();
        PrivilegedClient clientFromDb = session.load(PrivilegedClient.class, testClient3.getClientID());
        tx.commit();
        Assert.assertEquals("Rubinovich",clientFromDb.getLastName());
    }

}
