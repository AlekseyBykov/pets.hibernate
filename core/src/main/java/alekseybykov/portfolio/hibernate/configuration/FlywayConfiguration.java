//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-10-12
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FlywayConfiguration {

    public static final String dataSourceUrl = "jdbc:postgresql://localhost:5432/hibernate";
    public static final String scheme = "hibernate";
    public static final String location = "classpath:/db/migrations";
    public static final String user = "postgres";
    public static final String password = "root";
    public static final boolean outOfOrder = true;
    public static final boolean baselineOnMigrate = true;
}
