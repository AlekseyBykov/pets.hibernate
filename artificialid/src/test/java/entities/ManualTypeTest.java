package entities;

import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.*;
import org.hibernate.id.IdentifierGenerationException;
import org.testng.annotations.*;

import static org.testng.Assert.*;

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
    public void test__ShouldPass__saveManualTypeWithoutId() {
        try(Session session = sessionFactory.openSession()) {
            Long id = null;

            Transaction tx = session.beginTransaction();

            ManualType manualType = new ManualType();
            manualType.setName("some name");

            session.save(manualType);

            id = manualType.getId();

            assertNull(id);

            tx.commit();
        }
    }

    @Test
    public void test__ShouldPass__saveManualTypeWithId() {
        try(Session session = sessionFactory.openSession()) {
            Long id = null;

            Transaction tx = session.beginTransaction();

            ManualType manualType = new ManualType();
            manualType.setId(1L);
            manualType.setName("some another name");

            session.save(manualType);
            id = manualType.getId();

            assertNotNull(id);

            tx.commit();
        }
    }
}