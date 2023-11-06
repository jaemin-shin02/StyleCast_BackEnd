package toyproject.stylecast.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.Clothes;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Season;
import toyproject.stylecast.domain.clothes.Category;
import toyproject.stylecast.domain.clothes.Outwear;
import toyproject.stylecast.domain.clothes.Pants;
import toyproject.stylecast.domain.clothes.Top;
import toyproject.stylecast.repository.data.ClothesDataRepository;
import toyproject.stylecast.repository.data.MemberDataRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ClothesDataRepositoryTest {
    @Autowired
    private ClothesDataRepository clothesDataRepository;

    @Autowired
    private MemberDataRepository memberDataRepository;

    @Test
    public void createClothes() throws Exception {
        Member member = Member.creatMember("Sul", "바밤바","19990719", "meow@naver.com", "dbwls99!");
        memberDataRepository.save(member);

        Clothes clothes = Clothes.creatClothes(member, "인세인 롱슬리브", Category.상의, "검정", Season.가을);
        clothesDataRepository.save(clothes);

        Clothes find = clothesDataRepository.findById(clothes.getId()).get();

        assertThat(find.getName()).isEqualTo(clothes.getName());
        assertThat(find).isEqualTo(clothes);
    }

    @Test
    public void searchByMemberId() throws Exception {
        List<Member> all = memberDataRepository.findAll();
        List<Clothes> result = clothesDataRepository.findClothesByMemberId(all.get(0).getId());

        for (Clothes clothes : result) {
            System.out.println("clothes = " + clothes);
        }

        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    public void searchByMemberIdAndCategory() throws Exception {
        List<Member> all = memberDataRepository.findAll();
        List<Clothes> result = clothesDataRepository.findClothesByMemberIdAndCategory(all.get(0).getId(), Category.상의);

        for (Clothes clothes : result) {
            System.out.println("clothes = " + clothes);
        }

        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void SelectTest() throws Exception {
        //given
        Member member = Member.creatMember("Sul", "바밤바","19990719", "meow@naver.com", "dbwls99!");
        memberDataRepository.save(member);

        for(int i=0;i<100;i++){
            Clothes clothes1 = Clothes.creatClothes(member, "인세인 롱슬리브"+i, Category.상의, "검정", Season.가을);
            clothes1.setTop(Top.긴팔);
            Clothes clothes2 = Clothes.creatClothes(member, "블랙진"+i, Category.바지, "검정", Season.가을);
            clothes1.setPants(Pants.데님팬츠);
            Clothes clothes3 = Clothes.creatClothes(member, "후디"+i, Category.아우터, "검정", Season.가을);
            clothes3.setOutwear(Outwear.후드집업);
            clothesDataRepository.save(clothes1);
            clothesDataRepository.save(clothes2);
            clothesDataRepository.save(clothes3);
        }
        //when
        List<Long> selectByTop = clothesDataRepository.SelectByTop(Top.긴팔);
        List<Long> selectByPants = clothesDataRepository.SelectByPants(Pants.데님팬츠);
        List<Long> selectByOuter = clothesDataRepository.SelectByOuter(Outwear.후드집업);
        //then
        assertThat(selectByTop.size()).isEqualTo(100);
        assertThat(selectByPants.size()).isEqualTo(100);
        assertThat(selectByOuter.size()).isEqualTo(100);
    }


}