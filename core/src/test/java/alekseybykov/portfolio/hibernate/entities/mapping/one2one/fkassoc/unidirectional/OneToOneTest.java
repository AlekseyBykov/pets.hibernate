//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.entities.mapping.one2one.fkassoc.unidirectional;

import alekseybykov.portfolio.hibernate.entities.TestBase;
import common.utils.SessionUtil;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Any method on Session object fire off some event.
 * For example, session.save() will be translated to SaveOrUpdateEvent.
 * All the event is queued in ActionQueue.
 * At the end of the unit of work this actions are performed
 * in database (as DML).
 *
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-06-05
 */
class OneToOneTest extends TestBase {

    @Test
    void testSaveOneToOneUnidirectionalRelationship() {
        Passport passport = new Passport();
        passport.setPassportNo("1111");

        Person person = new Person();
        person.setName("Person 1");
        person.setPassport(passport);

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            session.save(person);

            tx.commit();
        }

        try(Session session = SessionUtil.getSession()) {
            Query<Person> queryForPersons = session.createQuery(
                String.format("from %s ", ClassUtils.getShortClassName(Person.class))
            );

            List<Person> listOfPersons = queryForPersons.list();

            assertTrue(listOfPersons.size() == NumberUtils.INTEGER_ONE);
        }

        // reading by using Criteria API
        try(Session session = SessionUtil.getSession()) {
            CriteriaQuery<Person> criteriaQuery = session.getCriteriaBuilder().createQuery(Person.class);
            criteriaQuery.from(Person.class);

            List<Person> persons = session.createQuery(criteriaQuery).getResultList();
            assertTrue(persons.size() == NumberUtils.INTEGER_ONE);
        }
    }
}
