import org.junit.Assert;
import org.junit.Test;
import pojos.Client;
import util.HibernateUtil;

import javax.persistence.EntityManager;
import java.time.LocalDate;

public class ClientTest {

    @Test
    public void createTest() {
        Client testClient = new Client("Bobby", "Lee", "+375295674321", "Main street", LocalDate.of(2001, 04, 25), "moonLoght", "notRepeat");
        Client testClient2 = new Client("Alan", "Walker", "+375447654321", "Street", LocalDate.now(), "login7", "password8");
        Client testClient3 = new Client("Jack", "Nikolson", "+375297162534", "Hollywood", LocalDate.now(), "login77", "password88");

        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(testClient);
            entityManager.persist(testClient2);
            entityManager.persist(testClient3);

            entityManager.getTransaction().commit();
        } catch (Throwable ex) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
        entityManager = HibernateUtil.getEntityManager();
        Client newClient = entityManager.find(Client.class, 1L);
        Assert.assertEquals("Bobby", newClient.getFirstName());
    }

    @Test
    public void readTest() {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        Client client = entityManager.find(Client.class, 1L);
        Assert.assertNull(client);
        Client testClient = new Client("Bobby", "Lee", "+375295674321", "Main street", LocalDate.of(2001, 04, 25), "moonLoght", "notRepeat");
        entityManager.getTransaction().begin();
        entityManager.persist(testClient);
        entityManager.getTransaction().commit();
        client = entityManager.find(Client.class, 1L);
        Assert.assertNotNull(client);
    }

    @Test
    public void deleteTest() {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        Client client2 = new Client("Jack", "Nikolson", "+375297162534", "Hollywood", LocalDate.now(), "login77", "password88");
        entityManager.getTransaction().begin();
        entityManager.remove(client2);
        entityManager.getTransaction().commit();
    }

    @Test
    public void update() {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        Client client;
        entityManager.getTransaction().begin();
        client= entityManager.find(Client.class,1l);
        client.setLastName("Speisi");
        entityManager.getTransaction().commit();
    }
}
