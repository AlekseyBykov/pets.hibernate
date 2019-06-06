package entities;

import common.utils.SessionUtil;
import org.hibernate.*;
import org.hibernate.id.IdentifierGenerationException;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class IdWithoutIdentityTest {

    @Test(expectedExceptions = IdentifierGenerationException.class)
    public void testSaveIdWithoutIdentityWithoutId() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            IdWithoutIdentity idWithoutIdentity = new IdWithoutIdentity();
            idWithoutIdentity.setName("some name");

            session.save(idWithoutIdentity);
            assertNull(idWithoutIdentity.getId());

            tx.commit();
        }
    }

    @Test
    public void testSaveIdWithoutIdentityWithId() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            IdWithoutIdentity idWithoutIdentity = new IdWithoutIdentity();
            idWithoutIdentity.setId(1L);
            idWithoutIdentity.setName("some another name");

            session.save(idWithoutIdentity);
            assertNotNull(idWithoutIdentity.getId());
            assertEquals(idWithoutIdentity.getName(), "some another name");

            tx.commit();
        }
    }
}