import lombok.ToString;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Test;
import pojos.Address;
import pojos.Client;
import pojos.CreditCard;
import util.EMFactory;
import util.SFactory;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientTest {
    @Test
    public void clientSaveTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        Client testClient = new Client("Bobby", "Lee", "+375295674321", new Address("Minsk", "Main", "52B", "121"), LocalDate.of(2001, 04, 25), "moonLoght", "notRepeat", null);
        Client testClient2 = new Client("Alan", "Walker", "+375447654321", new Address("Los Soligorsk", "Shaxterov", "1", "52"), LocalDate.of(1976, 01, 21), "login7", "password8", null);
        Client testClient3 = new Client("Jack", "Nikolson", "+375297162534", new Address("Svetlcity", "Molodejnui", "23", "21"), LocalDate.now(), "login77", "password88", null);
        Client testClient4 = new Client("Jack", "Morris", "+375445533215", new Address("Polock", "Efrasini", "54", "43B"), LocalDate.of(1985, 8, 05), "mbvfsa", "mcxv42sdf", null);
        CreditCard creditCard = new CreditCard(5643890743210091L, "MASTERCARD", "Jojo", "Pojo", LocalDate.of(2022, 07, 23), 333, 5555, 7, "ACTIVE", "BSFSDG231SFDSFSDGVSDFDSF", testClient);
        CreditCard creditCard2 = new CreditCard(4322558700884302L, "VISA", "Alan", "Walker", LocalDate.of(2025, 11, 15), 652, 8218, 0, "ACTIVE", "BSFSDG231SFDSFSDGVSDFDSF", testClient);
        entityManager.getTransaction().begin();
        entityManager.persist(testClient);
        entityManager.persist(testClient2);
        entityManager.persist(testClient3);
        entityManager.persist(creditCard);
        entityManager.persist(creditCard2);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        List<Client> list;
        Client clientFromDb = entityManager.find(Client.class, 1L);
        list = entityManager.createQuery("FROM Client ").getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        Assert.assertEquals(3, list.size());
        Assert.assertNotNull(clientFromDb);
        Assert.assertEquals(testClient, clientFromDb);
        list.forEach(System.out::println);
    }

    @Test
    public void hql() {
        EntityManager entityManager = EMFactory.getEntityManager();
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("FROM Client WHERE firstName like 'Alan' or firstName like 'Jack'");
        //разрыв соединения по истечению времени
        query.setTimeout(1000)
                // включить в кеш запросов
                .setCacheable(true)
                // добавлять в кэш, но не считывать из него
                .setCacheMode(CacheMode.REFRESH)
                .setHibernateFlushMode(FlushMode.COMMIT)
                // сущности и коллекции помечаюся как только для чтения
                .setReadOnly(true);
        List<Client> clientList = query.list();
        for (Client client : clientList) {
            System.out.println(client);
        }
        Assert.assertEquals(2, clientList.size());
        entityManager.close();
    }

    @Test
    public void selectTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        String phonePrefix = "+375";
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("SELECT substring(phoneNumber,1,4)  FROM Client", String.class);
        List<String> list = query.list();
        for (String str : list) {
            Assert.assertEquals(phonePrefix, str);
        }
        System.out.println(list);
    }

    @Test
    public void orderByTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("from Client order by clientID desc");
        List<Client> clientList = query.list();
        long idAfterOrdering = clientList.get(0).getClientID();
        for (Client client : clientList) {
            System.out.println(client);
        }
        Assert.assertNotEquals(1l, idAfterOrdering);
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
    public void parameterTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        String testName = "Alan";
        String noName = "Bob";
        javax.persistence.Query query = entityManager.createQuery("from Client c  where c.firstName=:name");
        query.setParameter("name", testName);
        List<Client> clientList = query.getResultList();
        clientList.forEach(System.out::println);
    }

    @Test
    public void groupByTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("select c.dateOfBirth,c.login,c.password from Client c where firstName='Jack' group by c.dateOfBirth,c.login,c.password");
        List<Object[]> clientsInfo = query.getResultList();
        LocalDate[] localDate = new LocalDate[2];
        for (Object[] client : clientsInfo) {
            localDate[0] = ( LocalDate ) client[0];
            System.out.println("Date of birth:  " + client[0] + " Login: " + client[1] + " Password: " + client[2]);
        }
        System.out.println(localDate.toString());
    }

    @Test
    public void searchByID() {
        EntityManager entityManager = EMFactory.getEntityManager();
        List<Long> ids = Arrays.asList(1l, 2l);
        javax.persistence.Query query = entityManager.createQuery("from Client c where c.clientID in (:ids)");
        query.setParameter("ids", ids);
        List<Client> clientList = query.getResultList();
        clientList.forEach(System.out::println);
    }

    @Test
    public void updateClient() {
        EntityManager entityManager = EMFactory.getEntityManager();
        entityManager.getTransaction().begin();
        javax.persistence.Query query = entityManager.createQuery("update Client c set c.address.city = :newCity" + " where c.lastName =:oldLastName ");
        query.setParameter("newCity", "Edgartown");
        query.setParameter("oldLastName", "Lee");
        int result = query.executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Test
    public void deleteClient() {
        EntityManager entityManager = EMFactory.getEntityManager();
        Client client = new Client("Sam", "Serious", "+79256532892", new Address("Varkuta", "Mendeleeva", "21", "99"), LocalDate.of(1977, 4, 4), "bcas6", "lkj(vc", null);
        entityManager.getTransaction().begin();
        entityManager.persist(client);
        javax.persistence.Query query = entityManager.createQuery("delete from Client where clientID=:id");
        query.setParameter("id", client.getClientID()).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        entityManager.clear();
        Client clientFromDb = entityManager.find(Client.class, client.getClientID());
        entityManager.getTransaction().commit();
        Assert.assertNull(clientFromDb);
    }

}

