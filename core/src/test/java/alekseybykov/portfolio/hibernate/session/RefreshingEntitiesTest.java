//
// Feel free to use these solutions in your work.
//
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

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-11-09
 */
@ExtendWith({TestContextHook.class})
@DisplayName("Tests for refreshing entities")
class RefreshingEntitiesTest {

    @Test
    @DisplayName("Save and detach entity and then refresh original state from the database")
    void testRefreshDetachedEntitysStateFromTheDatabase() {
        AutoIdentifiedEntity entity;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            entity = new AutoIdentifiedEntity();
            entity.setName("n");

            session.save(entity);

            tx.commit();
        }

        // will be ignored
        entity.setName("a");
        entity.setName("b");
        entity.setName("c");
        entity.setName("d");

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.refresh(entity);
            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            AutoIdentifiedEntity loadedEntity = session.load(AutoIdentifiedEntity.class, entity.getId());
            assertEquals(loadedEntity.getId(), entity.getId());

            assertEquals("n", loadedEntity.getName());
            assertEquals("n", entity.getName());
        }
    }
}
