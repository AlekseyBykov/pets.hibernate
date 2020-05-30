package alekseybykov.portfolio.hibernate.session;

import alekseybykov.portfolio.hibernate.TestContextHook;
import alekseybykov.portfolio.hibernate.annotations.id.AutoIdentifiedEntity;
import alekseybykov.portfolio.hibernate.annotations.id.ManuallyIdentifiedEntity;
import common.utils.SessionUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Aleksey Bykov
 * @since 12.11.2019
 */
@ExtendWith({TestContextHook.class})
@DisplayName("Tests for identity and equality")
class IdentityEqualityTest {

    @Test
    @DisplayName("Save entity and then obtain the same instance from the same session")
    void testObtainTheSameObjectFromTheSameSession() {
        Long id;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            AutoIdentifiedEntity autoIdentifiedEntity = new AutoIdentifiedEntity();

            session.save(autoIdentifiedEntity);

            id = autoIdentifiedEntity.getId();

            tx.commit();
        }

        try(Session session = SessionUtil.getSession()) {
            AutoIdentifiedEntity firstEntity = session.load(AutoIdentifiedEntity.class, id);
            AutoIdentifiedEntity secondEntity = session.load(AutoIdentifiedEntity.class, id);

            // the same instance
            assertEquals(firstEntity, secondEntity);
            assertTrue(firstEntity == secondEntity);
            assertTrue(firstEntity.equals(secondEntity));
        }
    }

    @Test
    @DisplayName("Save entity and then obtain the equivalent instances from the same different sessions")
    void testObtainTheEquivalentObjectsFromTheDifferentSessions() {
        Long id;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            ManuallyIdentifiedEntity manuallyIdentifiedEntity = new ManuallyIdentifiedEntity();
            manuallyIdentifiedEntity.setId(NumberUtils.LONG_MINUS_ONE);
            session.save(manuallyIdentifiedEntity);

            id = manuallyIdentifiedEntity.getId();

            tx.commit();
        }

        ManuallyIdentifiedEntity firstEntity;
        try(Session session = SessionUtil.getSession()) {
            firstEntity = session.load(ManuallyIdentifiedEntity.class, id);
        }

        ManuallyIdentifiedEntity secondEntity;
        try(Session session = SessionUtil.getSession()) {
            secondEntity = session.load(ManuallyIdentifiedEntity.class, id);
        }

        // equivalent instances, but not the same
        assertEquals(firstEntity, secondEntity);
        assertTrue(firstEntity.equals(secondEntity));
        assertFalse(firstEntity == secondEntity);
    }
}
