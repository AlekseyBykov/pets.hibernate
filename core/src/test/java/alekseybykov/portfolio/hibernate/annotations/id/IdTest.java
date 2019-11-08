//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.annotations.id;

import alekseybykov.portfolio.hibernate.TestContextHook;
import alekseybykov.portfolio.hibernate.entities.AutoIdentifiedEntity;
import alekseybykov.portfolio.hibernate.entities.ManuallyIdentifiedEntity;
import common.utils.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.id.IdentifierGenerationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-11-08
 */
@DisplayName("Tests for identifiers concepts")
@ExtendWith({TestContextHook.class})
class IdTest {

    @Test
    void testSaveAutoIdentifiedEntityWithoutExplicitlyAssignedId() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            AutoIdentifiedEntity entity = new AutoIdentifiedEntity();
            entity.setName("Another first entity");

            entity.setId(321L); // will be ignored

            session.save(entity);

            Long id = entity.getId();

            assertNotNull(id);
            assertNotEquals(321L, id);
            assertEquals("Another first entity", entity.getName());

            tx.commit();
        }
    }

    @Test
    void testSaveManuallyIdentifiedEntityWithoutExplicitlyAssignedId() {
        try (Session session = SessionUtil.getSession()) {

            ManuallyIdentifiedEntity entity = new ManuallyIdentifiedEntity();
            entity.setName("Another second entity");

            assertThrows(IdentifierGenerationException.class,
                () -> session.save(entity));
        }
    }

    @Test
    void testSaveManuallyIdentifiedEntityWithExplicitlyAssignedId() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            ManuallyIdentifiedEntity entity = new ManuallyIdentifiedEntity();
            entity.setId(2L);
            entity.setName("Another second entity");

            session.save(entity);
            assertNotNull(entity.getId());
            assertEquals("Another second entity", entity.getName());

            tx.commit();
        }
    }
}
