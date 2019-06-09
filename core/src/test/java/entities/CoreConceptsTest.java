package entities;

import common.utils.SessionUtil;
import org.hibernate.*;
import org.hibernate.id.IdentifierGenerationException;
import org.testng.annotations.*;

import static org.testng.Assert.*;


public class CoreConceptsTest {

    @BeforeSuite
    public void saveEntities() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            FirstEntity firstEntity = new FirstEntity();
            firstEntity.setName("First entity");

            session.save(firstEntity);

            assertNotNull(firstEntity.getId());
            assertEquals(firstEntity.getName(), "First entity");

            SecondEntity secondEntity = new SecondEntity();
            secondEntity.setId(1L);
            secondEntity.setName("Second entity");

            session.save(secondEntity);

            assertNotNull(secondEntity.getId());
            assertEquals(secondEntity.getName(), "Second entity");

            tx.commit();
        }
    }

    @Test(priority = 0)
    public void testForIdentityAndEquality() {
        Long id;
        SecondEntity entity0;

        try(Session session = SessionUtil.getSession()) {
            entity0 = session.load(SecondEntity.class, 1L);
            id = entity0.getId();

            assertEquals(id, Long.valueOf(1L));
            assertEquals(entity0.getName(), "Second entity");
        }

        try(Session session = SessionUtil.getSession()) {
            SecondEntity entity1 = session.load(SecondEntity.class, id);
            assertEquals(entity1.getName(), "Second entity");
            assertEquals(entity1.getId(), Long.valueOf(1L));

            SecondEntity entity2 = session.load(SecondEntity.class, id);
            assertEquals(entity2.getName(), "Second entity");
            assertEquals(entity2.getId(), Long.valueOf(1L));

            assertEquals(entity1, entity2);
            assertTrue(entity2 == entity1);

            assertEquals(entity0, entity1);
            assertFalse(entity0 == entity1);
        }
    }

    @Test(priority = 1)
    public void testDoubleSaveEntity() {
        Long id;
        FirstEntity entity;

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            entity = new FirstEntity();
            entity.setName("Third entity");

            session.save(entity);
            id = entity.getId();

            assertNotNull(id);

            tx.commit();
        }

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            entity.setName("Fourth entity");
            session.save(entity);

            tx.commit();
        }

        assertNotEquals(id, entity.getId());
        assertEquals(entity.getName(), "Fourth entity");
    }

    @Test(priority = 2)
    public void testSaveOrUpdateEntity() {
        try(Session session = SessionUtil.getSession()) {
            SecondEntity entity = session.load(SecondEntity.class, 1L);
            Transaction tx = session.beginTransaction();

            entity.setName("Updated entity");

            session.saveOrUpdate(entity);
            tx.commit();

            assertEquals(entity.getId(), Long.valueOf(1L));
            assertEquals(entity.getName(), "Updated entity");
        }
    }

    @Test(expectedExceptions = ObjectNotFoundException.class)
    public void testLoadMissingEntity() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            FirstEntity missingEntity = session.load(FirstEntity.class, 100L);
            assertNull(missingEntity);

            tx.commit();
        }
    }

    @Test
    public void testGetMissingEntity() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            FirstEntity missingEntity = session.get(FirstEntity.class, 100L);
            assertNull(missingEntity);

            tx.commit();
        }
    }

    @Test(priority = 3)
    public void firstTestSaveEntityWithoutExplicitlyAssignedId() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            FirstEntity entity = new FirstEntity();
            entity.setName("Another first entity");

            session.save(entity);
            assertNotNull(entity.getId());
            assertEquals(entity.getName(), "Another first entity");

            tx.commit();
        }
    }

    @Test(expectedExceptions = IdentifierGenerationException.class)
    public void secondTestSaveEntityWithoutExplicitlyAssignedId() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            SecondEntity entity = new SecondEntity();
            entity.setName("Another second entity");

            session.save(entity);
            assertNull(entity.getId());

            tx.commit();
        }
    }

    @Test(priority = 4)
    public void thirdTestSaveEntityWithExplicitlyAssignedId() {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            SecondEntity entity = new SecondEntity();
            entity.setId(2L);
            entity.setName("Another second entity");

            session.save(entity);
            assertNotNull(entity.getId());
            assertEquals(entity.getName(), "Another second entity");

            tx.commit();
        }
    }

    @Test(priority = 5)
    public void testMergeEntity() {
        try(Session session = SessionUtil.getSession()) {
            SecondEntity entity = session.load(SecondEntity.class, 1L);
            entity.setName("Merged to the database entity");

            Transaction tx = session.beginTransaction();

            session.merge(entity);

            tx.commit();

            entity = session.load(SecondEntity.class, 1L);

            assertEquals(entity.getName(), "Merged to the database entity");
            assertEquals(entity.getId(), Long.valueOf(1L));
        }
    }

    @Test(priority = 6)
    public void testSaveChangesOnPersistentState() {
        SecondEntity entity;

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            entity = session.load(SecondEntity.class, 1L);
            entity.setName("Modified entity");

            tx.commit();
        }

        assertEquals(entity.getName(), "Modified entity");
        assertEquals(entity.getId(), Long.valueOf(1L));
    }

    @Test(priority = 7)
    public void testRefreshEntity() {
        SecondEntity refreshedEntity;

        try(Session session = SessionUtil.getSession()) {
            refreshedEntity = session.load(SecondEntity.class, 1L);
            refreshedEntity.setName("out of synch");
        }

        assertEquals(refreshedEntity.getName(), "out of synch");
        assertEquals(refreshedEntity.getId(), Long.valueOf(1L));

        try(Session session = SessionUtil.getSession()) {
            session.refresh(refreshedEntity);
        }

        assertEquals(refreshedEntity.getName(), "Modified entity");
        assertEquals(refreshedEntity.getId(), Long.valueOf(1L));
    }
}