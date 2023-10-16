package toyproject.stylecast.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import toyproject.stylecast.domain.clothes.*;
import toyproject.stylecast.domain.recommendframe.Temperature;
import toyproject.stylecast.domain.recommendframe.Weather;

import javax.persistence.*;
import java.util.ArrayList;
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
    @JsonIgnore
    private Member member;
    private String name;
    private String description; //코디 설명

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "photo_id")
//    private FileInfo photo;

    @Enumerated(EnumType.STRING)
    private Style style;

    //악세서리는 추후 추가 예정
    private Long top_id;
    private Long bottom_id;
    private Long outerwear_id;

    private Long shoe_id;

    @Enumerated(EnumType.STRING)
    private Season season;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Weather.class)
    private List<Weather> weatherList  = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Temperature temperature;

    private Boolean bookmark;
    private int likes;
    public void addLike(){
        this.likes++;
    }
    public void unLike(){
        this.likes--;
    }

    //==연관관계 메서드==//
    public void setMember(Member member){
        this.member = member;
        member.getOutfitList().add(this);
    }

//    public void setFileInfo(FileInfo file){
//        this.photo = file;
//        file.setOutfit(this);
//    }

    //==생성 메서드==//
    public void setDefault(String name, Style style,String description, Long top_id, Long bottom_id, Long outerwear_id) {
        this.name = name;
        this.style = style;
        this.description = description;
        this.top_id = top_id;
        this.bottom_id = bottom_id;
        this.outerwear_id = outerwear_id;
//        this.season = season;
        this.bookmark = false;
    }

    public static Outfit creatOutfit(Member member, String name, Style style, String description, Long top_id, Long bottom_id, Long outerwear_id){
        Outfit outfit = new Outfit();
        outfit.setMember(member);
        outfit.setDefault(name, style, description, top_id, bottom_id, outerwear_id);

        return outfit;
    }

}
