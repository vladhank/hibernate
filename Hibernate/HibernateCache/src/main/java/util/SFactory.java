package util;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import pojos.*;

public class SFactory {
    public static Logger logger = Logger.getLogger(SFactory.class);
    private static SessionFactory sessionFactory = null;
    private static final ThreadLocal<Session> threadLocal;


    static {
        try {
            Configuration configuration = new Configuration().configure();
            configuration.setPhysicalNamingStrategy(new CustomNamingStrategy());
            configuration.addAnnotatedClass(Client.class);
            configuration.addAnnotatedClass(Bank.class);
            configuration.addAnnotatedClass(Address.class);
            configuration.addAnnotatedClass(CreditCard.class);
            configuration.addAnnotatedClass(Transaction.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
            threadLocal = new ThreadLocal<Session>();

        } catch (HibernateException ex) {
            logger.error("Error creating Session" + " " + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
        Session session = threadLocal.get();
        if (session == null) {
            session = sessionFactory.openSession();
            threadLocal.set(session);
        }
        return session;
    }

    public static void closeSession() {
        Session session = threadLocal.get();
        if (session != null) {
            session.close();
            threadLocal.set(null);
        }

    }

}
