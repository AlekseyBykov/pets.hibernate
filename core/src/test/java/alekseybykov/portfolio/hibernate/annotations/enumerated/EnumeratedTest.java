package alekseybykov.portfolio.hibernate.annotations.enumerated;

import alekseybykov.portfolio.hibernate.TestContextHook;
import common.utils.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-11-08
 */
@DisplayName("Tests for mapping of enums")
@ExtendWith({TestContextHook.class})
class EnumeratedTest {

    @Test
    @DisplayName("Save day of week in string and numerical representation")
    void testSaveDayOfWeekAsStringAndAsInt() {
        Long id;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Week week = new Week();
            week.setDayInOrdinal(Day.FRIDAY);
            week.setDayInString(Day.FRIDAY);

            session.save(week);

            id = week.getId();

            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            Week week = session.load(Week.class, id);

            week.setDayInOrdinal(Day.FRIDAY);
            week.setDayInString(Day.FRIDAY);

            assertEquals(Day.FRIDAY.name(), week.getDayInString().name());
            assertEquals(Day.FRIDAY.ordinal(), week.getDayInOrdinal().ordinal());
        }
    }
}
