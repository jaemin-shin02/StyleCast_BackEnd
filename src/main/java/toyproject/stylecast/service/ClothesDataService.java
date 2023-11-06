package toyproject.stylecast.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.Clothes;
import toyproject.stylecast.domain.FileInfo;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Season;
import toyproject.stylecast.domain.clothes.*;
import toyproject.stylecast.repository.data.ClothesDataRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClothesDataService {

    private final MemberDataService memberDataService;
    private final ClothesDataRepository clothesDataRepository;

    @Transactional
    public Long clothes(Clothes clothes){
        clothesDataRepository.save(clothes);
        return clothes.getId();
    }

    @Transactional
    public Long clothes(Long memberId, String name, Category category, String color, Season season){
        Member findMember = memberDataService.findOne(memberId);
        Clothes clothes = Clothes.creatClothes(findMember, name, category, color, season);

        clothesDataRepository.save(clothes);

        return clothes.getId();
    }

    @Transactional
    public void setPhoto(Long clothesId, FileInfo photo){
        Clothes clothes = clothesDataRepository.findById(clothesId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옷입니다."));
        clothes.setPhoto(photo);
    }

    @Transactional
    public void setTop(Long clothesId, Top top){
        Clothes clothes = clothesDataRepository.findById(clothesId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옷입니다."));
        clothes.setTop(top);
    }
    @Transactional
    public void setPants(Long clothesId, Pants pants){
        Clothes clothes = clothesDataRepository.findById(clothesId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옷입니다."));
        clothes.setPants(pants);
    }
    @Transactional
    public void setOuter(Long clothesId, Outwear outwear){
        Clothes clothes = clothesDataRepository.findById(clothesId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옷입니다."));
        clothes.setOutwear(outwear);
    }
    @Transactional
    public void setShoes(Long clothesId, Shoes shoes){
        Clothes clothes = clothesDataRepository.findById(clothesId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옷입니다."));
        clothes.setShoes(shoes);
    }
    @Transactional
    public void setOnepiece(Long clothesId, Onepiece onepiece){
        Clothes clothes = clothesDataRepository.findById(clothesId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옷입니다."));
        clothes.setOnepiece(onepiece);
    }
    @Transactional
    public void setSkirt(Long clothesId, Skirt skirt){
        Clothes clothes = clothesDataRepository.findById(clothesId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옷입니다."));
        clothes.setSkirt(skirt);
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

    public String getName(Long clothesId){
        if(clothesId == null){
            return "X";
        }
        Clothes clothes = clothesDataRepository.findById(clothesId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옷입니다."));
        return clothes.getName();
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
