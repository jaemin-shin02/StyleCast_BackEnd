package toyproject.stylecast.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.auth.JwtService;
import toyproject.stylecast.auth.JwtTokenProvider;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Profile;
import toyproject.stylecast.domain.Token;
import toyproject.stylecast.dto.member.LoginRequest;
import toyproject.stylecast.repository.data.MemberDataRepository;
import toyproject.stylecast.repository.data.ProfileRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberDataService {
    private final MemberDataRepository memberDataRepository;
    private final ProfileRepository profileRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        memberDataRepository.save(member);
        return member.getId();
    }

    @Transactional
    public void setProfile(Long memberId, Long profileId){
        Member member = memberDataRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("일치하는 회원 정보가 없습니다.")
        );

        Profile profile = profileRepository.findById(profileId).orElseThrow(
                () -> new IllegalArgumentException("Profile 정보가 없습니다.")
        );

        member.setProfile(profile);
    }

    @Transactional
    public Long join(Member member, Profile profile){
        validateDuplicateMember(member);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setProfile(profile);

        memberDataRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> byEmail = memberDataRepository.findMembersByEmail(member.getEmail());
        List<Member> byNameWithBirth = memberDataRepository.findMembersByNameAndBirthdate(member.getName(), member.getBirthdate());

        if(!byEmail.isEmpty() || !byNameWithBirth.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers(){
        return memberDataRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberDataRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    public Member findByEmail(String email){
        return memberDataRepository.findMemberByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
    }

    @Transactional
    public ResponseEntity<?> login(LoginRequest request) {
        Member member = memberDataRepository.findMemberByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));

        // 비밀번호를 비교하여 로그인을 검증합니다.
        if (passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            Token tokenDto = jwtTokenProvider.createAccessToken(member.getEmail(), member.getRoles());
            jwtService.login(tokenDto);

            // 로그인 성공 JSON 응답
            Map<String, String> response = new HashMap<>();
            response.put("message", "로그인 성공");
            response.put("access_token", tokenDto.getAccessToken());

            return ResponseEntity.ok(response);
        } else {
            // 로그인 실패 JSON 응답
            Map<String, String> response = new HashMap<>();
            response.put("message", "비밀번호가 일치하지 않습니다.");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @Transactional
    public void addLocation(Long id, String location){
        Member member = memberDataRepository.findById(id).get();
        member.addLocation(location);
    }

    @Transactional
    public void updateName(Long id, String name) {
        Member member = memberDataRepository.findById(id).get();
        member.setName(name);
    }

    @Transactional
    public void updateEmail(Long id, String email) {
        Member member = memberDataRepository.findById(id).get();
        member.setName(email);
    }

    @Transactional
    public void updateBirth(Long id, String birth){
        Member member = memberDataRepository.findById(id).get();
        member.setBirthdate(birth);
    }
}
