//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.inheritance.strategies.mappedsuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-11-12
 */
@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Glyphic extends Typeface {

    @Column(name = "stroke_weight_contrast")
    private Double strokeWeightContrast;

    public Glyphic(String name, Double strokeWeightContrast) {
        super(name);
        this.strokeWeightContrast = strokeWeightContrast;
    }
}
