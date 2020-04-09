package alekseybykov.portfolio.hibernate.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.util.Properties;

import static java.util.Objects.isNull;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-10-12
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FlywayProperties extends Properties {

    private static FlywayProperties instance = null;

    public static FlywayProperties getInstance() {
        if (isNull(instance)) {
            final String resourceName = "flyway.properties";
            try (InputStream is = ClassLoader.getSystemResourceAsStream(resourceName)) {
                instance = new FlywayProperties();
                instance.load(is);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return instance;
    }
}
