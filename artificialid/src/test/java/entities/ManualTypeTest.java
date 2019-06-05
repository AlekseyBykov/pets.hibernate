package entities;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.*;
import org.hibernate.id.IdentifierGenerationException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

//import static org.testng.Assert.*;

public class ManualTypeTest {
    private SessionFactory sessionFactory;

    @BeforeClass
    public void setup() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    @Test(expectedExceptions = IdentifierGenerationException.class)
    public void test__shouldPass__SaveManualType() {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            ManualType manualType = new ManualType();
            manualType.setName("some name");

            session.save(manualType);

            tx.commit();
        }
    }
}