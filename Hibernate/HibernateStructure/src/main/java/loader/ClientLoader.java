package loader;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojos.Client;
import util.SFactory;

import java.io.Serializable;
import java.time.LocalDate;

public class ClientLoader {
    public static final Logger logger = Logger.getLogger(ClientLoader.class);

    public static Client load(long id) {
        Client client = new Client();
        Session session = null;
        Transaction tx = null;
        try {
            session = SFactory.getSession();
            tx = session.beginTransaction();
            client = session.load(Client.class, id);
            tx.commit();
            session.close();
            return client;
        } catch (HibernateException ex) {
            tx.rollback();
            logger.error("Can't load data from database", ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    public static Client get(long id) {
        Client client = new Client();
        Session session = null;
        Transaction tx = null;
        try {
            session = SFactory.getSession();
            tx = session.beginTransaction();
            client = session.get(Client.class, id);
            tx.commit();
            session.close();
            SFactory.closeSession();
            return client;
        } catch (HibernateException ex) {
            tx.rollback();
            logger.error("Can't load data from database", ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    public static void addClient() {

    }

}
