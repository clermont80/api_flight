package beans;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
public class Reservation
{
    @Id
    @SequenceGenerator(name = "reservation_sequence_in_java",sequenceName = "reservation_sequence_in_database",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_sequence_in_java")
    private Integer id;


    @Column(nullable = false)
    private Integer flight_id;


    @Column(nullable = false)
    private Integer passenger_id;
}
