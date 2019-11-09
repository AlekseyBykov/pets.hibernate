//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.inheritance.strategies.singletable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-11-09
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
