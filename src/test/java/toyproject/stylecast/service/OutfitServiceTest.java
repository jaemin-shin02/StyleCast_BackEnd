package toyproject.stylecast.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.*;
import toyproject.stylecast.dto.OutfitDto;
import toyproject.stylecast.dto.OutfitSearchCondition;
import toyproject.stylecast.repository.OutfitDataRepository;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OutfitServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private OutfitService outfitService;

    @Autowired
    private OutfitDataRepository outfitDataRepository;

    @Test
    public void recommendTest() throws Exception {
        //given
        Member member = Member.creatMember("Gong", "꽁지","20010507", "gaya01@naver.com", "gonggong!");
        Profile profile = Profile.creatProfile(member, Gender.MEN, 73, 174, Figure.STANDARD, true);
        profile.addStyle(Style.스트릿);
        Long memberId = memberService.join(member, profile);

        //when
        List<Outfit> outfits = outfitService.recommendOutfit(memberId, Style.스트릿);

        //then
        System.out.println("outfits.size() = " + outfits.size());
        for (Outfit outfit : outfits) {
            System.out.println("outfit = " + outfit.getName());
        }
    }

    @Test
    public void recommendT() throws Exception {
        //given
        Member member = Member.creatMember("Gong", "꽁지","20010507", "gaya01@naver.com", "gonggong!");
        Profile profile = Profile.creatProfile(member, Gender.MEN, 73, 174, Figure.STANDARD, true);
        profile.addStyle(Style.스트릿);
        Long memberId = memberService.join(member, profile);

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
