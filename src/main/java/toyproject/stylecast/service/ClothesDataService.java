package toyproject.stylecast.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.Clothes;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Season;
import toyproject.stylecast.domain.clothes.Category;
import toyproject.stylecast.repository.ClothesDataRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClothesDataService {

    private final MemberDataService memberDataService;
    private final ClothesDataRepository clothesDataRepository;

    public Long clothes(Clothes clothes){
        clothesDataRepository.save(clothes);
        return clothes.getId();
    }

    public Long clothes(Long memberId, String name, Category category, String color, Season season){
        Member findMember = memberDataService.findOne(memberId);
        Clothes clothes = Clothes.creatClothes(findMember, name, category, color, season);

        clothesDataRepository.save(clothes);

        return clothes.getId();
    }

    public List<Clothes> findAllClothes(){
        return clothesDataRepository.findAll();
    }

    public Clothes findClothes(Long clothesId){
        return clothesDataRepository.findById(clothesId).get();
    }

    public List<Clothes> findClothesByMemberId(Long memberId){
        return clothesDataRepository.findClothesByMemberId(memberId);
    }

    public List<Clothes> findClothesByMemberIdWithCategory(Long memberId, Category category){
        return clothesDataRepository.findClothesByMemberIdAndCategory(memberId, category);
    }

    @Transactional
    public void updateName(Long clothesId, String name){
        Clothes findClothes = clothesDataRepository.findById(clothesId).get();
        findClothes.setName(name);
    }

    @Transactional
    public void updateCategory(Long clothesId, Category category){
        Clothes findClothes = clothesDataRepository.findById(clothesId).get();
        findClothes.setCategory(category);
    }

    @Transactional
    public void updateSeason(Long clothesId, Season season){
        Clothes findClothes = clothesDataRepository.findById(clothesId).get();
        findClothes.setSeason(season);
    }

    @Transactional
    public void updateColor(Long clothesId, String color){
        Clothes findClothes = clothesDataRepository.findById(clothesId).get();
        findClothes.setColor(color);
    }

}