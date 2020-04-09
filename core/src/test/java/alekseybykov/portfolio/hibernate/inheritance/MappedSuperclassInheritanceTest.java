package alekseybykov.portfolio.hibernate.inheritance;

import alekseybykov.portfolio.hibernate.TestContextHook;
import alekseybykov.portfolio.hibernate.inheritance.strategies.mappedsuperclass.Glyphic;
import alekseybykov.portfolio.hibernate.inheritance.strategies.mappedsuperclass.Modern;
import alekseybykov.portfolio.hibernate.inheritance.strategies.mappedsuperclass.Typeface;
import common.utils.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-11-12
 */
@ExtendWith({TestContextHook.class})
@DisplayName("Tests for MappedSuperclass inheritance strategy")
class MappedSuperclassInheritanceTest {

    @Test
    @DisplayName("Save hierarchy into two different tables")
    void testSaveHierarchyIntoTwoDifferentTables() {
        Long modernTypefaceId, glyphicTypefaceId;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Typeface modernTypeface = new Modern("modern", 11.11);
            Typeface glyphicTypeface = new Glyphic("glyphic", 10.1);

            session.persist(modernTypeface);
            session.persist(glyphicTypeface);

            modernTypefaceId = modernTypeface.getId();
            glyphicTypefaceId = glyphicTypeface.getId();

            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            Modern modernTypeface = session.load(Modern.class, modernTypefaceId);
            assertEquals("modern", modernTypeface.getName());
            assertEquals(modernTypefaceId, modernTypeface.getId());
            assertEquals(Double.valueOf(11.11), modernTypeface.getVerticalAxisHeight());

            Glyphic glyphicTypeface = session.load(Glyphic.class, glyphicTypefaceId);
            assertEquals("glyphic", glyphicTypeface.getName());
            assertEquals(modernTypefaceId, glyphicTypeface.getId());
            assertEquals(Double.valueOf(10.1), glyphicTypeface.getStrokeWeightContrast());
        }
    }
}
