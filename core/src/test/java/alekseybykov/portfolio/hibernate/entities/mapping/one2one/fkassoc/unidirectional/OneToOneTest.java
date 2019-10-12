//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.entities.mapping.one2one.fkassoc.unidirectional;

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
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-06-05
 */
class OneToOneTest {

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
    }

    @Test
    void readByUsingCriteria() {
        try(Session session = SessionUtil.getSession()) {
            CriteriaQuery<Person> criteriaQuery = session.getCriteriaBuilder().createQuery(Person.class);
            criteriaQuery.from(Person.class);

            List<Person> persons = session.createQuery(criteriaQuery).getResultList();
            assertTrue(persons.size() == NumberUtils.INTEGER_ONE);
        }
    }
}
