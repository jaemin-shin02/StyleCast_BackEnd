package toyproject.stylecast.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Style;
import toyproject.stylecast.repository.data.MemberDataRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberDataRepositoryTest {
    @Autowired
    private MemberDataRepository memberDataRepository;

    @Test
    public void createMember() throws Exception {
        Member member = Member.creatMember("Sul", "바밤바","19990719", "meow@naver.com", "dbwls99!");
        memberDataRepository.save(member);

        Optional<Member> findMember = memberDataRepository.findById(member.getId());
        Member find = findMember.get();

        assertThat(member.getName()).isEqualTo(find.getName());
        assertThat(member).isEqualTo(find);
    }

    @Test
    public void findByEmail() throws Exception {
        Member member = Member.creatMember("Sul", "바밤바","19990719", "meow@naver.com", "dbwls99!");
        memberDataRepository.save(member);

        List<Member> result = memberDataRepository.findMembersByEmail("meow@naver.com");

        assertThat(result.get(0).getName()).isEqualTo(member.getName());
        assertThat(result.get(0)).isEqualTo(member);
    }

    @Test
    public void findByNameWithBirth() throws Exception {
        Member member = Member.creatMember("Sul", "바밤바","19990719", "meow@naver.com", "dbwls99!");
        memberDataRepository.save(member);

        List<Member> sul = memberDataRepository.findMembersByNameAndBirthdate("Sul", "19990719");

        assertThat(sul.get(0).getName()).isEqualTo(member.getName());
        assertThat(sul.get(0)).isEqualTo(member);
    }
    
    @Test
    public void findByStyle() throws Exception {
        //given
        Style style = Style.스트릿;
        
        //when
        List<Member> byPreferStyle = memberDataRepository.findByPreferStyle(style);
        
        //then
        for (Member member : byPreferStyle) {
            System.out.println("member.getName() = " + member.getName());
        }

        assertThat(byPreferStyle.size()).isEqualTo(2);
    }

}