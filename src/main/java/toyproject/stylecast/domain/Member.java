package toyproject.stylecast.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @OneToOne(fetch = FetchType.LAZY)
    private Profile profile;

    @OneToMany
    private List<Clothes> clothesList;


}
