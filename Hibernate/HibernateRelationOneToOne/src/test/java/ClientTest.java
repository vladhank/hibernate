import org.junit.Assert;
import org.junit.Test;
import pojos.Address;
import pojos.Client;
import util.EMFactory;

import javax.persistence.EntityManager;
import java.time.LocalDate;

public class ClientTest {
    @Test
    public void clientSaveTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        Client testClient = new Client("Bobby", "Lee", "+375295674321", new Address("Minsk", "Main", "52B", "121"), LocalDate.of(2001, 04, 25), "moonLoght", "notRepeat");
        Client testClient2 = new Client("Alan", "Walker", "+375447654321", new Address("Los Soligorsk", "Shaxterov", "1", "52"), LocalDate.of(1976, 01, 21), "login7", "password8");
        Client testClient3 = new Client("Jack", "Nikolson", "+375297162534", new Address("Svetlcity", "Molodejnui", "23", "21"), LocalDate.now(), "login77", "password88");
        entityManager.getTransaction().begin();
        entityManager.persist(testClient);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        Client clientFromDB = entityManager.find(Client.class, 1L);
        entityManager.getTransaction().commit();
        Assert.assertEquals(testClient, clientFromDB);
    }

}
