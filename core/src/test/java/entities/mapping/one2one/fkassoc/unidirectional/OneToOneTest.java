package entities.mapping.one2one.fkassoc.unidirectional;

import common.utils.SessionUtil;
import org.apache.commons.lang.ClassUtils;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.testng.annotations.Test;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 1.0
 * @since   2019-06-05
 */
public class OneToOneTest {

    @Test(priority = 0)
    public void testSaveOneToOneUnidirectionalRelationship() {
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

            assertTrue(listOfPersons.size() == 1);
        }
    }

    @Test(priority = 1)
    public void readByUsingCriteria() {
        try(Session session = SessionUtil.getSession()) {
            CriteriaQuery<Person> criteriaQuery = session.getCriteriaBuilder().createQuery(Person.class);
            criteriaQuery.from(Person.class);

            List<Person> persons = session.createQuery(criteriaQuery).getResultList();
            assertTrue(persons.size() == 1);
        }
    }
}