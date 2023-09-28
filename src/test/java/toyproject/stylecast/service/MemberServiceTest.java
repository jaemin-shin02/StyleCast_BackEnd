package toyproject.stylecast.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.*;
import toyproject.stylecast.domain.geocode.Location;
import toyproject.stylecast.repository.MemberRepository;
import toyproject.stylecast.weather.WeatherService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;
//
//    @Test
//    public void joinTest() throws Exception {
//        //given
//        Member member = new Member();
//        member.setName("Shin");
//
//        //when
//        Long memberId = memberService.join(member);
//
//        //then
//        Assert.assertEquals(member, memberRepository.findOne(memberId));
//    }
//
//    @Test
//    public void 중복검사() throws Exception {
//        //given
//        Member member1 = new Member();
//        member1.setName("Shin");
//        member1.setBirth_date("010220");
//        member1.setEmail("naver");
//
//        Member member2 = new Member();
//        member2.setName("Shin");
//        member2.setBirth_date("020220");
//        member2.setEmail("naver");
//
//
//        //when
//        memberService.join(member1);
//        memberService.join(member2);
//
//        //then
//        Assert.fail("예외 발생해야한다!");
//    }
//
//    @Test
//    public void 조회() throws Exception {
//        //given
//        Member member1 = new Member();
//        member1.setName("Shin");
//        member1.setBirth_date("010220");
//        member1.setEmail("naver");
//
//        Member member2 = new Member();
//        member2.setName("jam");
//        member2.setBirth_date("020210");
//        member2.setEmail("naver.com");
//
//        memberService.join(member1);
//        memberService.join(member2);
//
//        //when
//        List<Member> members = memberService.findMembers();
//        for (Member member : members) {
//            System.out.println("member.getName() = " + member.getName());
//        }
//
//        //then
//    }
    
    @Test
    public void 생성_및_아이템추가() throws Exception {
        //given
        Member member = Member.creatMember("shin", "빰빰","020220", "sour_jam0220@naver.com", "woals0220!");
        Profile profile = Profile.creatProfile(member, Gender.MEN, 73, 174, Figure.STANDARD, true);
        member.setProfile(profile);
        
        //when
//        memberService.join(member);
        
        //then
        Assert.assertEquals(profile.getMember(), member);
        Assert.assertEquals(member.getProfile(), profile);
    }

    @Autowired
    private GeocodingService geocodingService;

    @Test
    public void 위도테스트() throws Exception {
        //given
        String str = geocodingService.getCoordinates("충북 제천");
        //when
        Location location = geocodingService.getLocation(str);
        //then
        System.out.println("location.getLat() = " + location.getLat());
        System.out.println("location.getLot() = " + location.getLon());
    }

    @Autowired
    private WeatherService weatherService;
    @Test
    public void 날씨_정보() throws Exception {
        //given
        String coordinates = geocodingService.getCoordinates("서울");
        Location location = geocodingService.getLocation(coordinates);
        String lat = location.getLat();
        String lon = location.getLon();
        System.out.println("lat = " + lat);
        System.out.println("lon = " + lon);
        weatherService.getWeatherData(lat, lon);
        //when

        //then
    }

}
