//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.entities;

import alekseybykov.portfolio.hibernate.configuration.FlywayConfiguration;
import org.flywaydb.core.Flyway;
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
}
