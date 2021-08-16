package by.valvik.bannermanagement.domain;

import by.valvik.bannermanagement.validation.annotation.Unique;
import by.valvik.bannermanagement.validation.annotation.UniqueValue;
import by.valvik.bannermanagement.view.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table
@Unique
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class Category extends BaseEntity {

    @NotBlank(message = "{name.mandatory}")
    @Size(max = 255, message = "{name.length}")
    @UniqueValue("category.name")
    @Column(name = "name", nullable = false)
    @JsonView(View.Public.class)
    private String name;
    
    @NotBlank(message = "{request.id.mandatory}")
    @Size(max = 255, message = "{request.id.length}")
    @UniqueValue("request.id")
    @Column(name = "req_name", nullable = false)
    @JsonView(View.Private.class)
    private String reqName;

    @JsonIgnore
    @Column(name = "deleted", nullable = false)
    private Boolean isDeleted;

}
