//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.context;

import alekseybykov.portfolio.hibernate.TestContextHook;
import alekseybykov.portfolio.hibernate.entities.FirstEntity;
import alekseybykov.portfolio.hibernate.entities.SecondEntity;
import common.utils.SessionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.id.IdentifierGenerationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Any method on Session object fire off some event.
 * For example, session.save() will be translated to SaveOrUpdateEvent.
 * All the event is queued in ActionQueue.
 * At the end of the unit of work this actions are performed
 * in database (as DML).
 *
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-06-09
 */
@ExtendWith({TestContextHook.class})
@DisplayName("Tests for persistence context concept")
class PersistenceContextTest {

    @Test
    void saveEntities() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            FirstEntity firstEntity = new FirstEntity();
            firstEntity.setName("First entity");

            session.save(firstEntity);

            assertNotNull(firstEntity.getId());
            assertEquals("First entity", firstEntity.getName());

            SecondEntity secondEntity = new SecondEntity();
            secondEntity.setId(NumberUtils.LONG_ONE);
            secondEntity.setName("Second entity");

            session.save(secondEntity);

            assertNotNull(secondEntity.getId());
            assertEquals("Second entity", secondEntity.getName());

            tx.commit();
        }
    }

    @Test
    void testDoubleSaveEntity() {
        Long id;
        FirstEntity entity;

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            entity = new FirstEntity();
            entity.setName("Third entity");

            session.save(entity);
            id = entity.getId();

            assertNotNull(id);

            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            entity.setName("Fourth entity");
            session.save(entity);

            tx.commit();
        }

        assertNotEquals(id, entity.getId());
        assertEquals("Fourth entity", entity.getName());
    }

    @Test
    void testSaveOrUpdateEntity() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            SecondEntity secondEntity = new SecondEntity();
            secondEntity.setId(NumberUtils.LONG_ONE);
            secondEntity.setName(StringUtils.EMPTY);

            session.save(secondEntity);

            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            SecondEntity entity = session.load(SecondEntity.class, NumberUtils.LONG_ONE);
            Transaction tx = session.beginTransaction();

            entity.setName("Updated entity");

            session.saveOrUpdate(entity);
            tx.commit();

            assertEquals(NumberUtils.LONG_ONE, entity.getId());
            assertEquals("Updated entity", entity.getName());
        }
    }

    @Test
    @DisplayName("Get a non-existent entity by identifier")
    void testGetMissingEntity() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            FirstEntity missingEntity = session.get(FirstEntity.class, 100L);
            assertNull(missingEntity);

            tx.commit();
        }
    }

    @Test
    @DisplayName("Load a non-existent entity by identifier through proxy")
    void testLoadMissingEntity() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            FirstEntity missingEntity = session.load(FirstEntity.class, 100L);

            ObjectNotFoundException thrown =
                    assertThrows(ObjectNotFoundException.class,
                            missingEntity::getName);

            assertNotNull(thrown);

            tx.commit();
        }
    }

    @Test
    void firstTestSaveEntityWithoutExplicitlyAssignedId() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            FirstEntity entity = new FirstEntity();
            entity.setName("Another first entity");

            session.save(entity);
            assertNotNull(entity.getId());
            assertEquals("Another first entity", entity.getName());

            tx.commit();
        }
    }

    @Test
    void secondTestSaveEntityWithoutExplicitlyAssignedId() {
        try (Session session = SessionUtil.getSession()) {
            SecondEntity entity = new SecondEntity();
            entity.setName("Another second entity");

            assertThrows(IdentifierGenerationException.class,
                    () -> session.save(entity));
        }
    }

    @Test
    void thirdTestSaveEntityWithExplicitlyAssignedId() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            SecondEntity entity = new SecondEntity();
            entity.setId(2L);
            entity.setName("Another second entity");

            session.save(entity);
            assertNotNull(entity.getId());
            assertEquals("Another second entity", entity.getName());

            tx.commit();
        }
    }

    @Test
    void testMergeEntity() {
        try (Session session = SessionUtil.getSession()) {
            SecondEntity entity = session.load(SecondEntity.class, NumberUtils.LONG_ONE);
            entity.setName("Merged to the database entity");

            Transaction tx = session.beginTransaction();

            session.merge(entity);

            tx.commit();

            entity = session.load(SecondEntity.class, NumberUtils.LONG_ONE);

            assertEquals("Merged to the database entity", entity.getName());
            assertEquals(NumberUtils.LONG_ONE, entity.getId());
        }
    }

    @Test
    void testSaveChangesOnPersistentState() {
        SecondEntity entity;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            entity = session.load(SecondEntity.class, NumberUtils.LONG_ONE);
            entity.setName("Modified entity");

            tx.commit();
        }

        assertEquals("Modified entity", entity.getName());
        assertEquals(NumberUtils.LONG_ONE, entity.getId());
    }

    @Test
    void testRefreshEntity() {
        SecondEntity refreshedEntity;
        try (Session session = SessionUtil.getSession()) {
            refreshedEntity = session.load(SecondEntity.class, NumberUtils.LONG_ONE);
            refreshedEntity.setName("out of synch");
        }

        assertEquals("out of synch", refreshedEntity.getName());
        assertEquals(NumberUtils.LONG_ONE, refreshedEntity.getId());

        try (Session session = SessionUtil.getSession()) {
            session.refresh(refreshedEntity);
        }

        assertNotEquals("out of synch", refreshedEntity.getName());
        assertEquals(NumberUtils.LONG_ONE, refreshedEntity.getId());
    }

    @Test
    void testDirtySession() {
        SecondEntity entity;
        try (Session session = SessionUtil.getSession()) {
            entity = session.load(SecondEntity.class, NumberUtils.LONG_ONE);

            assertFalse(session.isDirty());

            entity.setName("some changes");

            assertTrue(session.isDirty());
        }
    }

    @Test
    void testDeletePersistentEntityWithLoadingToMemory() {
        SecondEntity entity;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            entity = session.load(SecondEntity.class, NumberUtils.LONG_ONE);

            session.delete(entity);

            tx.commit();
        }

        assertNotNull(entity);

        try (Session session = SessionUtil.getSession()) {
            entity = session.get(SecondEntity.class, NumberUtils.LONG_ONE);
        }

        assertNull(entity);
    }

    @Test
    void testDeleteThroughTransientObjectWithLoadingToMemory() {
        SecondEntity entity = new SecondEntity();
        entity.setId(2L);
        entity.setName("for removing");

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            session.delete(entity);

            tx.commit();
        }

        assertNotNull(entity);

        try (Session session = SessionUtil.getSession()) {
            entity = session.get(SecondEntity.class, 2L);
        }

        assertNull(entity);
    }

    @Test
    void testForIdentityAndEquality() {
        Long id;
        SecondEntity entity0;

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            SecondEntity secondEntity = new SecondEntity();
            secondEntity.setId(NumberUtils.LONG_MINUS_ONE);
            secondEntity.setName("Second entity");

            session.save(secondEntity);

            assertNotNull(secondEntity.getId());
            assertEquals("Second entity", secondEntity.getName());

            tx.commit();
        }

        try(Session session = SessionUtil.getSession()) {
            entity0 = session.load(SecondEntity.class, NumberUtils.LONG_MINUS_ONE);
            id = entity0.getId();

            assertEquals(NumberUtils.LONG_MINUS_ONE, id);
            assertEquals("Second entity", entity0.getName());
        }

        try(Session session = SessionUtil.getSession()) {
            SecondEntity entity1 = session.load(SecondEntity.class, id);
            assertEquals("Second entity", entity1.getName());
            assertEquals(NumberUtils.LONG_MINUS_ONE, entity1.getId());

            SecondEntity entity2 = session.load(SecondEntity.class, id);
            assertEquals("Second entity", entity2.getName());
            assertEquals(NumberUtils.LONG_MINUS_ONE, entity2.getId());

            assertEquals(entity1, entity2);
            assertTrue(entity2 == entity1);

            assertEquals(entity0, entity1);
            assertFalse(entity0 == entity1);
        }
    }
}
