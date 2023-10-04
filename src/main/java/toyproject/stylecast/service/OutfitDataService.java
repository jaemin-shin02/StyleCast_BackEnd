package toyproject.stylecast.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.Outfit;
import toyproject.stylecast.repository.MemberDataRepository;
import toyproject.stylecast.repository.OutfitDataRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OutfitDataService {
    private final OutfitDataRepository outfitDataRepository;
    private final MemberDataRepository memberDataRepository;

    public Long outfit(Outfit outfit){
        outfitDataRepository.save(outfit);
        return outfit.getId();
    }

    public List<Outfit> findAllClothes(){
        return outfitDataRepository.findAll();
    }

    public Outfit findOutfit(Long outfitId){
        return outfitDataRepository.findById(outfitId).get();
    }

//    public List<Outfit> recommendOutfit(Long memberId, Style style){
//        Member findMember = memberRepository.findOne(memberId);
//        List<Outfit> recommendOutfit = outfitRepository.RecommendOutfitV1(findMember.getProfile(), style);
//
//        return recommendOutfit;
//    }

    public List<Outfit> findOutfitByMember(Long memberId){
        return outfitDataRepository.findOutfitsByMember_Id(memberId);
    }

    @Transactional
    public void updateName(Long outfitId, String name){
        Outfit outfit = outfitDataRepository.findById(outfitId).get();
        outfit.setName(name);
    }

    @Transactional
    public void updateDescription(Long outfitId, String description){
        Outfit outfit = outfitDataRepository.findById(outfitId).get();
        outfit.setDescription(description);
    }

    @Transactional
    public void updateTop(Long outfitId, Long id){
        Outfit outfit = outfitDataRepository.findById(outfitId).get();
        outfit.setTop_id(id);
    }
    @Transactional
    public void updateBottom(Long outfitId, Long id){
        Outfit outfit = outfitDataRepository.findById(outfitId).get();
        outfit.setBottom_id(id);
    }
    @Transactional
    public void updateOuter(Long outfitId, Long id){
        Outfit outfit = outfitDataRepository.findById(outfitId).get();
        outfit.setOuterwear_id(id);
    }
}
