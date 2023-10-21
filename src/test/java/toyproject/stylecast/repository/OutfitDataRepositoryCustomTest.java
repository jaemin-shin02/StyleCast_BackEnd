package toyproject.stylecast.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.*;
import toyproject.stylecast.domain.clothes.Category;
import toyproject.stylecast.dto.OutfitDto;
import toyproject.stylecast.dto.OutfitSearchCondition;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OutfitDataRepositoryCustomTest {
    @Autowired
    MemberDataRepository memberDataRepository;
    @Autowired
    ClothesDataRepository clothesDataRepository;
    @Autowired
    OutfitDataRepository outfitDataRepository;

    @Test
    public void createOutfit() throws Exception {
        Member member = Member.creatMember("Sul", "바밤바","19990719", "meow@naver.com", "dbwls99!");
        memberDataRepository.save(member);

        Clothes clothes1 = Clothes.creatClothes(member, "인세인 롱슬리브", Category.상의, "검정", Season.가을);
        Clothes clothes2 = Clothes.creatClothes(member, "인세인 롱슬리브", Category.스커트, "검정", Season.가을);
        clothesDataRepository.save(clothes1);
        clothesDataRepository.save(clothes2);

        Outfit outfit = Outfit.creatOutfit(member, "힙하게", Style.스트릿, "힙하게 꾸미기", clothes1.getId(), clothes2.getId(), null);
        outfitDataRepository.save(outfit);

        Optional<Outfit> find = outfitDataRepository.findById(outfit.getId());

        assertThat(find.get().getName()).isEqualTo(outfit.getName());
        assertThat(find.get()).isEqualTo(outfit);
    }

    @Test
    public void recommend() throws Exception {
        //given
        Member member1 = Member.creatMember("박예은", "망그러진곰", "20021011", "pye.eun@naver.com", "rnldyal!");
        Profile profile1 = Profile.creatProfile(member1, Gender.WOMEN, 48, 161, Figure.STANDARD, true);
        profile1.addStyle(Style.스트릿);
        member1.setProfile(profile1);
        memberDataRepository.save(member1);

        Member member2 = Member.creatMember("김민지", "ENTJ", "20001116", "kwiwi@naver.com", "sksekdy!!");
        Profile profile2 = Profile.creatProfile(member2, Gender.WOMEN, 45, 158, Figure.STANDARD, true);
        profile2.addStyle(Style.스트릿);
        member2.setProfile(profile2);
        memberDataRepository.save(member2);

        Member findMember = memberDataRepository.findMemberByEmail("youjin@naver.com").get();

        for(int i=0; i<100; i++){
            Clothes set1 = clothesDataRepository.save(Clothes.creatClothes(findMember, "기본니트" + i, Category.상의, "검정", Season.가을));
            Clothes set2 = clothesDataRepository.save(Clothes.creatClothes(findMember, "기본바지" + i, Category.바지, "검정", Season.가을));
            Clothes set3 = clothesDataRepository.save(Clothes.creatClothes(findMember, "기본아우터" + i, Category.아우터, "검정", Season.가을));
            outfitDataRepository.save(Outfit.creatOutfit(findMember, "힙하게"+i, Style.스트릿, "힙하게 꾸미기"+i, set1.getId(), set2.getId(), set3.getId()));

            Clothes set4 = clothesDataRepository.save(Clothes.creatClothes(member2, "나그랑티" + i, Category.상의, "검정", Season.가을));
            Clothes set5 = clothesDataRepository.save(Clothes.creatClothes(member2, "치마" + i, Category.스커트, "검정", Season.가을));
            Clothes set6 = clothesDataRepository.save(Clothes.creatClothes(member2, "코트" + i, Category.아우터, "검정", Season.가을));
            outfitDataRepository.save(Outfit.creatOutfit(member2, "캐주얼하게"+i, Style.캐주얼, "힙하게 꾸미기"+i, set4.getId(), set5.getId(), set6.getId()));
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
        OutfitSearchCondition condition = new OutfitSearchCondition();
        condition.setProfile(profile1);
//        condition.setStyle(Style.스트릿);

        List<OutfitDto> outfits = outfitDataRepository.RecommendOutfit(condition);
        //then
        System.out.println("outfits.size() = " + outfits.size());
        for (OutfitDto outfit : outfits) {
            System.out.println("outfit = " + outfit.getName());
        }
    }
}