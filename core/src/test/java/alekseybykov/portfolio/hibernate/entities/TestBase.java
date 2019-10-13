//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.entities;

import alekseybykov.portfolio.hibernate.configuration.FlywayProperties;
import common.utils.SessionUtil;
import org.flywaydb.core.Flyway;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-10-12
 */
public class TestBase {

    private static final FlywayProperties properties = FlywayProperties.getInstance();

    @BeforeAll
    public static void setup() {
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
    }

    @AfterAll
    static void bulkDeleteWithoutLoadingToMemory() {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            session.createQuery("delete from FirstEntity").executeUpdate();
            session.createQuery("delete from SecondEntity").executeUpdate();
            session.createQuery("delete from Student").executeUpdate();
            session.createQuery("delete from University").executeUpdate();
            session.createQuery("delete from Publisher").executeUpdate();
            session.createQuery("delete from Book").executeUpdate();
            session.createQuery("delete from Person").executeUpdate();
            session.createQuery("delete from Passport").executeUpdate();

            tx.commit();
        }
    }
}
