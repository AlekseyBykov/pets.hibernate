//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.context;

import alekseybykov.portfolio.hibernate.TestContextHook;
import alekseybykov.portfolio.hibernate.annotations.id.AutoIdentifiedEntity;
import common.utils.SessionUtil;
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
    @DisplayName("Change entity states")
    void testChangeObjectStates() {

        Long id;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            // entity in transient state
            AutoIdentifiedEntity entity = new AutoIdentifiedEntity();
            entity.setName("A");

            // similarly -- session.persist(entity);
            session.save(entity);

            id = entity.getId();

            assertNotNull(entity.getId());
            assertEquals("A", entity.getName());

            // entity in persistent state
            entity.setName("a");
            entity.setName("b");
            entity.setName("c");
            entity.setName("d");

            tx.commit();
        }

        // session closed, entity in detached state

        try (Session session = SessionUtil.getSession()) {
            AutoIdentifiedEntity entity = session.load(AutoIdentifiedEntity.class, id);

            // entity in persistent state

            assertEquals("d", entity.getName());

            entity.setName("e");

            // entity in detached state, changes will be ignored
            session.detach(entity);

            Transaction tx = session.beginTransaction();

            entity.setName("f");
            entity.setName("g");
            entity.setName("h");

            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            AutoIdentifiedEntity entity = session.load(AutoIdentifiedEntity.class, id);
            assertNotEquals("h", entity.getName());
            assertEquals("d", entity.getName());

            Transaction tx = session.beginTransaction();

            entity.setName("i");
            session.merge(entity);

            tx.commit();

            // entity in persistent state
        }

        try (Session session = SessionUtil.getSession()) {
            AutoIdentifiedEntity entity = session.load(AutoIdentifiedEntity.class, id);
            assertEquals("i", entity.getName());
        }
    }
}
