//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.psql.functions;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

/**
 * Registering with some customization of SQL function
 * before using it in JPQL or Criteria API.
 *
 * @author  aleksey.n.bykov@gmail.com
 * @version 1.0
 * @since   2019-09-02
 */
public class CustomSqlFunctions implements MetadataBuilderContributor {
    @Override
    public void contribute(MetadataBuilder metadataBuilder) {
        metadataBuilder.applySqlFunction(
                "truncate_for_minutes",
                new SQLFunctionTemplate(
                        StandardBasicTypes.TIMESTAMP,
                        "date_trunc('minute', (?1 AT TIME ZONE ?2))"
                )
        );
    }
}
