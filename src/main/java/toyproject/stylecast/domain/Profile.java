package toyproject.stylecast.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Profile {
    @Id @GeneratedValue
    @Column(name = "profile_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "profile")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int weight;
    private int height;

    @Enumerated(EnumType.STRING)
    private Figure figure;
    private Boolean work_out;

    private String detail;

    //==연관관계 메서드==//
    public void setMember(Member member){
        this.member = member;
    }
    public void setDefault(Gender gender, int weight, int height, Figure figure, Boolean work_out, String detail){
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.figure = figure;
        this.work_out = work_out;
        this.detail = detail;
    }

    //==생성 메서드==//
    public static Profile creatProfile( Member member, Gender gender, int weight, int height, Figure figure, Boolean work_out, String detail ){
        Profile profile = new Profile();
        profile.setMember(member);
        profile.setDefault(gender, weight, height, figure, work_out, detail);

        return profile;
    }


}
