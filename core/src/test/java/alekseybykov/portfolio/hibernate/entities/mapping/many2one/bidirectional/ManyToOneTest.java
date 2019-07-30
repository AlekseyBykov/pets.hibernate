package alekseybykov.portfolio.hibernate.entities.mapping.many2one.bidirectional;

import common.utils.SessionUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.*;
import org.testng.annotations.Test;

import javax.persistence.criteria.CriteriaQuery;
import java.util.*;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 1.0
 * @since   2019-06-05
 */
public class ManyToOneTest {

    @Test(priority = 0)
    public void testSaveManyToOneBidirectionalRelationship() {
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
    }

    @Test(priority = 1)
    public void readChildRecordsThroughParent() {
        try(Session session = SessionUtil.getSession()) {
            CriteriaQuery<University> criteriaQuery = session.getCriteriaBuilder().createQuery(University.class);
            criteriaQuery.from(University.class);

            University university = session.createQuery(criteriaQuery).uniqueResult();
            assertNotNull(university);

            List<Student> students = university.getStudents();
            assertNotNull(students);
            assertTrue(students.size() == 3);
        }
    }

    @Test(priority = 2)
    public void readParentRecordThroughChilds() {
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