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
//    @PostConstruct
//    public void init(){
//        initService.dbInit1();
//        initService.dbInit2();
//        initService.dbInit3();
//    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final MemberDataService memberDataService;
        private final ProfileService profileService;
        private final ClothesDataService clothesDataService;
        private final OutfitDataService outfitDataService;


        public void dbInit1(){
            Member member = Member.creatMember("신재민", "우주최강재민","20020220", "sour_jam0220@naver.com", "woals0220!");
            Profile profile = Profile.creatProfile(member, Gender.MEN, 73, 174, Figure.STANDARD, true);
            profile.addStyle(Style.스트릿);
            profile.addStyle(Style.포멀);
            profile.addStyle(Style.댄디);

            Long memberId = memberDataService.join(member);
            Long profileId = profileService.save(profile);
            memberDataService.setProfile(memberId, profileId);

            memberDataService.addLocation(memberId, "서울시");
        }

        public void dbInit2(){
            Member member = Member.creatMember("설유진", "우주최강유진","19990518", "youjin@naver.com", "sksmsdbwls!");
            Profile profile = Profile.creatProfile(member, Gender.WOMEN, 48, 161, Figure.STANDARD, false);
            profile.addStyle(Style.캐주얼);
            profile.addStyle(Style.걸리시);
            profile.addStyle(Style.포멀);

            Long memberId = memberDataService.join(member);
            Long profileId = profileService.save(profile);
            memberDataService.setProfile(memberId, profileId);

            memberDataService.addLocation(memberId,"서울시");
        }
        public void dbInit3(){
            Member member = Member.creatMember("박예은", "pye.eun","20021011", "pye.eun@naver.com", "sksmsdPdms!");
            Profile profile = Profile.creatProfile(member, Gender.WOMEN, 46, 158, Figure.STANDARD, false);
            profile.addStyle(Style.캐주얼);
            profile.addStyle(Style.걸리시);
            profile.addStyle(Style.스트릿);

            Long memberId = memberDataService.join(member);
            Long profileId = profileService.save(profile);
            memberDataService.setProfile(memberId, profileId);

            memberDataService.addLocation(memberId,"서울시");
        }
    }

}
