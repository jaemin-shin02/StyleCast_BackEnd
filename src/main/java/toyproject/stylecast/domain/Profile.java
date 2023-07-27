package toyproject.stylecast.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Profile {
    @Id @GeneratedValue
    @Column(name = "profile_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "profile")
    @JsonIgnore
    private Member member;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int weight;
    private int height;

    @Enumerated(EnumType.STRING)
    private Figure figure;
    private Boolean work_out;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Style.class)
    private List<Style> prefer_style = new ArrayList<>();

    //==연관관계 메서드==//
    public void setMember(Member member){
        this.member = member;
    }
    public void setDefault(Gender gender, int weight, int height, Figure figure, Boolean work_out){
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.figure = figure;
        this.work_out = work_out;
    }

    public void addStyle(Style style){
        this.prefer_style.add(style);
    }

    //==생성 메서드==//
    public static Profile creatProfile( Member member, Gender gender, int weight, int height, Figure figure, Boolean work_out){
        Profile profile = new Profile();
        profile.setMember(member);
        profile.setDefault(gender, weight, height, figure, work_out);

        return profile;
    }


}
