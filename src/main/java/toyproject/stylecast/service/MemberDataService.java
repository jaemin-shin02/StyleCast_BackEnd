package toyproject.stylecast.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Profile;
import toyproject.stylecast.repository.MemberDataRepository;
import toyproject.stylecast.repository.ProfileRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberDataService {
    private final MemberDataRepository memberDataRepository;

    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        memberDataRepository.save(member);
        return member.getId();
    }

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
        return memberDataRepository.findById(memberId).get();
    }

    public Member findByEmail(String email){
        return memberDataRepository.findMemberByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
    }

    public void setProfile(Long memberId, Profile profile){
        Member member = memberDataRepository.findById(memberId).get();
        member.setProfile(profile);
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

    @Transactional
    public void addLocation(Long id, String location){
        Member member = memberDataRepository.findById(id).get();
        member.addLocation(location);
    }

}
