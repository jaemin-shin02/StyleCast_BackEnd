package toyproject.stylecast.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Clothes {
    @Id @GeneratedValue
    @Column(name = "clothes_id")
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    @Enumerated(EnumType.STRING)
    private Category category;
    private String color;
    @Enumerated(EnumType.STRING)
    private Season season;

    public void setDefault(String name, Category category, String color, Season season) {
        this.name = name;
        this.category = category;
        this.color = color;
        this.season = season;
    }

    //==연관관계 메소드==//
    public void setMember(Member member){
        this.member = member;
        member.getClothesList().add(this);
    }

    //==생성 메소드==//
    public static Clothes creatClothes(Member member, String name, Category category, String color, Season season){
        Clothes clothes = new Clothes();
        clothes.setMember(member);
        clothes.setDefault(name, category, color, season);

        return clothes;
    }

}
