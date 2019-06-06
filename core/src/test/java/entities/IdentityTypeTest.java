package entities;

import common.utils.SessionUtil;
import org.hibernate.*;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class IdentityTypeTest {

    @Test
    public void testSaveIdentityTypeWithoutExplicitlyAssignedId() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            IdentityType identityType = new IdentityType();
            identityType.setName("some string");

            session.save(identityType);
            assertNotNull(identityType.getId());
            assertEquals(identityType.getName(), "some string");

            tx.commit();
        }
    }
}