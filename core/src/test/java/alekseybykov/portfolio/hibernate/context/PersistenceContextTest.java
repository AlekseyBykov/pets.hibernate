//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.context;

import alekseybykov.portfolio.hibernate.TestContextHook;
import alekseybykov.portfolio.hibernate.entities.AutoIdentifiedEntity;
import alekseybykov.portfolio.hibernate.entities.ManuallyIdentifiedEntity;
import common.utils.SessionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
    @DisplayName("Change entity state from transient to persistent")
    void testChangeObjectStateFromTransientToPersistent() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            AutoIdentifiedEntity firstEntity = new AutoIdentifiedEntity();
            firstEntity.setName("First entity");

            session.save(firstEntity);

            assertNotNull(firstEntity.getId());
            assertEquals("First entity", firstEntity.getName());

            ManuallyIdentifiedEntity secondEntity = new ManuallyIdentifiedEntity();
            secondEntity.setId(-2L);
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
        AutoIdentifiedEntity entity;

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            entity = new AutoIdentifiedEntity();
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

            ManuallyIdentifiedEntity secondEntity = new ManuallyIdentifiedEntity();
            secondEntity.setId(NumberUtils.LONG_ONE);
            secondEntity.setName(StringUtils.EMPTY);

            session.save(secondEntity);

            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            ManuallyIdentifiedEntity entity = session.load(ManuallyIdentifiedEntity.class, NumberUtils.LONG_ONE);
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

            AutoIdentifiedEntity missingEntity = session.get(AutoIdentifiedEntity.class, 100L);
            assertNull(missingEntity);

            tx.commit();
        }
    }

    @Test
    @DisplayName("Load a non-existent entity by identifier through proxy")
    void testLoadMissingEntity() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            assertThrows(ObjectNotFoundException.class,
                    () -> session.load(AutoIdentifiedEntity.class, 100L));

            tx.commit();
        }
    }

    @Test
    void testMergeEntity() {
        try (Session session = SessionUtil.getSession()) {
            ManuallyIdentifiedEntity entity = session.load(ManuallyIdentifiedEntity.class, NumberUtils.LONG_ONE);
            entity.setName("Merged to the database entity");

            Transaction tx = session.beginTransaction();

            session.merge(entity);

            tx.commit();

            entity = session.load(ManuallyIdentifiedEntity.class, NumberUtils.LONG_ONE);

            assertEquals("Merged to the database entity", entity.getName());
            assertEquals(NumberUtils.LONG_ONE, entity.getId());
        }
    }

    @Test
    void testSaveChangesOnPersistentState() {
        ManuallyIdentifiedEntity entity;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            entity = session.load(ManuallyIdentifiedEntity.class, NumberUtils.LONG_ONE);
            entity.setName("Modified entity");

            tx.commit();
        }

        assertEquals("Modified entity", entity.getName());
        assertEquals(NumberUtils.LONG_ONE, entity.getId());
    }

    @Test
    void testRefreshEntity() {
        ManuallyIdentifiedEntity refreshedEntity;
        try (Session session = SessionUtil.getSession()) {
            refreshedEntity = session.load(ManuallyIdentifiedEntity.class, NumberUtils.LONG_ONE);
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
        ManuallyIdentifiedEntity entity;
        try (Session session = SessionUtil.getSession()) {
            entity = session.load(ManuallyIdentifiedEntity.class, NumberUtils.LONG_ONE);

            assertFalse(session.isDirty());

            entity.setName("some changes");

            assertTrue(session.isDirty());
        }
    }

    @Test
    void testDeletePersistentEntityWithLoadingToMemory() {
        ManuallyIdentifiedEntity entity;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            entity = session.load(ManuallyIdentifiedEntity.class, NumberUtils.LONG_ONE);

            session.delete(entity);

            tx.commit();
        }

        assertNotNull(entity);

        try (Session session = SessionUtil.getSession()) {
            entity = session.get(ManuallyIdentifiedEntity.class, NumberUtils.LONG_ONE);
        }

        assertNull(entity);
    }

    @Test
    void testDeleteThroughTransientObjectWithLoadingToMemory() {
        ManuallyIdentifiedEntity entity = new ManuallyIdentifiedEntity();
        entity.setId(2L);
        entity.setName("for removing");

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            session.delete(entity);

            tx.commit();
        }

        assertNotNull(entity);

        try (Session session = SessionUtil.getSession()) {
            entity = session.get(ManuallyIdentifiedEntity.class, 2L);
        }

        assertNull(entity);
    }

    @Test
    void testForIdentityAndEquality() {
        Long id;
        ManuallyIdentifiedEntity entity0;

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            ManuallyIdentifiedEntity secondEntity = new ManuallyIdentifiedEntity();
            secondEntity.setId(NumberUtils.LONG_MINUS_ONE);
            secondEntity.setName("Second entity");

            session.save(secondEntity);

            assertNotNull(secondEntity.getId());
            assertEquals("Second entity", secondEntity.getName());

            tx.commit();
        }

        try(Session session = SessionUtil.getSession()) {
            entity0 = session.load(ManuallyIdentifiedEntity.class, NumberUtils.LONG_MINUS_ONE);
            id = entity0.getId();

            assertEquals(NumberUtils.LONG_MINUS_ONE, id);
            assertEquals("Second entity", entity0.getName());
        }

        try(Session session = SessionUtil.getSession()) {
            ManuallyIdentifiedEntity entity1 = session.load(ManuallyIdentifiedEntity.class, id);
            assertEquals("Second entity", entity1.getName());
            assertEquals(NumberUtils.LONG_MINUS_ONE, entity1.getId());

            ManuallyIdentifiedEntity entity2 = session.load(ManuallyIdentifiedEntity.class, id);
            assertEquals("Second entity", entity2.getName());
            assertEquals(NumberUtils.LONG_MINUS_ONE, entity2.getId());

            assertEquals(entity1, entity2);
            assertTrue(entity2 == entity1);

            assertEquals(entity0, entity1);
            assertFalse(entity0 == entity1);
        }
    }
}
