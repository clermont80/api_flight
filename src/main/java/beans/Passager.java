package beans;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "passagers")
@Getter
@Setter
@NoArgsConstructor
public class Passager
{
    @Id
    @SequenceGenerator(name = "passager_sequence_in_java",sequenceName = "passager_sequence_in_database",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passager_sequence_in_java")
    private Integer id;

    @NotBlank(message = "le prenom du passager ne peut être vide")
    @Column(nullable = false)
    private String surname;

    @NotBlank(message = "le nom du passager ne peut être vide")
    @Column(nullable = false)
    private String firstname;

    @NotBlank(message = "l'email du passager ne peut être vide")
    @Column(nullable = false,unique = true)
    private String email_address;
}
