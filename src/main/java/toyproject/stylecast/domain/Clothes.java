package toyproject.stylecast.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import toyproject.stylecast.domain.clothes.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class Clothes {
    @Id @GeneratedValue
    @Column(name = "clothes_id")
    private Long id;
    private String name;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "photo_id")
//    private FileInfo photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Top top;
    @Enumerated(EnumType.STRING)
    private Pants pants;
    @Enumerated(EnumType.STRING)
    private Skirt skirt;
    @Enumerated(EnumType.STRING)
    private Onepiece onepiece;
    @Enumerated(EnumType.STRING)
    private Shoes shoes;
    @Enumerated(EnumType.STRING)
    private Outer outer;
    private String color;
    @Enumerated(EnumType.STRING)
    private Season season;

    private Boolean bookmark;

    public void setDefault(String name, Category category, String color, Season season) {
        this.name = name;
        this.category = category;
        this.color = color;
        this.season = season;
        this.bookmark = false;
    }


    //==연관관계 메소드==//
    public void setMember(Member member){
        this.member = member;
        member.getClothesList().add(this);
    }

//    public void setFileInfo(FileInfo file){
//        this.photo = file;
//        file.setClothes(this);
//    }

    public void setBookmarkT(){
        this.bookmark = true;
    }
    public void setBookmarkF(){
        this.bookmark = false;
    }

    //==생성 메소드==//
    public static Clothes creatClothes(Member member, String name, Category category, String color, Season season){
        Clothes clothes = new Clothes();
        clothes.setMember(member);
        clothes.setDefault(name, category, color, season);

        return clothes;
    }

}
