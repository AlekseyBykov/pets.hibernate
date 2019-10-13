//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.entities.mapping.many2one.bidirectional;

import alekseybykov.portfolio.hibernate.TestContextHook;
import common.utils.SessionUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
@ExtendWith({TestContextHook.class})
class ManyToOneTest  {

    @Test
    void testSaveManyToOneBidirectionalRelationship() {
        University university = new University();
        university.setName("University");

        Student firstStudent = new Student();
        firstStudent.setFirstName("First Student");
        firstStudent.setUniversity(university);

        Student secondStudent = new Student();
        secondStudent.setFirstName("Second Student");
        secondStudent.setUniversity(university);

        Student thirdStudent = new Student();
        thirdStudent.setFirstName("Third Student");
        thirdStudent.setUniversity(university);

        List<Student> allTheStudents = new ArrayList<>();

        allTheStudents.add(firstStudent);
        allTheStudents.add(secondStudent);
        allTheStudents.add(thirdStudent);

        university.setStudents(allTheStudents);

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            session.save(university);

            tx.commit();
        }

        // reading child records through parent
        try(Session session = SessionUtil.getSession()) {
            CriteriaQuery<University> criteriaQuery = session.getCriteriaBuilder().createQuery(University.class);
            criteriaQuery.from(University.class);

            University loadedUniversity = session.createQuery(criteriaQuery).uniqueResult();
            assertNotNull(loadedUniversity);

            List<Student> students = loadedUniversity.getStudents();
            assertNotNull(students);
            assertTrue(students.size() == 3);
        }

        // read parent record through childs
        try(Session session = SessionUtil.getSession()) {
            CriteriaQuery<Student> criteriaQuery = session.getCriteriaBuilder().createQuery(Student.class);
            criteriaQuery.from(Student.class);

            List<Student> students = session.createQuery(criteriaQuery).getResultList();
            assertNotNull(students);

            assertTrue(students.size() == 3);

            Set<University> universities = students.stream()
                    .map(Student::getUniversity)
                    .collect(Collectors.toSet());

            assertTrue(universities.size() == NumberUtils.INTEGER_ONE);
        }
    }
}
