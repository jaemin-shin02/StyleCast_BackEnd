package toyproject.stylecast.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Outfit;
import toyproject.stylecast.domain.Style;
import toyproject.stylecast.repository.MemberRepository;
import toyproject.stylecast.repository.OutfitRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OutfitService {
    private final OutfitRepository outfitRepository;
    private final MemberRepository memberRepository;

    public Long outfit(Outfit outfit){
        outfitRepository.save(outfit);
        return outfit.getId();
    }

    public List<Outfit> findAllClothes(){
        return outfitRepository.findAll();
    }

    public Outfit findOutfit(Long outfitId){
        return outfitRepository.findOne(outfitId);
    }

    public List<Outfit> recommendOutfit(Long memberId, Style style){
        Member findMember = memberRepository.findOne(memberId);
        List<Outfit> recommendOutfit = outfitRepository.RecommendOutfitV1(findMember.getProfile(), style);

        return recommendOutfit;
    }

    public List<Outfit> findOutfitByMember(Long memberId){
        return outfitRepository.findAllByMemberId(memberId);
    }

    @Transactional
    public void updateName(Long outfitId, String name){
        Outfit outfit = outfitRepository.findOne(outfitId);
        outfit.setName(name);
    }

    @Transactional
    public void updateDescription(Long outfitId, String description){
        Outfit outfit = outfitRepository.findOne(outfitId);
        outfit.setDescription(description);
    }

    @Transactional
    public void updateTop(Long outfitId, Long id){
        Outfit outfit = outfitRepository.findOne(outfitId);
        outfit.setTop_id(id);
    }
    @Transactional
    public void updateBottom(Long outfitId, Long id){
        Outfit outfit = outfitRepository.findOne(outfitId);
        outfit.setBottom_id(id);
    }
    @Transactional
    public void updateOuter(Long outfitId, Long id){
        Outfit outfit = outfitRepository.findOne(outfitId);
        outfit.setOuterwear_id(id);
    }

}
