package by.valvik.bannermanagement.security.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "site_admin")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "{login.mandatory}")
    @Size(max = 255, message = "{login.length}")
    @Column(name = "login", nullable = false)
    private String login;

    @NotBlank(message = "{password.mandatory}")
    @Size(max = 255, message = "{password.length}")
    @Column(name = "password", nullable = false)
    private String password;

}
