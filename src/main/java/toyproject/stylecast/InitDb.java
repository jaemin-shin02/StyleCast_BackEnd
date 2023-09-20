package toyproject.stylecast;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.*;
import toyproject.stylecast.domain.clothes.Category;
import toyproject.stylecast.service.*;

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
        private final MemberDataService memberDataService;

        private final ClothesService clothesService;
        private final ClothesDataService clothesDataService;
        private final OutfitService outfitService;
        private final OutfitDataService outfitDataService;

        public void dbInit1(){
            Member member = Member.creatMember("Shin", "20020220", "sour_jam0220@naver.com", "woals0220!");
            Profile profile = Profile.creatProfile(member, Gender.MEN, 73, 174, Figure.STANDARD, true);
            profile.addStyle(Style.스트릿);
            profile.addStyle(Style.포멀);
            Long memberId = memberDataService.join(member, profile);

            Long clothesId1 = clothesDataService.clothes(memberId, "무지반팔", Category.상의, "검정", Season.여름);
            Clothes clothes1 = clothesDataService.findClothes(clothesId1);
            Long clothesId2 = clothesDataService.clothes(memberId, "카고팬츠", Category.바지, "검정", Season.여름);
            Clothes clothes2 = clothesDataService.findClothes(clothesId2);
            Long clothesId3 = clothesDataService.clothes(memberId, "된장포스", Category.신발, "된장", Season.여름);
            Clothes clothes3 = clothesDataService.findClothes(clothesId3);

            Outfit outfit = Outfit.creatOutfit(member, "기본코디1", Style.스트릿, "꾸안꾸", clothesId1, clothesId2, clothesId3);
            Long outfitId = outfitDataService.outfit(outfit);
            Outfit clothes = outfitDataService.findOutfit(outfitId);

            memberDataService.addLocation(memberId, "서울");
        }

        public void dbInit2(){
            Member member = Member.creatMember("Yes", "20020611", "yesju@naver.com", "sulwha");
            Profile profile = Profile.creatProfile(member, Gender.WOMEN, 71, 176, Figure.STANDARD, true);
            profile.addStyle(Style.스트릿);
            profile.addStyle(Style.걸리시);
            Long memberId = memberDataService.join(member, profile);

            Long clothesId1 = clothesDataService.clothes(memberId, "프린팅반팔", Category.상의, "검정", Season.여름);
            Clothes clothes1 = clothesDataService.findClothes(clothesId1);
            Long clothesId2 = clothesDataService.clothes(memberId, "와이드블랙진", Category.바지, "검정", Season.여름);
            Clothes clothes2 = clothesDataService.findClothes(clothesId2);
            Long clothesId3 = clothesDataService.clothes(memberId, "된장포스", Category.신발, "된장", Season.여름);
            Clothes clothes3 = clothesDataService.findClothes(clothesId3);

            Outfit outfit = Outfit.creatOutfit(member, "기본코디2", Style.스트릿, "꾸안꾸", clothesId1, clothesId2, clothesId3);
            Long outfitId = outfitDataService.outfit(outfit);

            Outfit clothes = outfitDataService.findOutfit(outfitId);

            memberDataService.addLocation(memberId,"부산");
        }
    }

}
