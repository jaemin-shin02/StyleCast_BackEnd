package toyproject.stylecast.repository.data;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.stylecast.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberDataRepository extends JpaRepository<Member, Long> , MemberDataRepositoryCustom{
    List<Member> findMembersByNameAndBirthdate(String name, String birthdate);
    List<Member> findMembersByEmail(String email);
    Optional<Member> findByName(String username);
    Optional<Member> findMemberByEmail(String email);

}
