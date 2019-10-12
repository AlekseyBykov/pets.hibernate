//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.entities;

import alekseybykov.portfolio.hibernate.configuration.FlywayConfiguration;
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

    @BeforeAll
    public static void setup() {
        Flyway flyway = Flyway.configure()
                .dataSource(FlywayConfiguration.dataSourceUrl,
                            FlywayConfiguration.user,
                            FlywayConfiguration.password)
                .schemas(FlywayConfiguration.scheme)
                .locations(FlywayConfiguration.location)
                .baselineOnMigrate(FlywayConfiguration.baselineOnMigrate)
                .outOfOrder(FlywayConfiguration.outOfOrder)
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
