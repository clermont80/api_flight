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

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "vols")
@Getter
@Setter
@NoArgsConstructor
public class Vol extends PanacheEntityBase
{
    @Id
    @SequenceGenerator(name = "vols_sequence_in_java",sequenceName = "vols_sequence_in_database",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vols_sequence_in_java")
    private Integer id;

    @NotBlank(message = "le numéro de vol ne doit pas être vide")
    @Column(nullable = false)
    private String number;

    @NotBlank(message = "l'origine ne doit pas être vide")
    @Column(nullable = false)
    private String origin;

    @NotBlank(message = "la destination ne doit pas être vide")
    @Column(nullable = false)
    private String destination;

    @NotNull(message = "la date de départ doit avoir une valeur")
    @Column(nullable = false)
    private Date departure_date;

    @NotNull(message = "la temps de départ doit avoir une valeur")
    @Column(nullable = false)
    private Time departure_time;

    @NotNull(message = "la date d'arrivée doit avoir une valeur")
    @Column(nullable = false)
    private Date arrival_date;

    @NotNull(message = "l'heure d'arrivée' doit avoir une valeur")
    @Column(nullable = false)
    private Time arrival_time;

    @NotNull(message = "l'identifiant de vol doit avoir une valeur")
    @Column(nullable = false,unique = true)
    private Integer plane_id;

}
