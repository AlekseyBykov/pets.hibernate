package alekseybykov.portfolio.hibernate.functions.psql;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

/**
 * @author  aleksey.n.bykov@gmail.com
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
