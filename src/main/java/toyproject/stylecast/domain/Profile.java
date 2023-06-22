package toyproject.stylecast.domain;

import javax.persistence.*;

@Entity
public class Profile {
    @Id @GeneratedValue
    @Column(name = "profile_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int weight;
    private int height;

    @Enumerated(EnumType.STRING)
    private Figure figure;
    private Boolean work_out;

    private String detail;

}
