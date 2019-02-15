package util;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFactory {
    public static final Logger logger = Logger.getLogger(EMFactory.class);
    private static final EntityManagerFactory entityManagerFactory;
    private static final ThreadLocal<EntityManager> threadLocal;
    private static final String pesistenceUnitName = "hibernate.hank.vlad";

    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(pesistenceUnitName);
            threadLocal = new ThreadLocal<EntityManager>();
        } catch (Throwable ex) {
            logger.error("Can't initialize entity manager factory" + " " + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {
        EntityManager entityManager = threadLocal.get();

        if (entityManager == null) {
            entityManager = entityManagerFactory.createEntityManager();
            threadLocal.set(entityManager);
        }
        return entityManager;
    }

    public static void closeEntityManager() {
        EntityManager entityManager = threadLocal.get();

        if (entityManager != null) {
            entityManager.close();
            threadLocal.set(null);
        }

    }

    public static void closeEntityManagerFactory() {
        entityManagerFactory.close();
    }

    public static void beginTransaction() {
        getEntityManager().getTransaction().begin();
    }

    public static void rollback() {
        getEntityManager().getTransaction().rollback();
    }

    public static void commit() {
        getEntityManager().getTransaction().commit();
    }


}
