import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pojos.*;
import util.EMFactory;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionsTest {
    @Test
    public void initTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        Client testClient = new Client("Bobby", "Lee", "+375295674321", new Address("Minsk", "Main", "52B", "121"), LocalDate.of(2001, 04, 25), "moonLoght", "notRepeat", null);
        Client testClient2 = new Client("Alan", "Walker", "+375447654321", new Address("Los Soligorsk", "Shaxterov", "1", "52"), LocalDate.of(1976, 01, 21), "login7", "password8", null);
        Client testClient3 = new Client("Jack", "Nikolson", "+375297162534", new Address("Svetlcity", "Molodejnui", "23", "21"), LocalDate.now(), "login77", "password88", null);
        Client testClient4 = new Client("Jack", "Morris", "+375445533215", new Address("Polock", "Efrasini", "54", "43B"), LocalDate.of(1985, 8, 05), "mbvfsa", "mcxv42sdf", null);
        Bank bankAccount = new Bank("BAFNSDF2421MASFASAFASF87", testClient, 9900);
        Bank bankAccount2 = new Bank("BCASDA977ASFA123VS", testClient4, 98500);
        CreditCard creditCard = new CreditCard(5643890743210091L, "MASTERCARD", "Jojo", "Pojo", LocalDate.of(2022, 07, 23), 333, 5555, 7, "ACTIVE", bankAccount, null, testClient);
        CreditCard creditCard2 = new CreditCard(4322558700884302L, "VISA", "Alan", "Walker", LocalDate.of(2025, 11, 15), 652, 8218, 0, "ACTIVE", bankAccount, null, testClient);
        CreditCard creditCard3 = new CreditCard(4531775344009622L, "VISA", "Andrew", "Makarevich", LocalDate.of(2043, 02, LocalDate.now().getDayOfMonth()), 321, 5421, 8, "ACTIVE", bankAccount2, null, testClient4);
        Transaction transaction = new Transaction(4531775344009622L, "DEPOSIT", 2330, LocalDateTime.now(), creditCard3);
        Transaction transaction2 = new Transaction(4531775344009622L, "WITHDRAW", 430, LocalDateTime.now(), creditCard3);
        entityManager.getTransaction().begin();
        entityManager.persist(testClient);
        entityManager.persist(testClient2);
        entityManager.persist(testClient3);
        entityManager.persist(testClient4);
        entityManager.persist(creditCard);
        entityManager.persist(creditCard2);
        entityManager.persist(creditCard3);
        entityManager.persist(bankAccount);
        entityManager.persist(bankAccount2);
        entityManager.persist(transaction);
        entityManager.persist(transaction2);
        creditCard3.setTransactionList(new ArrayList<>());
        creditCard3.getTransactionList().add(transaction);
        creditCard3.getTransactionList().add(transaction2);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        entityManager.persist(creditCard3);
        List<Client> list;
        Client clientFromDb = entityManager.find(Client.class, 1L);
        list = entityManager.createQuery("FROM Client ").getResultList();
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        testClient.setFirstName("Johny");
        entityManager.getTransaction().commit();
        entityManager.close();
        Assert.assertEquals(4, list.size());
        Assert.assertNotNull(clientFromDb);
        Assert.assertEquals(testClient, clientFromDb);
        list.forEach(System.out::println);
    }


    @Test
    public void optimisticLockVersionTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        entityManager.getTransaction().begin();
        Client client = entityManager.find(Client.class, 3L);
        client.setFirstName("Ulla");
        entityManager.getTransaction().commit();
        Query query = entityManager.createQuery("select c.version FROM Client c  WHERE c.clientID =:id");
        query.setParameter("id", 3L);
        Integer version = ( Integer ) query.getSingleResult();
        Assert.assertNotEquals(java.util.Optional.of(0), client.getVersion());
        System.out.println(version);
        entityManager.close();
    }

    @Test
    public void optimisticLockAllTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        entityManager.getTransaction().begin();
        Client clientFromDb = entityManager.find(Client.class, 1L);
        Bank bankFromDb = entityManager.find(Bank.class, "BAFNSDF2421MASFASAFASF87");
        Query query = entityManager.createQuery("from Bank where client.clientID=:id");
        query.setParameter("id", clientFromDb.getClientID());
        Bank bank = ( Bank ) query.getSingleResult();
        clientFromDb.getCards().add(new CreditCard(5241995201864211L, "MASTERCARD", "Elvis", "Presley", LocalDate.of(2024, 11, 12), 752, 0073, 3, "ACTIVE", bank, null, clientFromDb));
        bankFromDb.setBalance(111000);
        entityManager.getTransaction().commit();
        Assert.assertEquals(clientFromDb, entityManager.find(Client.class, clientFromDb.getClientID()));
        Assert.assertEquals(bankFromDb, entityManager.find(Bank.class, bankFromDb.getBankAccount()));
        entityManager.close();
    }

    @Test
    public void optimisticLockDirtyTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        entityManager.getTransaction().begin();
        Transaction testTransaction = entityManager.find(Transaction.class, 2L);
        testTransaction.setTransactionType("WITHDRAW");
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void optimisticLockTest() throws InterruptedException {
        EntityManager entityManager = EMFactory.getEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("from CreditCard where cashBack=:cashback and cardCompany=:cardcompany");
        query.setParameter("cardcompany", "VISA");
        query.setParameter("cashback", 8);
        CreditCard creditCard = ( CreditCard ) query.getSingleResult();
        entityManager.refresh(creditCard, LockModeType.OPTIMISTIC);
        creditCard.setCashBack(11);
        new Thread(() -> {
            EntityManager entityManagerThread = EMFactory.getEntityManager();
            entityManagerThread.getTransaction().begin();
            CreditCard creditCard1 = entityManager.find(CreditCard.class, 4531775344009622L);
            creditCard1.setCardCompany("VISA");
            entityManagerThread.getTransaction().commit();
        }).start();
        Thread.sleep(500);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void optimisticForceIncrementTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        entityManager.getTransaction().begin();
        Client client = entityManager.find(Client.class, 3L);
//        Client client = entityManager.find(Client.class,3L,LockModeType.WRITE);
        int firstVersion = client.getVersion();
        entityManager.lock(client, LockModeType.WRITE);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        int secondVersion = entityManager.find(Client.class, 3L).getVersion();
        entityManager.getTransaction().commit();
        Assert.assertNotEquals(firstVersion, secondVersion);
    }

    @Test(expected = PessimisticLockException.class)
    public void pessimisticLockModeTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        entityManager.getTransaction().begin();
        Transaction transaction = entityManager.find(Transaction.class,1L,LockModeType.PESSIMISTIC_WRITE);
        Transaction transaction1 = entityManager.find(Transaction.class,1L);
        transaction1.setAmountMoney(717);
        entityManager.getTransaction().commit();
    }

    @Test
    public void queryLockTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("from CreditCard where cashBack=:cashback");
        query.setParameter("cashback", 3);
        query.setLockMode(LockModeType.WRITE);
        CreditCard creditCard = ( CreditCard ) query.getSingleResult();
        int firstVersion = creditCard.getVersion();
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        int secondVersion = entityManager.find(CreditCard.class,creditCard.getCardNumber()).getVersion();
        Assert.assertNotEquals(firstVersion,secondVersion);
    }
}
