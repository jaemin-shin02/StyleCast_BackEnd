package toyproject.stylecast.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.Clothes;
import toyproject.stylecast.domain.Outfit;
import toyproject.stylecast.repository.OutfitRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OutfitService {
    private final OutfitRepository outfitRepository;

    public Long outfit(Outfit outfit){
        outfitRepository.save(outfit);
        return outfit.getId();
    }

    public List<Outfit> findAllClothes(){
        return outfitRepository.findAll();
    }

    public Outfit findClothes(Long outfitId){
        return outfitRepository.findOne(outfitId);
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
