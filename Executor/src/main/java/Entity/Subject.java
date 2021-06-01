package Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_id_generator")
    @SequenceGenerator(name="subject_id_generator", allocationSize = 1,initialValue = 1)
    private long id;

    @Column(name = "subject_name")
    private String name;

    @OneToOne(mappedBy = "subject", fetch = FetchType.LAZY)
    private Teacher teacher;
}
