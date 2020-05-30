package alekseybykov.portfolio.hibernate.functions.psql;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

/**
 * @author Aleksey Bykov
 * @since 02.09.2019
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
