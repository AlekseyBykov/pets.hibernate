package entities.mapping.one2one;

import common.utils.SessionUtil;
import entities.mapping.one2one.unidirectional.*;
import org.apache.commons.lang.ClassUtils;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertTrue;

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
}