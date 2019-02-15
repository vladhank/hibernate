package util;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFactory {
    public static final Logger logger = Logger.getLogger(EMFactory.class);
    private static EntityManagerFactory entityManagerFactory = null;
    private static final String pesistenceUnitName = "hibernate.hank.vlad";

    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(pesistenceUnitName);
        } catch (Throwable ex) {
            logger.error("Can't initialize entity manager factory" + " " + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void closeEntityManager() {
        entityManagerFactory.close();
    }

}
