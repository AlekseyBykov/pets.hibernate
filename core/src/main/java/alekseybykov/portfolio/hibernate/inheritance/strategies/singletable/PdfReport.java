package alekseybykov.portfolio.hibernate.inheritance.strategies.singletable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * @author Aleksey Bykov
 * @since 09.11.2019
 */
@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PdfReport extends Report {

    private String typeface;

    public PdfReport(String format, String typeface) {
        super(format);
        this.typeface = typeface;
    }
}
