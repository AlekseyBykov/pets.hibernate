//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.annotations.id;

import alekseybykov.portfolio.hibernate.TestContextHook;
import alekseybykov.portfolio.hibernate.entities.FirstEntity;
import alekseybykov.portfolio.hibernate.entities.SecondEntity;
import common.utils.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.id.IdentifierGenerationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-11-08
 */
@ExtendWith({TestContextHook.class})
class IdTest {

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
}
