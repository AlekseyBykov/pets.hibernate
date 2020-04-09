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
@DisplayName("Tests for saving entities")
class SavingEntitiesTest {

    @Test
    @DisplayName("Save entity")
    void testSaveEntity() {
        Long id;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            AutoIdentifiedEntity entity = new AutoIdentifiedEntity();
            entity.setName("a");

            session.save(entity);
            id = entity.getId();

            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            AutoIdentifiedEntity entity = session.load(AutoIdentifiedEntity.class, id);
            assertEquals("a", entity.getName());
            assertEquals(id, entity.getId());
        }
    }
}
