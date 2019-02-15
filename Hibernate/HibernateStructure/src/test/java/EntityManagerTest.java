import org.junit.Assert;
import org.junit.Test;
import pojos.Client;
import sun.misc.Cleaner;
import util.EMFactory;

import javax.persistence.EntityManager;
import java.time.LocalDate;

public class EntityManagerTest {

    @Test
    public void saveTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        entityManager.getTransaction().begin();
        Client client = new Client(3l, "Vadim", "Motulev", "+375259999999", "Miroshnichenko 4", LocalDate.now(), "kolos", "password");
        entityManager.persist(client);
        entityManager.getTransaction().commit();
        entityManager.clear();

        entityManager.getTransaction().begin();
        Client clientFromDB = entityManager.find(Client.class, client.getClientID());
        Assert.assertEquals(client, clientFromDB);
    }

}
