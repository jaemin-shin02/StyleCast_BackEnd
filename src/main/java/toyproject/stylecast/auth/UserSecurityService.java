package toyproject.stylecast.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.UserRole;
import toyproject.stylecast.repository.MemberDataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {
    private final MemberDataRepository memberDataRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("email = " + email);
        Optional<Member> findMember = this.memberDataRepository.findMemberByEmail(email);
        System.out.println("findMember = " + findMember);
        if (findMember.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
//        Member member = findMember.get();
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        if ("admin".equals(email)) {
//            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
//        } else {
//            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
//        }
//        return new User(member.getEmail(), member.getPassword(), authorities);

        return new CustomUserDetails(findMember.get());
    }
}
