//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate;

import alekseybykov.portfolio.hibernate.configuration.FlywayProperties;
import common.utils.SessionUtil;
import org.flywaydb.core.Flyway;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-10-13
 */
public class TestContextHook implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

    private static final FlywayProperties properties = FlywayProperties.getInstance();

    private static boolean started = false;

    @Override
    public void beforeAll(ExtensionContext context) {
        if (!started) {
            started = true;
            Flyway flyway = Flyway.configure()
                    .dataSource(properties.getProperty("dataSourceUrl"),
                            properties.getProperty("user"),
                            properties.getProperty("password"))
                    .schemas(properties.getProperty("scheme"))
                    .locations(properties.getProperty("location"))
                    .baselineOnMigrate(Boolean.valueOf(properties.getProperty("baselineOnMigrate")))
                    .outOfOrder(Boolean.valueOf(properties.getProperty("outOfOrder")))
                    .load();

            flyway.clean();
            flyway.repair();
            flyway.migrate();

            context.getRoot().getStore(GLOBAL).put("TestContextHook", this);
        }
    }

    @Override
    public void close() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            // bulk delete
            session.createQuery("delete from AutoIdentifiedEntity").executeUpdate();
            session.createQuery("delete from ManuallyIdentifiedEntity").executeUpdate();
            session.createQuery("delete from Student").executeUpdate();
            session.createQuery("delete from University").executeUpdate();
            session.createQuery("delete from Publisher").executeUpdate();
            session.createQuery("delete from Book").executeUpdate();
            session.createQuery("delete from Person").executeUpdate();
            session.createQuery("delete from Passport").executeUpdate();
            session.createQuery("delete from Week").executeUpdate();
            session.createQuery("delete from Report").executeUpdate();
            session.createQuery("delete from EmploymentAgreement").executeUpdate();
            session.createQuery("delete from BusinessPlan").executeUpdate();
            session.createQuery("delete from Document").executeUpdate();

            tx.commit();
        }
    }
}
