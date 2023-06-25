package toyproject.stylecast.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> byEmail = memberRepository.findByEmail(member.getEmail());
        List<Member> byNameWithBirth = memberRepository.findByNameWithBirth(member.getName(), member.getBirth_date());

        if(!byEmail.isEmpty() || !byNameWithBirth.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void updateName(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }

    @Transactional
    public void updateEmail(Long id, String email) {
        Member member = memberRepository.findOne(id);
        member.setName(email);
    }

    @Transactional
    public void updateBirth(Long id, String birth){
        Member member = memberRepository.findOne(id);
        member.setBirth_date(birth);
    }


}
