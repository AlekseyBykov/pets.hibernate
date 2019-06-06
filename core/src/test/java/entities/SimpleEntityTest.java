package entities;

import common.utils.SessionUtil;
import org.hibernate.*;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SimpleEntityTest {

    @Test
    public void testSaveSimpleEntity() {
        Long id;
        SimpleEntity simpleEntity;

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            simpleEntity = new SimpleEntity();
            simpleEntity.setName("some string");

            session.save(simpleEntity);
            id = simpleEntity.getId();

            assertNotNull(id);
            assertEquals(simpleEntity.getName(), "some string");

            tx.commit();
        }

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            simpleEntity.setName("some another name");

            session.save(simpleEntity);

            tx.commit();
        }

        assertNotEquals(id, simpleEntity.getId());
    }
}