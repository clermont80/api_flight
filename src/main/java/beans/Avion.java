package beans;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "avions")
@Getter
@Setter
@NoArgsConstructor
public class Avion extends PanacheEntityBase
{
    @Id
    @SequenceGenerator(name = "avions_sequence_in_java",sequenceName = "avions_sequence_in_database",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "avions_sequence_in_java")
    private Integer id;

    @NotBlank(message = "le fabriquant ne doit pas être vide")
    @Column(nullable = false)
    private String operator;

    @NotBlank(message = "le modele ne doit pas être vide")
    @Column(nullable = false)
    private String model;

    @NotBlank(message = "l'immatriculation ne doit pas être vide")
    @Size(max=6,message = "on doit faire au maximum 6 caractères")
    @Column(nullable = false,unique = true)
    private String immatriculation;

    @NotNull(message = "la capacité doit avoir une valeur")
    @Min(value = 2, message = "la capacité de l'avion doit être supérieure à deux")
    @Column(nullable = false)
    private Integer capacity;
}
