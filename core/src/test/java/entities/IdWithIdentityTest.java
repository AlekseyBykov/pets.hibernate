package entities;

import common.utils.SessionUtil;
import org.hibernate.*;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class IdWithIdentityTest {

    @Test
    public void testSaveIdWithIdentityWithoutExplicitlyAssignedId() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            IdWithIdentity idWithIdentity = new IdWithIdentity();
            idWithIdentity.setName("some string");

            session.save(idWithIdentity);
            assertNotNull(idWithIdentity.getId());
            assertEquals(idWithIdentity.getName(), "some string");

            tx.commit();
        }
    }
}