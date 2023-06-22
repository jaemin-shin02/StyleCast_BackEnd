package toyproject.stylecast.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Clothes {
    @Id @GeneratedValue
    @Column(name = "clothes_id")
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany
    private List<Outfit> outfit;

    @Enumerated(EnumType.STRING)
    private Category category;
    private String color;
    @Enumerated(EnumType.STRING)
    private Season season;
}
