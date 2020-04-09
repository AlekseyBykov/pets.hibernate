package alekseybykov.portfolio.hibernate.session;

import alekseybykov.portfolio.hibernate.TestContextHook;
import alekseybykov.portfolio.hibernate.annotations.id.AutoIdentifiedEntity;
import common.utils.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-11-09
 */
@ExtendWith({TestContextHook.class})
@DisplayName("Tests for merging entities")
class MergingEntitiesTest {

    @Test
    @DisplayName("Save and detach entity and then merge new changes to the database")
    void testMergeDetachedEntitysStateToTheDatabase() {
        Long id;
        AutoIdentifiedEntity entity;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            entity = new AutoIdentifiedEntity();
            entity.setName("l");

            session.save(entity);
            id = entity.getId();

            tx.commit();
        }

        // entity in detached state

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            entity.setName("r");
            session.merge(entity);
            tx.commit();
            // entity in persistent state
        }

        try (Session session = SessionUtil.getSession()) {
            AutoIdentifiedEntity loadedEntity = session.load(AutoIdentifiedEntity.class, id);
            assertEquals(loadedEntity.getId(), entity.getId());

            assertEquals("r", entity.getName());
            assertNotEquals("l", entity.getName());
        }
    }
}
