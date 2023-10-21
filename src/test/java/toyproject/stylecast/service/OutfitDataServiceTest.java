package toyproject.stylecast.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.*;
import toyproject.stylecast.domain.clothes.*;
import toyproject.stylecast.domain.recommendframe.Weather;
import toyproject.stylecast.dto.OutfitDto;
import toyproject.stylecast.dto.OutfitSearchCondition;
import toyproject.stylecast.repository.ClothesDataRepository;
import toyproject.stylecast.repository.MemberDataRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class OutfitDataServiceTest {
    @Autowired
    private OutfitDataService outfitDataService;
    @Autowired
    private MemberDataService memberDataService;
    @Autowired
    private MemberDataRepository memberDataRepository;
    @Autowired
    private ClothesDataService clothesDataService;
    @Autowired
    private ClothesDataRepository clothesDataRepository;

    @Test
    public void clothe() throws Exception {
        //given
        List<Long> longs = clothesDataRepository.SelectByTop(Top.반팔);
        //when

        //then
    }

    @Test
    @Rollback(value = true)
    public void recommend() throws Exception {
        //given
        Member member1 = Member.creatMember("박예은", "망그러진곰", "20021011", "pye.eun@naver.com", "rnldyal!");
        Profile profile1 = Profile.creatProfile(member1, Gender.WOMEN, 45, 158, Figure.STANDARD, true);
        profile1.addStyle(Style.스트릿);
        member1.setProfile(profile1);
        member1.addLocation("서울");
        memberDataService.join(member1);

        Member member2 = Member.creatMember("김민지", "ENTJ", "20001116", "kwiwi@naver.com", "sksekdy!!");
        Profile profile2 = Profile.creatProfile(member2, Gender.WOMEN, 45, 158, Figure.STANDARD, true);
        profile2.addStyle(Style.스트릿);
        member2.setProfile(profile2);
        memberDataService.join(member2);

        Member findMember0 = memberDataService.findByEmail("youjin@naver.com");
        Member findMember = memberDataRepository.findMemberByEmail("youjin@naver.com").get();



        for(int i=0; i<100; i++){
            Long top1 = clothesDataService.clothes(Clothes.creatClothes(findMember, "기본니트" + i, Category.상의, "검정", Season.가을));
            Long bottom1 = clothesDataService.clothes(Clothes.creatClothes(findMember, "기본바지" + i, Category.바지, "검정", Season.가을));
            Long outer1 = clothesDataService.clothes(Clothes.creatClothes(findMember, "기본아우터" + i, Category.아우터, "검정", Season.가을));
            Long outfit = outfitDataService.outfit(Outfit.creatOutfit(findMember, "힙하게" + i, Style.스트릿, "힙하게 꾸미기" + i, top1, bottom1, outer1));

            Long top2 = clothesDataService.clothes(Clothes.creatClothes(member2, "나그랑티" + i, Category.상의, "검정", Season.가을));
            Long bottom2 = clothesDataService.clothes(Clothes.creatClothes(member2, "치마" + i, Category.스커트, "검정", Season.가을));
            Long outer2 = clothesDataService.clothes(Clothes.creatClothes(member2, "코트" + i, Category.아우터, "검정", Season.가을));
            Long outfit1 = outfitDataService.outfit(Outfit.creatOutfit(member2, "힙한데귀엽게" + i, Style.스트릿, "아마 힙하게 꾸미기" + i, top2, bottom2, outer2));
            Clothes clothes = clothesDataRepository.findById(top1).get();
            Clothes clothes1 = clothesDataRepository.findById(top2).get();
            Clothes clothes2 = clothesDataRepository.findById(bottom1).get();
            Clothes clothes3 = clothesDataRepository.findById(bottom2).get();
            Clothes clothes4 = clothesDataRepository.findById(outer1).get();
            Clothes clothes5 = clothesDataRepository.findById(outer2).get();
            if(i%2 == 0){
                clothes.setTop(Top.니트);
                clothes1.setTop(Top.후드티);
                clothes2.setPants(Pants.데님팬츠);
                clothes3.setPants(Pants.나일론팬츠);
                clothes4.setOuter(Outer.블루종);
                clothes5.setOuter(Outer.항공점퍼);
            }else {
                clothes.setTop(Top.반팔);
                clothes1.setTop(Top.긴팔);
                clothes2.setPants(Pants.숏팬츠);
                clothes3.setPants(Pants.슬랙스);
                clothes4.setOuter(Outer.래더재킷);
                clothes5.setOuter(Outer.헌팅재킷);
            }
            Outfit outfit2 = outfitDataService.findOutfit(outfit);
            Outfit outfit3 = outfitDataService.findOutfit(outfit1);
            outfit2.addWeather(Weather.Clear);
            outfit3.addWeather(Weather.Clear);
        }

        List<Outfit> outfitList = findMember.getOutfitList();
        List<Outfit> outfitList1 = member2.getOutfitList();
        for (Outfit outfit : outfitList) {
            if(outfit.getName().contains("3")){
                for(int i=0; i<100; i++){
                    outfit.addLike();
                }
            }
        }
        for (Outfit outfit : outfitList1) {
            if(outfit.getName().contains("3")){
                for(int i=0; i<100; i++){
                    outfit.addLike();
                }
            }
        }

        //when
        List<OutfitDto> outfitDtoList = outfitDataService.recommendOutfitBasic(member1.getId());
        System.out.println("outfitDtoList.size() = " + outfitDtoList.size());
        for (OutfitDto outfitDto : outfitDtoList) {
            System.out.println("outfitDto = " + outfitDto);
        }
    }

    @Test
    @Rollback(value = true)
    public void recommendByStyle() throws Exception {
        //given
        Member member1 = Member.creatMember("박예은", "망그러진곰", "20021011", "pye.eun@naver.com", "rnldyal!");
        Profile profile1 = Profile.creatProfile(member1, Gender.WOMEN, 45, 158, Figure.STANDARD, true);
        profile1.addStyle(Style.스트릿);
        member1.setProfile(profile1);
        member1.addLocation("서울");
        memberDataService.join(member1);

        Member member2 = Member.creatMember("김민지", "ENTJ", "20001116", "kwiwi@naver.com", "sksekdy!!");
        Profile profile2 = Profile.creatProfile(member2, Gender.WOMEN, 45, 158, Figure.STANDARD, true);
        profile2.addStyle(Style.스트릿);
        member2.setProfile(profile2);
        memberDataService.join(member2);

        Member findMember = memberDataService.findByEmail("youjin@naver.com");

        for(int i=0; i<100; i++){
            Long top1 = clothesDataService.clothes(Clothes.creatClothes(findMember, "기본니트" + i, Category.상의, "검정", Season.가을));
            Long bottom1 = clothesDataService.clothes(Clothes.creatClothes(findMember, "기본바지" + i, Category.바지, "검정", Season.가을));
            Long outer1 = clothesDataService.clothes(Clothes.creatClothes(findMember, "기본아우터" + i, Category.아우터, "검정", Season.가을));
            Long outfit = outfitDataService.outfit(Outfit.creatOutfit(findMember, "힙하게" + i, Style.스트릿, "힙하게 꾸미기" + i, top1, bottom1, outer1));

            Long top2 = clothesDataService.clothes(Clothes.creatClothes(member2, "나그랑티" + i, Category.상의, "검정", Season.가을));
            Long bottom2 = clothesDataService.clothes(Clothes.creatClothes(member2, "치마" + i, Category.스커트, "검정", Season.가을));
            Long outer2 = clothesDataService.clothes(Clothes.creatClothes(member2, "코트" + i, Category.아우터, "검정", Season.가을));
            Long outfit1 = outfitDataService.outfit(Outfit.creatOutfit(member2, "힙한데귀엽게" + i, Style.스트릿, "아마 힙하게 꾸미기" + i, top2, bottom2, outer2));
            Clothes clothes = clothesDataRepository.findById(top1).get();
            Clothes clothes1 = clothesDataRepository.findById(top2).get();
            Clothes clothes2 = clothesDataRepository.findById(bottom1).get();
            Clothes clothes3 = clothesDataRepository.findById(bottom2).get();
            Clothes clothes4 = clothesDataRepository.findById(outer1).get();
            Clothes clothes5 = clothesDataRepository.findById(outer2).get();
            if(i%2 == 0){
                clothes.setTop(Top.니트);
                clothes1.setTop(Top.후드티);
                clothes2.setPants(Pants.데님팬츠);
                clothes3.setSkirt(Skirt.미니스커트);
                clothes4.setOuter(Outer.블루종);
                clothes5.setOuter(Outer.항공점퍼);
            }else {
                clothes.setTop(Top.맨투맨);
                clothes1.setTop(Top.긴팔);
                clothes2.setPants(Pants.숏팬츠);
                clothes3.setSkirt(Skirt.롱스커트);
                clothes4.setOuter(Outer.래더재킷);
                clothes5.setOuter(Outer.헌팅재킷);
            }
            Outfit outfit2 = outfitDataService.findOutfit(outfit);
            Outfit outfit3 = outfitDataService.findOutfit(outfit1);
            outfit2.addWeather(Weather.Clear);
            outfit3.addWeather(Weather.Clear);
        }

        List<Outfit> outfitList = findMember.getOutfitList();
        List<Outfit> outfitList1 = member2.getOutfitList();
        for (Outfit outfit : outfitList) {
            if(outfit.getName().contains("4")){
                for(int i=0; i<100; i++){
                    outfit.addLike();
                    if(outfit.getName().contains("3")){
                        outfit.addLike();
                    }
                }
            }
        }
        for (Outfit outfit : outfitList1) {
            if(outfit.getName().contains("3")){
                for(int i=0; i<100; i++){
                    outfit.addLike();
                }
            }
        }

        //when
        List<OutfitDto> outfitDtoList = outfitDataService.recommendOutfitByStyle(member1.getId(), Style.스트릿);
        System.out.println("outfitDtoList.size() = " + outfitDtoList.size());
        for (OutfitDto outfitDto : outfitDtoList) {
            System.out.println("outfitDto = " + outfitDto);
        }
    }
}