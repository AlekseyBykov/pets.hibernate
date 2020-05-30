package alekseybykov.portfolio.hibernate.session;

import alekseybykov.portfolio.hibernate.TestContextHook;
import alekseybykov.portfolio.hibernate.annotations.id.AutoIdentifiedEntity;
import common.utils.SessionUtil;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Aleksey Bykov
 * @since 09.11.2019
 */
@ExtendWith({TestContextHook.class})
@DisplayName("Tests for reading entities")
class ReadingEntitiesTest {

    @Test
    @DisplayName("Get a non-existent entity by identifier")
    void testGetMissingEntity() {
        try (Session session = SessionUtil.getSession()) {
            AutoIdentifiedEntity missingEntity = session.get(AutoIdentifiedEntity.class, 100L);
            assertNull(missingEntity);
        }
    }

    @Test
    @DisplayName("Load a non-existent entity by identifier through proxy")
    void testLoadMissingEntityThroughProxy() {
        try (Session session = SessionUtil.getSession()) {
            assertThrows(ObjectNotFoundException.class,
                    () -> session.load(AutoIdentifiedEntity.class, 100L));
        }
    }
}
