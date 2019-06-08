package entities;

import common.utils.SessionUtil;
import org.hibernate.*;
import org.hibernate.id.IdentifierGenerationException;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CoreConceptsTest {

    @Test
    public void testForIdentityAndEquality() {
        Long id;
        EntityWithId entity;

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            entity = new EntityWithId();
            entity.setName("A");

            session.save(entity);
            id = entity.getId();

            assertNotNull(id);
            assertEquals(entity.getName(), "A");

            tx.commit();
        }

        try(Session session = SessionUtil.getSession()) {
            EntityWithId firstEntity = session.load(EntityWithId.class, id);
            assertEquals(firstEntity.getName(), "A");

            EntityWithId secondEntity = session.load(EntityWithId.class, id);
            assertEquals(secondEntity.getName(), "A");

            assertEquals(firstEntity, secondEntity);
            assertTrue(firstEntity == secondEntity);

            assertEquals(entity, firstEntity);
            assertFalse(entity == firstEntity);
        }
    }

    @Test
    public void testSaveEntity() {
        Long id;
        EntityWithId entity;

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            entity = new EntityWithId();
            entity.setName("B");

            session.save(entity);
            id = entity.getId();

            assertNotNull(id);
            assertEquals(entity.getName(), "B");

            tx.commit();
        }

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            entity.setName("C");

            session.save(entity);

            tx.commit();
        }

        assertNotEquals(id, entity.getId());
        assertEquals(entity.getName(), "C");
    }

    @Test
    public void testSaveOrUpdateEntity() {
        Long id;
        EntityWithId entity;

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            entity = new EntityWithId();
            entity.setName("D");

            session.save(entity);
            id = entity.getId();

            assertNotNull(id);
            assertEquals(entity.getName(), "D");

            tx.commit();
        }

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            entity.setName("E");

            session.saveOrUpdate(entity);
            tx.commit();
        }

        assertEquals(id, entity.getId());
        assertEquals(entity.getName(), "E");
    }

    @Test(expectedExceptions = ObjectNotFoundException.class)
    public void testLoadMissingEntity() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            EntityWithId missingEntity = session.load(EntityWithId.class, 100L);
            assertNull(missingEntity);

            tx.commit();
        }
    }

    @Test
    public void testGetMissingEntity() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            EntityWithId missingEntity = session.get(EntityWithId.class, 100L);
            assertNull(missingEntity);

            tx.commit();
        }
    }

    @Test
    public void firstTestSaveEntityWithoutExplicitlyAssignedId() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            EntityWithId entity = new EntityWithId();
            entity.setName("F");

            session.save(entity);
            assertNotNull(entity.getId());
            assertEquals(entity.getName(), "F");

            tx.commit();
        }
    }

    @Test(expectedExceptions = IdentifierGenerationException.class)
    public void secondTestSaveEntityWithoutExplicitlyAssignedId() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            EntityWithoutId entity = new EntityWithoutId();
            entity.setName("G");

            session.save(entity);
            assertNull(entity.getId());

            tx.commit();
        }
    }

    @Test
    public void thirdTestSaveEntityWithExplicitlyAssignedId() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            EntityWithoutId entity = new EntityWithoutId();
            entity.setId(200L);
            entity.setName("H");

            session.save(entity);
            assertNotNull(entity.getId());
            assertEquals(entity.getName(), "H");

            tx.commit();
        }
    }

    @Test
    public void testMergeEntity() {
        EntityWithoutId entity;
        Long id;

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            entity = new EntityWithoutId();

            entity.setId(12L);
            entity.setName("I");

            session.save(entity);

            id = entity.getId();

            tx.commit();
        }

        try(Session session = SessionUtil.getSession()) {
            entity = session.load(EntityWithoutId.class, id);

            assertEquals(entity.getName(), "I");
            assertEquals(entity.getId(), Long.valueOf(12L));
        }

        entity.setName("J");

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            session.merge(entity);

            tx.commit();
        }

        try(Session session = SessionUtil.getSession()) {
            entity = session.load(EntityWithoutId.class, id);

            assertEquals(entity.getName(), "J");
            assertEquals(entity.getId(), Long.valueOf(12L));
        }
    }

}