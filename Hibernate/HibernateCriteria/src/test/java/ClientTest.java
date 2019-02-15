import lombok.ToString;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Test;
import pojos.*;
import util.EMFactory;
import util.SFactory;

import javax.persistence.Embedded;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientTest {
    //    TODO  почему при явном указании fk ругается на него при удалении? какой в этом случае будет тип каскада в самой БД
    @Test
    public void clientSaveTest() {
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
        entityManager.close();
        Assert.assertEquals(4, list.size());
        Assert.assertNotNull(clientFromDb);
        Assert.assertEquals(testClient, clientFromDb);
        list.forEach(System.out::println);
    }

    @Test
    public void getAllTest() {
        EntityManager em = EMFactory.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Client> criteria = cb.createQuery(Client.class);
        Root<Client> clientRoot = criteria.from(Client.class);
        criteria.select(clientRoot);
        List<Client> clientList = em.createQuery(criteria).getResultList();
        clientList.forEach(System.out::println);
        Assert.assertEquals(4, clientList.size());
    }

    @Test
    public void restrictionLikeTest() {
        EntityManager em = EMFactory.getEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Client> criteria = criteriaBuilder.createQuery(Client.class);
        Root<Client> clientRoot = criteria.from(Client.class);
        criteria.select(clientRoot).where(criteriaBuilder.like(clientRoot.get("firstName"), "Jack"));
        List<Client> clientList = em.createQuery(criteria).getResultList();
        Assert.assertNotNull(clientList);
        for (Client client : clientList) {
            Assert.assertEquals("Jack", client.getFirstName());
        }
        clientList.forEach(System.out::println);
    }

    @Test
    public void notNullTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CreditCard> criteria = cb.createQuery(CreditCard.class);
        Root<CreditCard> creditCardRoot = criteria.from(CreditCard.class);
        criteria.select(creditCardRoot).where(cb.isNotNull(creditCardRoot.get("pin")));
        List<CreditCard> cardList = entityManager.createQuery(criteria).getResultList();
        Assert.assertNotNull(cardList);
        Assert.assertEquals(3, cardList.size());
    }

    @Test
    public void betweenTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CreditCard> criteria = cb.createQuery(CreditCard.class);
        Root<CreditCard> cardRoot = criteria.from(CreditCard.class);
        criteria.select(cardRoot).where(cb.between(cardRoot.get("cashBack"), 5, 7));
        List<CreditCard> creditCards = entityManager.createQuery(criteria).getResultList();
        for (CreditCard creditCard : creditCards) {
            System.out.println(creditCard);
            Assert.assertTrue(creditCard.getCashBack() > 5);
        }
    }


    @Test
    public void predicateTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CreditCard> criteriaQuery = criteriaBuilder.createQuery(CreditCard.class);
        Root<CreditCard> cardRoot = criteriaQuery.from(CreditCard.class);
        Predicate[] predicates = new Predicate[2];
        predicates[0] = criteriaBuilder.notLike(cardRoot.get("cardCompany"), "VISA");
        predicates[1] = criteriaBuilder.like(cardRoot.get("status"), "ACTIVE");
        criteriaQuery.select(cardRoot).where(predicates);
        List<CreditCard> creditCards = entityManager.createQuery(criteriaQuery).getResultList();
        creditCards.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Client> clientRoot = criteriaQuery.from(Client.class);
        criteriaQuery.select(criteriaBuilder.count(clientRoot));
        long count = entityManager.createQuery(criteriaQuery).getSingleResult();
        Assert.assertTrue(count > 0);
    }

    @Test
    public void paginationTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        int pageNumber = 1;
        int pageSize = 2;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        criteriaQuery.from(Client.class);
        TypedQuery<Client> typedQuery = entityManager.createQuery(criteriaQuery);
//        2
//        typedQuery.setFirstResult(pageSize*(pageNumber-1));
//        typedQuery.setMaxResults(pageSize);
//        3
        typedQuery.setFirstResult(0);
        typedQuery.setMaxResults(3);
        List<Client> clientList = typedQuery.getResultList();
        clientList.forEach(System.out::println);

    }

    @Test
    public void joinTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteria = criteriaBuilder.createQuery(Client.class);
        Root<Client> clientRoot = criteria.from(Client.class);
        Join<Client, CreditCard> creditCardJoin = clientRoot.join("cards", JoinType.INNER);
        criteria.where(criteriaBuilder.equal(creditCardJoin.get("cardNumber"), 4322558700884302L));
        Client client = entityManager.createQuery(criteria).getSingleResult();
        Assert.assertNotNull(client);
        Assert.assertEquals("Bobby", client.getFirstName());
        System.out.println(client);
    }

    @Test
    public void fetchTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CreditCard> criteriaQuery = criteriaBuilder.createQuery(CreditCard.class);
        Root<CreditCard> cardRoot = criteriaQuery.from(CreditCard.class);
        cardRoot.fetch("client");
        ParameterExpression<Long> cardNumberParameter = criteriaBuilder.parameter(Long.class);
        criteriaQuery.where(criteriaBuilder.equal(cardRoot.get("cardNumber"),cardNumberParameter));
        CreditCard creditCard= entityManager.createQuery(criteriaQuery).setParameter(cardNumberParameter,4322558700884302L).getSingleResult();
        System.out.println(creditCard);
    }

    @Test
    public void deleteBankAccount() {
        EntityManager em = EMFactory.getEntityManager();
        em.getTransaction().begin();
        Bank bank = em.find(Bank.class, "BCASDA977ASFA123VS");
        em.remove(bank);
        em.remove(bank);
        em.getTransaction().commit();
    }

}

