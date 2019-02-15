package util;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import pojos.Bank;
import pojos.Client;

public class SFactory {
    public static Logger logger = Logger.getLogger(SFactory.class);
    private static SessionFactory sessionFactory = null;
    private static final ThreadLocal<Session> threadLocal;


    static {
        try {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(Client.class);
            configuration.addAnnotatedClass(Bank.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
            threadLocal = new ThreadLocal<Session>();

        } catch (HibernateException ex) {
            logger.error("Error creating Session" + " " + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

//        public static Session getSession() {
//        return sessionFactory.openSession();
//    }
    public static Session getSession() {
        Session session = threadLocal.get();
        if (session == null) {
            session = sessionFactory.openSession();
            threadLocal.set(session);
        }
        return session;
    }

    public static void closeSession() {
        sessionFactory.close();
    }

}
