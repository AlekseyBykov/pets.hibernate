package common.utils;

import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.*;

public final class SessionUtil {

    private static final SessionUtil instance = new SessionUtil();
    private final SessionFactory sessionFactory;

    private SessionUtil() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    private static SessionUtil getInstance() {
        return instance;
    }

    public static Session getSession() {
        return getInstance().sessionFactory.openSession();
    }
}