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
public class Modern extends Typeface {

    @Column(name = "vertical_axis_height")
    private Double verticalAxisHeight;

    public Modern(String name, Double verticalAxisHeight) {
        super(name);
        this.verticalAxisHeight = verticalAxisHeight;
    }
}
