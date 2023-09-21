package toyproject.stylecast.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.Clothes;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Season;
import toyproject.stylecast.domain.clothes.Category;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ClothesDataRepositoryTest {
    @Autowired
    private ClothesDataRepository clothesDataRepository;

    @Autowired
    private MemberDataRepository memberDataRepository;

    @Test
    public void createClothes() throws Exception {
        Member member = Member.creatMember("Sul", "19990719", "meow@naver.com", "dbwls99!");
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
}