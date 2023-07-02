package toyproject.stylecast.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String birth_date;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Profile profile;

    @OneToMany(mappedBy = "member")
    private List<Clothes> clothesList;

    @OneToMany(mappedBy = "member")
    private List<Outfit> outfitList;

    public void setGrade_User(){
        this.grade = Grade.USER;
    }

    public void setDefault(String name, String birth_date, String email, String password) {
        this.name = name;
        this.birth_date = birth_date;
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

    //==생성 메소드==//
    public static Member creatMember(String name, String birth_date, String email, String password){
        Member member = new Member();
        member.setDefault(name, birth_date, email, password);
        member.setGrade_User();

        return member;
    }
}