package toyproject.stylecast.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.Category;
import toyproject.stylecast.domain.Clothes;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Season;
import toyproject.stylecast.repository.ClothesRepository;
import toyproject.stylecast.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClothesService {
    private final ClothesRepository clothesRepository;

    public Long clothes(Clothes clothes){
        clothesRepository.save(clothes);

        return clothes.getId();
    }

    public List<Clothes> findAllClothes(){
        return clothesRepository.findAll();
    }

    public Clothes findClothes(Long clothesId){
        return clothesRepository.findOne(clothesId);
    }

    @Transactional
    public void updateName(Long clothesId, String name){
        Clothes findClothes = clothesRepository.findOne(clothesId);
        findClothes.setName(name);
    }

    @Transactional
    public void updateCategory(Long clothesId, Category category){
        Clothes findClothes = clothesRepository.findOne(clothesId);
        findClothes.setCategory(category);
    }

    @Transactional
    public void updateSeason(Long clothesId, Season season){
        Clothes findClothes = clothesRepository.findOne(clothesId);
        findClothes.setSeason(season);
    }

    @Transactional
    public void updateColor(Long clothesId, String color){
        Clothes findClothes = clothesRepository.findOne(clothesId);
        findClothes.setColor(color);
    }

}
