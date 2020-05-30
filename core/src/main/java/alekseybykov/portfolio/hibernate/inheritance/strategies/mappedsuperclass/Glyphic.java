package alekseybykov.portfolio.hibernate.inheritance.strategies.mappedsuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Aleksey Bykov
 * @since 12.11.2019
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
