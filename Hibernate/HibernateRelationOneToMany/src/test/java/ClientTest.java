import lombok.ToString;
import org.junit.Assert;
import org.junit.Test;
import pojos.Address;
import pojos.Client;
import pojos.CreditCard;
import util.EMFactory;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientTest {
    //    TODO  почему при явном указании fk ругается на него при удалении? какой в этом случае будет тип каскада в самой БД
    //    TODO мы добавляем в клиента коллецию карт, но они не хранятся в БД. Это делается для того чтобы hibernate понимали связи между табл?
    @Test
    public void clientSaveTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        Client testClient = new Client("Bobby", "Lee", "+375295674321", new Address("Minsk", "Main", "52B", "121"), LocalDate.of(2001, 04, 25), "moonLoght", "notRepeat", null);
        Client testClient2 = new Client("Alan", "Walker", "+375447654321", new Address("Los Soligorsk", "Shaxterov", "1", "52"), LocalDate.of(1976, 01, 21), "login7", "password8", null);
        Client testClient3 = new Client("Jack", "Nikolson", "+375297162534", new Address("Svetlcity", "Molodejnui", "23", "21"), LocalDate.now(), "login77", "password88", null);
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
    }

    @Test
    public void clientUpdateTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        Client testClient = new Client("Bobby", "Lee", "+375295674321", new Address("Minsk", "Main", "52B", "121"), LocalDate.of(2001, 04, 25), "moonLoght", "notRepeat", null);
        Client testClient2 = new Client("Alan", "Walker", "+375447654321", new Address("Los Soligorsk", "Shaxterov", "1", "52"), LocalDate.of(1976, 01, 21), "login7", "password8", null);
        Client testClient3 = new Client("Jack", "Nikolson", "+375297162534", new Address("Svetlcity", "Molodejnui", "23", "21"), LocalDate.now(), "login77", "password88", null);
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
        testClient.setLastName("Plusovich");
        testClient.setCards(new ArrayList<>());
        creditCard2.setCashBack(5);
        testClient.getCards().add(creditCard);
        testClient.getCards().add(creditCard2);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        entityManager.clear();
        Client clientFromDb = entityManager.find(Client.class, 1L);
        CreditCard cardFromDB = entityManager.find(CreditCard.class, creditCard2.getCardNumber());
        entityManager.getTransaction().commit();
        entityManager.close();
        Assert.assertEquals(testClient.getDateOfBirth(), clientFromDb.getDateOfBirth());
        Assert.assertEquals(creditCard2, cardFromDB);
    }

    @Test
    public void deleteClientTest() {
        EntityManager entityManager = EMFactory.getEntityManager();
        Client testClient = new Client("Bobby", "Lee", "+375295674321", new Address("Minsk", "Main", "52B", "121"), LocalDate.of(2001, 04, 25), "moonLoght", "notRepeat", new ArrayList<>());
        Client testClient2 = new Client("Alan", "Walker", "+375447654321", new Address("Los Soligorsk", "Shaxterov", "1", "52"), LocalDate.of(1976, 01, 21), "login7", "password8", null);
        Client testClient3 = new Client("Jack", "Nikolson", "+375297162534", new Address("Svetlcity", "Molodejnui", "23", "21"), LocalDate.now(), "login77", "password88", null);
        CreditCard creditCard = new CreditCard(5643890743210091L, "MASTERCARD", "Jojo", "Pojo", LocalDate.of(2022, 07, 23), 333, 5555, 7, "ACTIVE", "BSFSDG231SFDSFSDGVSDFDSF", testClient);
        CreditCard creditCard2 = new CreditCard(4322558700884302L, "VISA", "Alan", "Walker", LocalDate.of(2025, 11, 15), 652, 8218, 0, "ACTIVE", "BSFSDG231SFDSFSDGVSDFDSF", testClient);
        entityManager.getTransaction().begin();
        entityManager.persist(testClient);
        entityManager.persist(testClient2);
        entityManager.persist(testClient3);
        entityManager.persist(creditCard);
        entityManager.persist(creditCard2);
        testClient.getCards().add(creditCard);
        testClient.getCards().add(creditCard2);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
//        entityManager.clear();
//        entityManager.remove(creditCard);
        entityManager.remove(testClient);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

