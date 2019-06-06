package entities;

import common.utils.SessionUtil;
import org.hibernate.*;
import org.hibernate.id.IdentifierGenerationException;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class ManualTypeTest {

    @Test(expectedExceptions = IdentifierGenerationException.class)
    public void testSaveManualTypeWithoutId() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            ManualType manualType = new ManualType();
            manualType.setName("some name");

            session.save(manualType);
            assertNull(manualType.getId());

            tx.commit();
        }
    }

    @Test
    public void testSaveManualTypeWithId() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            ManualType manualType = new ManualType();
            manualType.setId(1L);
            manualType.setName("some another name");

            session.save(manualType);
            assertNotNull(manualType.getId());
            assertEquals(manualType.getName(), "some another name");

            tx.commit();
        }
    }
}