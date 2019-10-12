//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.entities.mapping.one2one.fkassoc.bidirectional;

import common.utils.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaQuery;

import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-06-05
 */
class OneToOneTest {

    @Test
    void testSaveOneToOneBidirectionalRelationship() {
        Book book = new Book();
        book.setTitle("Some book");

        Publisher publisher = new Publisher();
        publisher.setName("Some publisher");
        publisher.setBook(book);

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            session.save(publisher);

            tx.commit();
        }
    }

    @Test
    void readChildRecordThroughParent() {
        try(Session session = SessionUtil.getSession()) {
            CriteriaQuery<Publisher> criteriaQuery = session.getCriteriaBuilder().createQuery(Publisher.class);
            criteriaQuery.from(Publisher.class);

            Publisher publisher = session.createQuery(criteriaQuery).uniqueResult();
            assertNotNull(publisher);

            Book book = publisher.getBook();
            assertNotNull(book);
        }
    }

    @Test
    void readParentRecordThroughChild() {
        try(Session session = SessionUtil.getSession()) {
            CriteriaQuery<Book> criteriaQuery = session.getCriteriaBuilder().createQuery(Book.class);
            criteriaQuery.from(Book.class);

            Book book = session.createQuery(criteriaQuery).uniqueResult();
            assertNotNull(book);

            Publisher publisher = book.getPublisher();
            assertNotNull(publisher);
        }
    }
}
