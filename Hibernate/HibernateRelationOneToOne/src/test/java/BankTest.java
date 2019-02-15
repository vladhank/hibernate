import org.hibernate.HibernateException;
import org.junit.Assert;
import org.junit.Test;
import pojos.Address;
import pojos.Bank;
import pojos.Client;
import util.EMFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.sql.SQLException;
import java.time.LocalDate;

public class BankTest {

    @Test
    public void bankAccountSaveTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        Client testClient = new Client("Bobby", "Lee", "+375295674321", new Address("Minsk", "Main", "52B", "121"), LocalDate.of(2001, 04, 25), "moonLoght", "notRepeat");
        Bank testBank = new Bank("BNDAASFASFASFAGASFASDSAFAS", testClient, 5800);
        entityManager.getTransaction().begin();
        entityManager.persist(testClient);
        entityManager.persist(testBank);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        Client clientFromDB = entityManager.find(Client.class, 1L);
        entityManager.getTransaction().commit();
        entityManager.close();
        System.out.println(clientFromDB);
    }

    @Test
    public void bankAccountDeleteTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        Client testClient = new Client("Bobby", "Lee", "+375295674321", new Address("Minsk", "Main", "52B", "121"), LocalDate.of(2001, 04, 25), "moonLoght", "notRepeat");
        Client testClient2 = new Client("Alan", "Walker", "+375447654321", new Address("Los Soligorsk", "Shaxterov", "1", "52"), LocalDate.of(1976, 01, 21), "login7", "password8");
        Client testClient3 = new Client("Jack", "Nikolson", "+375297162534", new Address("Svetlcity", "Molodejnui", "23", "21"), LocalDate.now(), "login77", "password88");
        Bank testBankAccount = new Bank("BNDAASFASFASFAGASFASDSAFAS", testClient, 5800);
        Bank test2BankAccount = new Bank("BADSAFASFNXVMX2423DFSF23423", testClient2, 4600);
        entityManager.getTransaction().begin();
        entityManager.persist(testClient);
        entityManager.persist(testClient2);
        entityManager.persist(testClient3);
        entityManager.persist(testBankAccount);
        entityManager.persist(test2BankAccount);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        entityManager.remove(testBankAccount);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        Bank accountFromDb = entityManager.find(Bank.class, 1L);
        Client clientFromDb = entityManager.find(Client.class, 1L);
        entityManager.getTransaction().commit();
        entityManager.close();
        Assert.assertNull(accountFromDb);
        Assert.assertNull(clientFromDb);
        System.out.println(testClient2);
    }

    @Test
    public void bankAccountUpdateTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        Client testClient = new Client("Bobby", "Lee", "+375295674321", new Address("Minsk", "Main", "52B", "121"), LocalDate.of(2001, 04, 25), "moonLoght", "notRepeat");
        Bank testBank = new Bank("BNDAASFASFASFAGASFASDSAFAS", testClient, 5800);
        entityManager.getTransaction().begin();
        entityManager.persist(testClient);
        entityManager.persist(testBank);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        testBank.setBalance(9700);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        Bank accountFromDb=entityManager.find(Bank.class,1L);
        entityManager.getTransaction().commit();
        entityManager.close();
        Assert.assertEquals(testBank,accountFromDb);
    }

    @Test(expected = PersistenceException.class)
    public void bankAccountNullableTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        Client testClient = new Client("Bobby", "Lee", "+375295674321", new Address("Minsk", "Main", "52B", "121"), LocalDate.of(2001, 04, 25), "moonLoght", "notRepeat");
        Bank testBank = new Bank("BNDAASFASFASFAGASFASDSAFAS", null, 5800);
        entityManager.getTransaction().begin();
        entityManager.persist(testClient);
        entityManager.persist(testBank);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
