import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Test;
import pojos.Client;
import util.EMFactory;
import util.SFactory;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

public class ClientTest {

    @Test
    public void saveSessionTest() {
        Client testClient = new Client("Bobby", "Lee", "+375295674321", "Main street", LocalDate.of(2001, 04, 25), "moonLoght", "notRepeat");
        Client testClient2 = new Client("Alan", "Walker", "+375447654321", "Street", LocalDate.of(1976, 01, 21), "login7", "password8");
        Client testClient3 = new Client("Jack", "Nikolson", "+375297162534", "Hollywood", LocalDate.now(), "login77", "password88");
        Session session = SFactory.getSession();
        Transaction tx = session.beginTransaction();
        session.save(testClient);
        session.save(testClient2);
        session.save(testClient3);
        tx.commit();
        tx.begin();
        List clients = session.createQuery("FROM Client ").list();
        tx.commit();
        session.close();
        Assert.assertEquals(3, clients.size());
    }

    @Test
    public void saveEmTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        Client client = new Client("Vadim", "Motulev", "+375259999999", "Miroshnichenko 4", LocalDate.now(), "kolos", "lololop");
        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();
        entityManager.clear();
        entityManager.getTransaction().begin();
        Client clientFromDb = entityManager.find(Client.class,client.getClientID());
        entityManager.getTransaction().commit();
        entityManager.close();
        System.out.println(clientFromDb);
    }


    @Test
    public void updateEmTest(){
        EntityManager entityManager = EMFactory.getEntityManager();
        entityManager.getTransaction().begin();
        Client client = entityManager.find(Client.class,1L);
        entityManager.getTransaction().commit();
        String nameBeforeUpdate = client.getFirstName();
        entityManager.getTransaction().begin();
        client.setFirstName("Tommy");
        client.setLastName("Richards");
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        Client updatedClient = entityManager.find(Client.class,1L);
        entityManager.getTransaction().commit();
        entityManager.close();
        Assert.assertNotEquals(nameBeforeUpdate,updatedClient.getFirstName());
    }

    @Test
    public void deleteTest(){
        Session session = SFactory.getSession();
        Transaction tx = session.beginTransaction();
        Client deletedClient = new Client( "Sergei", "Minaev", "+375446567732", "Vaneeva 15", LocalDate.of(1973, 12, 21), "jokdsa", "gbvc67s");
        session.save(deletedClient);
        tx.commit();
        tx.begin();
        session.delete(deletedClient);
        tx.commit();
        session.close();
    }

}
