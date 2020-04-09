package alekseybykov.portfolio.hibernate.session;

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
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-11-10
 */
@ExtendWith({TestContextHook.class})
@DisplayName("Tests for deleting entities")
class DeletingEntitiesTest {

    @Test
    @DisplayName("Save entity and then remove from the db by using persistent object")
    void testRemoveEntityFromDatabaseWithLoadingToMemory() {
        Long id;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            AutoIdentifiedEntity entity = new AutoIdentifiedEntity();
            entity.setName("z");

            session.save(entity);
            id = entity.getId();

            tx.commit();
        }

        AutoIdentifiedEntity loadedEntity;
        try (Session session = SessionUtil.getSession()) {
            loadedEntity = session.load(AutoIdentifiedEntity.class, id);

            assertEquals(id, loadedEntity.getId());

            Transaction tx = session.beginTransaction();

            // entity was loaded into memory before deleting
            session.delete(loadedEntity);
            tx.commit();
        }

        // removed state, but still exists in memory (waiting for GC)
        try (Session session = SessionUtil.getSession()) {
            AutoIdentifiedEntity entity = session.get(AutoIdentifiedEntity.class, id);

            assertNull(entity);
            assertNotNull(loadedEntity);
        }
    }
}
