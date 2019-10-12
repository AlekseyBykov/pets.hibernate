//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.entities.core;

import alekseybykov.portfolio.hibernate.entities.TestBase;
import common.utils.SessionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.id.IdentifierGenerationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-06-09
 */
class CoreConceptsTest extends TestBase {

    @Test
    void saveEntities() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            FirstEntity firstEntity = new FirstEntity();
            firstEntity.setName("First entity");

            session.save(firstEntity);

            assertNotNull(firstEntity.getId());
            assertEquals(firstEntity.getName(), "First entity");

            SecondEntity secondEntity = new SecondEntity();
            secondEntity.setId(NumberUtils.LONG_ONE);
            secondEntity.setName("Second entity");

            session.save(secondEntity);

            assertNotNull(secondEntity.getId());
            assertEquals(secondEntity.getName(), "Second entity");

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
        assertEquals(entity.getName(), "Fourth entity");
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

            assertEquals(entity.getId(), NumberUtils.LONG_ONE);
            assertEquals(entity.getName(), "Updated entity");
        }
    }

    @Test
    void testGetMissingEntity() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            FirstEntity missingEntity = session.get(FirstEntity.class, 100L);
            assertNull(missingEntity);

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
            assertEquals(entity.getName(), "Another first entity");

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
            assertEquals(entity.getName(), "Another second entity");

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

            assertEquals(entity.getName(), "Merged to the database entity");
            assertEquals(entity.getId(), NumberUtils.LONG_ONE);
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

        assertEquals(entity.getName(), "Modified entity");
        assertEquals(entity.getId(), NumberUtils.LONG_ONE);
    }

    @Test
    void testRefreshEntity() {
        SecondEntity refreshedEntity;
        try (Session session = SessionUtil.getSession()) {
            refreshedEntity = session.load(SecondEntity.class, NumberUtils.LONG_ONE);
            refreshedEntity.setName("out of synch");
        }

        assertEquals(refreshedEntity.getName(), "out of synch");
        assertEquals(refreshedEntity.getId(), NumberUtils.LONG_ONE);

        try (Session session = SessionUtil.getSession()) {
            session.refresh(refreshedEntity);
        }

        assertNotEquals(refreshedEntity.getName(), "out of synch");
        assertEquals(refreshedEntity.getId(), NumberUtils.LONG_ONE);
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
}
