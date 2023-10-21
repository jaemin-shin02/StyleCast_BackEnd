package toyproject.stylecast.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.Clothes;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Season;
import toyproject.stylecast.domain.clothes.Category;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ClosetServiceTest {
    @Autowired
    private ClothesDataService clothesDataService;
    @Autowired
    private MemberDataService memberDataService;

    @Test
    public void 옷생성() throws Exception {
        //given
        Member member = Member.creatMember("pye", "예니","20021011", "pyecute@naver.com", "dksdlfrTLq!!");
        Long memberId = memberDataService.join(member, null);

//        when
        Long clothesId = clothesDataService.clothes(memberId, "무지반팔", Category.상의, "검정", Season.여름);
        Clothes clothes = clothesDataService.findClothes(clothesId);

//        then
        Assert.assertEquals("사용자의 아이디가 같아야 한다.", memberId, clothes.getMember().getId());
        Assert.assertEquals("이름이 동일해야 한다.", "무지반팔", clothes.getName());
        Assert.assertEquals("카테고리가 같아야 한다.", Category.상의, clothes.getCategory());
        Assert.assertEquals("색상이 같아야 한다.", "검정", clothes.getColor());
        Assert.assertEquals("시즌이 같아야 한다.", Season.여름, clothes.getSeason());
        Assert.assertEquals("등록한 옷의 개수가 같아야 한다.", 1, member.getClothesList().size());
    }
}
