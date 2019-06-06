package entities;

import common.utils.SessionUtil;
import org.hibernate.*;
import org.hibernate.id.IdentifierGenerationException;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class IdWithNonameTest {

    @Test(expectedExceptions = IdentifierGenerationException.class)
    public void testSaveIdWithNonameWithoutId() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            IdWithNoname idWithNoname = new IdWithNoname();
            idWithNoname.setNoname("some noname");

            session.save(idWithNoname);
            assertNull(idWithNoname.getId());

            tx.commit();
        }
    }

    @Test
    public void testSaveIdWithNonameWithId() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            IdWithNoname idWithNoname = new IdWithNoname();
            idWithNoname.setId(1L);
            idWithNoname.setNoname("some another noname");

            session.save(idWithNoname);
            assertNotNull(idWithNoname.getId());
            assertEquals(idWithNoname.getNoname(), "some another noname");

            tx.commit();
        }
    }
}