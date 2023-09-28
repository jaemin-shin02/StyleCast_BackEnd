package toyproject.stylecast.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Profile;
import toyproject.stylecast.repository.MemberDataRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberDataService {
    private final MemberDataRepository memberDataRepository;
    private final PasswordEncoder passwordEncoder;

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
