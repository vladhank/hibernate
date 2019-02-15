import loader.ClientLoader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Test;
import pojos.Client;
import sun.misc.Cleaner;
import util.SFactory;

import java.time.LocalDate;

public class SessionFactoryTest {

    @Test
    public void saveTest() {

        Session session = SFactory.getSession();
        Assert.assertNotNull(session);
        Client client = new Client(4l, "Vadim", "Motulev", "+375259999999", "Miroshnichenko 4", LocalDate.now(), "kolos", "password");
        Client loadedClient;
        Transaction tx = session.beginTransaction();
        session.save(client);
        tx.commit();
        loadedClient = ClientLoader.get(1l);
        Assert.assertNotEquals(client, loadedClient);
        session.close();
    }

    @Test
    public void loadTest() {
        Session session = SFactory.getSession();
        Client loadedClient;
        loadedClient = session.load(Client.class, 1l);
        String firstName = loadedClient.getFirstName();
        Assert.assertNotNull(loadedClient);
        Assert.assertEquals(firstName, "Bobby");
        session.close();
    }

    @Test
    public void lazyLoadTest() {
        Session session = SFactory.getSession();
        Client loadedClient = session.load(Client.class, 2l);
//        session.clear();
        System.out.println(loadedClient.getPhoneNumber());
        session.close();
    }

    @Test
    public void getTest() {
        Session session = SFactory.getSession();
        Client loadedClient;
        loadedClient = ClientLoader.get(1l);
        String firstName = loadedClient.getFirstName();
        session.close();
        Assert.assertNotNull(loadedClient);
        Assert.assertEquals(firstName, "Alan");
    }

    @Test
    public void deleteTest() {
        Session session = SFactory.getSession();
        Client deletedClient = new Client(4l, "Sergei", "Minaev", "+375446567732", "Vaneeva 15", LocalDate.of(1973, 12, 21), "jokdsa", "gbvc67s");
        System.out.println("Count before save " + session.createQuery("Select count(*) from Client").getSingleResult());
        session.beginTransaction();
        session.save(deletedClient);
        System.out.println("Count after save " + session.createQuery("Select count(*) from Client").getSingleResult());
        session.delete(deletedClient);
        session.getTransaction().commit();
        System.out.println("Count after delete " + session.createQuery("Select count(*) from Client").getSingleResult());
        session.close();
    }

    @Test
    public void dirtyTest() {
        Session session = SFactory.getSession();
        Client loadedClient = session.load(Client.class, 3l);
        loadedClient.setLastName("Malahaev");
        System.out.println(session.isDirty());
        session.close();
    }

    @Test
    public void flushCommitTest() {
        Session session = SFactory.getSession();
        session.getTransaction().begin();
        Client loadedClient = session.get(Client.class, 1l);
        Client loadedClient2 = session.get(Client.class, 2l);
        String client1NameBefore = loadedClient.getFirstName();
        String client2NameBefore = loadedClient2.getFirstName();
        loadedClient.setFirstName("Kevin");
        loadedClient2.setFirstName("Bruno");
        session.flush();
        loadedClient = session.get(Client.class, 1l);
        loadedClient2 = session.get(Client.class, 2l);
        String client1NameAfter = loadedClient.getFirstName();
        String client2NameAfter = loadedClient2.getFirstName();
        session.close();
        Assert.assertNotEquals(client1NameBefore,client1NameAfter);
        Assert.assertNotEquals(client2NameBefore,client2NameAfter);
    }
}

