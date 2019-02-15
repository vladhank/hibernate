import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;
import pojos.*;
import pojos.enums.CardType;
import util.EMFactory;
import util.SFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CacheTest {
    @Test
    public void initTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        Client testClient = new Client("Bobby", "Lee", "+375295674321", new Address("Minsk", "Main", "52B", "121"), LocalDate.of(2001, 04, 25), "moonLoght", "notRepeat", null);
        Client testClient2 = new Client("Alan", "Walker", "+375447654321", new Address("Los Soligorsk", "Shaxterov", "1", "52"), LocalDate.of(1976, 01, 21), "login7", "password8", null);
        Client testClient3 = new Client("Jack", "Nikolson", "+375297162534", new Address("Svetlcity", "Molodejnui", "23", "21"), LocalDate.now(), "login77", "password88", null);
        Client testClient4 = new Client("Jack", "Morris", "+375445533215", new Address("Polock", "Efrasini", "54", "43B"), LocalDate.of(1985, 8, 05), "mbvfsa", "mcxv42sdf", null);
        Bank bankAccount = new Bank("BAFNSDF2421MASFASAFASF87", testClient, 9900, new ArrayList<>());
        Bank bankAccount2 = new Bank("BCASDA977ASFA123VS", testClient4, 98500, new ArrayList<>());
        CreditCard creditCard = new CreditCard(5643890743210091L, CardType.MASTERCARD, "Jojo", "Pojo", LocalDate.of(2018, 07, 23), 333, 5555, 7, "ACTIVE", bankAccount, null, testClient);
        CreditCard creditCard2 = new CreditCard(4322558700884302L, CardType.VISA, "Alan", "Walker", LocalDate.of(2025, 11, 15), 652, 8218, 0, "ACTIVE", bankAccount, null, testClient);
        CreditCard creditCard3 = new CreditCard(4531775344009622L, CardType.VISA, "Andrew", "Makarevich", LocalDate.of(2043, 02, LocalDate.now().getDayOfMonth()), 321, 5421, 8, "ACTIVE", bankAccount2, null, testClient4);
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
        bankAccount.getCardList().add(creditCard);
        bankAccount.getCardList().add(creditCard2);
        bankAccount2.getCardList().add(creditCard3);
        entityManager.persist(bankAccount);
        entityManager.persist(bankAccount2);
        entityManager.persist(transaction);
        entityManager.persist(transaction2);
        entityManager.flush();
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
    public void sessionCacheTest() {
//        Session session = SFactory.getSession();
        EntityManager entityManager = EMFactory.getEntityManager();
        Session session = entityManager.unwrap(Session.class);
        org.hibernate.Transaction tx = session.beginTransaction();
        Client clientFromDb = session.get(Client.class, 1l);
        tx.commit();
        session.clear();
        tx.begin();
        Client client = session.get(Client.class, 1l);
        tx.commit();
        session.close();
        System.out.println(client);

    }

    @Test
    public void getAllClientCardsTest(){
        EntityManager em = EMFactory.getEntityManager();
        em.getTransaction().begin();
        Client client = em.find(Client.class,1l);
        em.getTransaction().commit();
        em.getTransaction().begin();
        List<CreditCard> creditCardList = client.getCards();
        em.getTransaction().commit();
        creditCardList.forEach(System.out::println);
    }

    @Test
    public void emCacheTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        entityManager.getTransaction().begin();
        Client clientFromDb = entityManager.find(Client.class, 1L);
        entityManager.getTransaction().commit();
        entityManager.clear();
        //        TODO почему при транзакции через новый em выпадает exception
//        EntityManager entityManager1 = EMFactory.getEntityManager();
//        entityManager1.getTransaction().begin();
//        Client client = entityManager1.find(Client.class, 1L);
//        entityManager1.getTransaction().commit();
//        entityManager.close();

        new Thread(() -> {
            EntityManager entityManager1 = EMFactory.getEntityManager();
            entityManager1.getTransaction().begin();
            Client client = entityManager1.find(Client.class, 1L);
            entityManager1.getTransaction().commit();
        }).start();

    }

    @Test
    public void queryTransactionalCacheTest() {
        Session session = SFactory.getSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from CreditCard cc where  cashBack=:cashBack and cc.lastName=:lastName");
        query.setHint("org.hibernate.cacheable", "true").setParameter("cashBack", 7).setParameter("lastName", "Pojo");
        CreditCard creditCard = ( CreditCard ) query.getSingleResult();
        tx.commit();
        tx.begin();
        session.clear();
        CreditCard creditCard1 = ( CreditCard ) query.setHint("org.hibernate.cacheable", "true").getSingleResult();
        tx.commit();
        session.close();
    }

    @Test
    public void queryReadWriteTest() {
        Session session = SFactory.getSession();
//        org.hibernate.Transaction tx = session.beginTransaction();
        Client client = session.createQuery("from Client c where c.login=:login", Client.class)
                .setParameter("login", "login77")
                .setHint("org.hibernate.cacheable", "true")
                .getSingleResult();
//        tx.commit();
        session.clear();
        client = session.createQuery("from Client c where c.login=:login", Client.class)
                .setParameter("login", "login77")
                .setHint("org.hibernate.cacheable", "true")
                .getSingleResult();
//        tx.commit();
    }


    @Test
    public void listTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        Query query = entityManager.createQuery("select b.bankAccount,c.cardNumber,b.balance from Bank b join b.cardList c where b.bankAccount=:bankAccount");
        query.setParameter("bankAccount", "BAFNSDF2421MASFASAFASF87");
        List<Object[]> list = query.getResultList();
        for (Object[] object : list) {
            System.out.println(object[0] + " " + object[1] + " " + object[2]);
        }
        entityManager.getTransaction().begin();
        CreditCard creditCard = entityManager.find(CreditCard.class, 5643890743210091L);
        List<CreditCard> cardList = entityManager.createQuery("from CreditCard cc where cc.expDate<=:date ").setParameter("date", LocalDate.now()).getResultList();
        System.out.println(cardList);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        entityManager.remove(creditCard);
        entityManager.getTransaction().commit();
    }

    @Test
    public void getTransactionsTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        entityManager.getTransaction().begin();
        List<Transaction> transactionList = entityManager.createQuery("SELECT cc.transactionList FROM CreditCard cc WHERE cc.cardNumber=:cardNumber")
                .setParameter("cardNumber", 4531775344009622L).getResultList();
        entityManager.getTransaction().commit();
        System.out.println(transactionList);
    }

    @Test
    public void getBalanceTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        entityManager.getTransaction().begin();
        double balance = ( double ) entityManager.createQuery("select b.balance from Bank b join b.cardList cl  WHERE cl.cardNumber=:cardNumber")
                .setParameter("cardNumber",4531775344009622L).getSingleResult();
        entityManager.getTransaction().commit();
        System.out.println(balance);
    }


}
