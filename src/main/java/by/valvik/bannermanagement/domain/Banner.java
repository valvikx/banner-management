package by.valvik.bannermanagement.domain;

import by.valvik.bannermanagement.validation.annotation.Unique;
import by.valvik.bannermanagement.validation.annotation.UniqueValue;
import by.valvik.bannermanagement.view.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table
@Unique
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class Banner extends BaseEntity {

    @NotBlank(message = "{name.mandatory}")
    @Size(max = 255, message = "{name.length}")
    @UniqueValue("banner.name")
    @Column(name = "name", nullable = false)
    @JsonView(View.Private.class)
    private String name;

    @NotNull(message = "{price.mandatory}")
    @DecimalMin(value = "0.01", inclusive = false, message = "{price.value}")
    @Digits(integer = 8, fraction = 2, message = "{price.out.range}")
    @Column(name ="price", nullable = false, precision = 8, scale = 2)
    @JsonView(View.Private.class)
    private BigDecimal price;

    @NotNull(message = "{category.mandatory}")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JsonView(View.Public.class)
    private Category category;

    @NotBlank(message = "{content.mandatory}")
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    @JsonView(View.Public.class)
    private String content;

    @JsonIgnore
    @Column(name = "deleted", nullable = false)
    private Boolean isDeleted;

}