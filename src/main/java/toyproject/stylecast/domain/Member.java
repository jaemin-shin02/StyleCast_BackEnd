package toyproject.stylecast.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String nickname;
    private String name;
    private String birthdate;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "member")
    private List<Clothes> clothesList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Outfit> outfitList = new ArrayList<>();

    @ElementCollection
    private List<String> locationList = new ArrayList<>();

    private String refreshToken;

    @ElementCollection(fetch = FetchType.EAGER)
//    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setGrade_User(){
        this.grade = Grade.USER;
    }

    public void setDefault(String name, String nickname, String birth_date, String email, String password) {
        this.name = name;
        this.nickname = nickname;
        this.birthdate = birth_date;
        this.email = email;
        this.password = password;
    }



    //==연관관계 메소드==//
    public void setProfile(Profile profile){
        this.profile = profile;
        profile.setMember(this);
    }

    public void addClothes(Clothes clothes){
        clothes.setMember(this);
        this.clothesList.add(clothes);
    }

    public void addOutfits(Outfit outfit){
        outfit.setMember(this);
        this.outfitList.add(outfit);
    }

    public void addLocation(String location){
        this.locationList.add(location);
    }

    //==생성 메소드==//
    public static Member creatMember(String name, String nickname, String birth_date, String email, String password){
        Member member = new Member();
        member.setDefault(name, nickname, birth_date, email, password);
        member.setGrade_User();

        return member;
    }

    public static Member creatAdmin(String name, String nickname, String birth_date, String email, String password){
        Member member = new Member();
        member.setDefault(name, nickname, birth_date, email, password);
        member.setGrade(Grade.ADMIN);

        return member;
    }
}