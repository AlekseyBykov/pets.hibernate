package alekseybykov.portfolio.hibernate.mapping.one2one.fkassoc.bidirectional;

import alekseybykov.portfolio.hibernate.TestContextHook;
import common.utils.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.criteria.CriteriaQuery;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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

        // reading child record through parent
        try(Session session = SessionUtil.getSession()) {
            CriteriaQuery<Publisher> criteriaQuery = session.getCriteriaBuilder().createQuery(Publisher.class);
            criteriaQuery.from(Publisher.class);

            Publisher loadedPublisher = session.createQuery(criteriaQuery).uniqueResult();
            assertNotNull(loadedPublisher);

            Book loadedBook = loadedPublisher.getBook();
            assertNotNull(loadedBook);
        }

        // read parent record through child
        try(Session session = SessionUtil.getSession()) {
            CriteriaQuery<Book> criteriaQuery = session.getCriteriaBuilder().createQuery(Book.class);
            criteriaQuery.from(Book.class);

            Book loadedBook = session.createQuery(criteriaQuery).uniqueResult();
            assertNotNull(loadedBook);

            Publisher loadedPublisher = loadedBook.getPublisher();
            assertNotNull(loadedPublisher);
        }
    }
}
