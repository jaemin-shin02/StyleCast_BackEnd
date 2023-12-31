package toyproject.stylecast.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.clothes.Category;
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
    private final MemberRepository memberRepository;

    public Long clothes(Clothes clothes){
        clothesRepository.save(clothes);
        return clothes.getId();
    }

    public Long clothes(Long memberId, String name, Category category, String color, Season season){
        Member findMember = memberRepository.findOne(memberId);
        Clothes clothes = Clothes.creatClothes(findMember, name, category, color, season);

        clothesRepository.save(clothes);

        return clothes.getId();
    }

    public List<Clothes> findAllClothes(){
        return clothesRepository.findAll();
    }

    public Clothes findClothes(Long clothesId){
        return clothesRepository.findOne(clothesId);
    }

    public List<Clothes> findClothesByMemberId(Long memberId){
        return clothesRepository.findClothesByMember(memberId);
    }

    public List<Clothes> findClothesByMemberIdWithCategory(Long memberId, Category category){
        return clothesRepository.findClothesByMemberWithCategory(memberId, category);
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
