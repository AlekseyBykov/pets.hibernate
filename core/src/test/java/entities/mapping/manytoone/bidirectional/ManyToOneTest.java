package entities.mapping.manytoone.bidirectional;

import common.utils.SessionUtil;
import org.apache.commons.lang.ClassUtils;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;


public class ManyToOneTest {
    @Test
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

        try(Session session = SessionUtil.getSession()) {
            Query<Student> queryForStudents = session.createQuery(
                String.format("from %s ", ClassUtils.getShortClassName(Student.class))
            );

            List<Student> listOfStudents = queryForStudents.list();

            assertTrue(listOfStudents.size() == 3);

            Set<University> universities = listOfStudents.stream()
                    .map(Student::getUniversity)
                    .collect(Collectors.toSet());

            assertTrue(universities.size() == 1);

            Query<University> queryForUniversity = session.createQuery(
                String.format("from %s ", ClassUtils.getShortClassName(University.class))
            );

            List<University> listOfUniversities = queryForUniversity.list();

            assertTrue(listOfUniversities.size() == 1);

            University universityEntity = listOfUniversities.get(0);
            List<Student> studentList = universityEntity.getStudents();

            assertTrue(studentList.size() == 3);
        }
    }
}