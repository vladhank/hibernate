package util;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
    public  static final Logger LOGGER = Logger.getLogger(HibernateUtil.class);
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY;
    private static final String PERSISTENCE_UNIT_NAME = "hibernate.hank.vlad";

    static {
        try {
            ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Throwable ex) {
            LOGGER.error("Can't initialize entity manager factory" + " " + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }

    }

    public static EntityManager getEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public static void closeEntityManger(){
        ENTITY_MANAGER_FACTORY.close();
    }

}
