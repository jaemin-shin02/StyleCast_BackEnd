package toyproject.stylecast;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.*;
import toyproject.stylecast.service.ClothesService;
import toyproject.stylecast.service.MemberService;
import toyproject.stylecast.service.OutfitService;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final MemberService memberService;
        private final ClothesService clothesService;
        private final OutfitService outfitService;

        public void dbInit1(){
            Member member = Member.creatMember("Shin", "20020220", "sour_jam0220@naver.com", "woals0220!");
            Profile profile = Profile.creatProfile(member, Gender.MEN, 73, 174, Figure.STANDARD, true);
            profile.addStyle(Style.스트릿);
            profile.addStyle(Style.포멀);
            Long memberId = memberService.join(member, profile);

            Long clothesId1 = clothesService.clothes(memberId, "무지반팔", Category.상의, "검정", Season.여름);
            Clothes clothes1 = clothesService.findClothes(clothesId1);
            Long clothesId2 = clothesService.clothes(memberId, "카고팬츠", Category.하의, "검정", Season.여름);
            Clothes clothes2 = clothesService.findClothes(clothesId2);
            Long clothesId3 = clothesService.clothes(memberId, "된장포스", Category.신발, "된장", Season.여름);
            Clothes clothes3 = clothesService.findClothes(clothesId3);

            Long outfit = outfitService.outfit(memberId, "기본코디1", Style.스트릿, "꾸안꾸", clothesId1, clothesId2, clothesId3);
            Outfit clothes = outfitService.findOutfit(outfit);

            memberService.addLocation(memberId, "서울");
        }

        public void dbInit2(){
            Member member = Member.creatMember("Yes", "20020611", "yesju@naver.com", "sulwha");
            Profile profile = Profile.creatProfile(member, Gender.WOMEN, 71, 176, Figure.STANDARD, true);
            profile.addStyle(Style.스트릿);
            profile.addStyle(Style.걸리시);
            Long memberId = memberService.join(member, profile);

            Long clothesId1 = clothesService.clothes(memberId, "프린팅반팔", Category.상의, "검정", Season.여름);
            Clothes clothes1 = clothesService.findClothes(clothesId1);
            Long clothesId2 = clothesService.clothes(memberId, "와이드블랙진", Category.하의, "검정", Season.여름);
            Clothes clothes2 = clothesService.findClothes(clothesId2);
            Long clothesId3 = clothesService.clothes(memberId, "된장포스", Category.신발, "된장", Season.여름);
            Clothes clothes3 = clothesService.findClothes(clothesId3);

            Long outfit = outfitService.outfit(memberId, "기본코디2", Style.스트릿,"꾸안꾸", clothesId1, clothesId2, clothesId3);
            Outfit clothes = outfitService.findOutfit(outfit);

            memberService.addLocation(memberId,"부산");
        }
    }

}
