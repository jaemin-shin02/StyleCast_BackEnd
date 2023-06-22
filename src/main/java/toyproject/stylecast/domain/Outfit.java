package toyproject.stylecast.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Outfit {
    @Id @GeneratedValue
    @Column(name = "outfit_id")
    private Long id;

    @ManyToOne
    private Member member;
    private String name;
    private String description;

    private Long top_id;
    private Long bottom_id;
    private Long outerwear_id;

    @OneToMany
    private List<Clothes> accessories;
}
