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
        Member member = Member.creatMember("Sul", "19990719", "meow@naver.com", "dbwls99!");
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
        Member member = Member.creatMember("Sul", "19990719", "meow@naver.com", "dbwls99!");
        Profile profile = Profile.creatProfile(member, Gender.WOMEN, 49, 164, Figure.STANDARD, true);
        profile.addStyle(Style.스트릿);
        member.setProfile(profile);
        memberDataRepository.save(member);

        Clothes clothes1 = Clothes.creatClothes(member, "인세인 롱슬리브", Category.상의, "검정", Season.가을);
        Clothes clothes2 = Clothes.creatClothes(member, "인세인 롱슬리브", Category.스커트, "검정", Season.가을);
        //when

        OutfitSearchCondition condition = new OutfitSearchCondition();
        condition.setProfile(profile);

        List<OutfitDto> outfits = outfitDataRepository.RecommendOutfit(condition);
        //then
        System.out.println("outfits.size() = " + outfits.size());
        for (OutfitDto outfit : outfits) {
            System.out.println("outfit = " + outfit.getName());
        }
    }
}