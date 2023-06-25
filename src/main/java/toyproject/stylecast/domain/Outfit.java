package toyproject.stylecast.domain;

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
public class Outfit {
    @Id @GeneratedValue
    @Column(name = "outfit_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String name;
    private String description; //코디 설명

    //악세서리는 추후 추가 예정
    private Long top_id;
    private Long bottom_id;
    private Long outerwear_id;

    public void setDefault(String name, String description, Long top_id, Long bottom_id, Long outerwear_id) {
        this.name = name;
        this.description = description;
        this.top_id = top_id;
        this.bottom_id = bottom_id;
        this.outerwear_id = outerwear_id;
    }

    //==연관관계 메서드==//
    public void setMember(Member member){
        this.member = member;
        member.getOutfitList().add(this);
    }

    //==생성 메서드==//
    public static Outfit creatOutfit(Member member, String name, String description, Long top_id, Long bottom_id, Long outerwear_id){
        Outfit outfit = new Outfit();
        outfit.setMember(member);
        outfit.setDefault(name, description, top_id, bottom_id, outerwear_id);

        return outfit;
    }
}
