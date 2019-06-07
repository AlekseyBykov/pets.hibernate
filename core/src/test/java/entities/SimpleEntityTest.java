package entities;

import common.utils.SessionUtil;
import org.hibernate.*;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SimpleEntityTest {

    @Test
    public void testForEquality() {
        Long id;
        SimpleEntity simpleEntity;

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            simpleEntity = new SimpleEntity();
            simpleEntity.setName("string");

            session.save(simpleEntity);
            id = simpleEntity.getId();

            assertNotNull(id);
            assertEquals(simpleEntity.getName(), "string");

            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            SimpleEntity firstSimpleEntity = session.load(SimpleEntity.class, id);
            assertEquals(firstSimpleEntity.getName(), "string");

            SimpleEntity secondSimpleEntity = session.load(SimpleEntity.class, id);

            assertEquals(firstSimpleEntity, secondSimpleEntity);
            assertTrue(firstSimpleEntity == secondSimpleEntity);

            assertEquals(simpleEntity, firstSimpleEntity);
            assertFalse(simpleEntity == firstSimpleEntity);
        }
    }

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
        assertEquals(simpleEntity.getName(), "some another name");
    }

    @Test
    public void testSaveOrUpdateSimpleEntity() {
        Long id;
        SimpleEntity simpleEntity;

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            simpleEntity = new SimpleEntity();
            simpleEntity.setName("some another string");

            session.save(simpleEntity);
            id = simpleEntity.getId();

            assertNotNull(id);
            assertEquals(simpleEntity.getName(), "some another string");

            tx.commit();
        }

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            simpleEntity.setName("string");

            session.saveOrUpdate(simpleEntity);
            tx.commit();
        }

        assertEquals(id, simpleEntity.getId());
        assertEquals(simpleEntity.getName(), "string");
    }
}