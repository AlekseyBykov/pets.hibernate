package alekseybykov.portfolio.hibernate.annotations.id;

import alekseybykov.portfolio.hibernate.TestContextHook;
import common.utils.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.id.IdentifierGenerationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Aleksey Bykov
 * @since 08.11.2019
 */
@DisplayName("Tests for identifiers concepts")
@ExtendWith({TestContextHook.class})
class IdTest {

    private final String name = "Entity";

    @Test
    @DisplayName("Save auto identified entity without explicitly assigned identifier")
    void testSaveAutoIdentifiedEntityWithoutExplicitlyAssignedId() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            AutoIdentifiedEntity entity = new AutoIdentifiedEntity();
            entity.setName(name);

            entity.setId(321L); // will be ignored

            session.save(entity);

            Long id = entity.getId();

            assertNotNull(id);
            assertNotEquals(321L, id);
            assertEquals(name, entity.getName());

            tx.commit();
        }
    }

    @Test
    @DisplayName("Save manually identified entity without explicitly assigned identifier")
    void testSaveManuallyIdentifiedEntityWithoutExplicitlyAssignedId() {
        try (Session session = SessionUtil.getSession()) {

            ManuallyIdentifiedEntity entity = new ManuallyIdentifiedEntity();
            entity.setName(name);

            assertThrows(IdentifierGenerationException.class,
                () -> session.save(entity));
        }
    }

    @Test
    @DisplayName("Save manually identified entity with explicitly assigned identifier")
    void testSaveManuallyIdentifiedEntityWithExplicitlyAssignedId() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            ManuallyIdentifiedEntity entity = new ManuallyIdentifiedEntity();
            entity.setId(2L);
            entity.setName(name);

            session.save(entity);
            assertNotNull(entity.getId());
            assertEquals(name, entity.getName());

            tx.commit();
        }
    }
}
