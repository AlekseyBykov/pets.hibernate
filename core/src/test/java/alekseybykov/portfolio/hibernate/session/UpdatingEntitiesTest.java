package alekseybykov.portfolio.hibernate.session;

import alekseybykov.portfolio.hibernate.TestContextHook;
import alekseybykov.portfolio.hibernate.annotations.id.AutoIdentifiedEntity;
import common.utils.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-11-09
 */
@ExtendWith({TestContextHook.class})
@DisplayName("Tests for updating entities")
class UpdatingEntitiesTest {

    @Test
    @DisplayName("Save and then update entity in the database by changing persistent object")
    void testSaveAndThenUpdateEntityByChangingPersistentObject() {
        Long id;

        // save entity
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            AutoIdentifiedEntity entity = new AutoIdentifiedEntity();
            entity.setName("b");

            session.save(entity);
            id = entity.getId();

            tx.commit();
        }

        // make changes on a persistent object
        try (Session session = SessionUtil.getSession()) {
            AutoIdentifiedEntity entity = session.load(AutoIdentifiedEntity.class, id);
            assertEquals("b", entity.getName());
            assertEquals(id, entity.getId());

            Transaction tx = session.beginTransaction();
            entity.setName("n");
            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            AutoIdentifiedEntity entity = session.load(AutoIdentifiedEntity.class, id);
            assertEquals("n", entity.getName());
            assertEquals(id, entity.getId());
        }
    }

    @Test
    @DisplayName("Save and then update entity in the database by using saveOrUpdate() method")
    void testSaveAndThenUpdateEntityByUsingSaveOrUpdateMethod() {
        Long id;
        AutoIdentifiedEntity entity;

        // save entity
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            entity = new AutoIdentifiedEntity();
            entity.setName("z");

            session.save(entity);
            id = entity.getId();

            tx.commit();
        }

        // call Session.saveOrUpdate()
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            entity.setName("y");
            session.saveOrUpdate(entity);
            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            AutoIdentifiedEntity updatedEntity = session.load(AutoIdentifiedEntity.class, id);
            assertEquals("y", updatedEntity.getName());
            assertEquals(id, updatedEntity.getId());
        }
    }

    @Test
    @DisplayName("Save and then update entity in the database by using save() method")
    void testSaveAndThenUpdateEntityWrongWay() {
        Long id;
        AutoIdentifiedEntity entity;

        // save entity
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            entity = new AutoIdentifiedEntity();
            entity.setName("q");

            session.save(entity);
            id = entity.getId();

            tx.commit();
        }

        // call Session.save() will create new row in db
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            entity.setName("k");
            session.save(entity);
            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            AutoIdentifiedEntity initiallySavedEntity = session.load(AutoIdentifiedEntity.class, id);
            // two different entities
            assertNotEquals(entity.getId(), initiallySavedEntity.getId());
        }
    }
}