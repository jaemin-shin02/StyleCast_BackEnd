package toyproject.stylecast.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.stylecast.domain.Clothes;
import toyproject.stylecast.domain.clothes.Category;
import toyproject.stylecast.dto.outfit.CreateOutfitRequest;
import toyproject.stylecast.service.ClothesDataService;
import toyproject.stylecast.service.MemberDataService;
import toyproject.stylecast.service.OutfitDataService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OutfitController {
    
    private final MemberDataService memberService;
    private final ClothesDataService clothesService;
    private final OutfitDataService outfitService;
    
    @GetMapping("/outfit/create/{id}")
    public String AddClothesPage(Model model, @PathVariable("id") Long memberId){

        List<Clothes> topList = clothesService.findClothesByMemberIdWithCategory(memberId, Category.상의);
        List<Clothes> bottomList = clothesService.findClothesByMemberIdWithCategory(memberId, Category.바지);
        List<Clothes> outerList = clothesService.findClothesByMemberIdWithCategory(memberId, Category.아우터);

        // 경로 변수 'id'를 모델에 추가하여 뷰로 전달
        model.addAttribute("memberId", memberId);
        model.addAttribute("topList", topList);
        model.addAttribute("bottomList", bottomList);
        model.addAttribute("outerList", outerList);

        return "outfitUpdate";
    }

    @PostMapping("/outfit/create/{id}")
    public String AddClothes(@PathVariable("id") Long memberId, @Valid CreateOutfitRequest request){
        log.info("옷 추가 시도");
        System.out.println("request = " + request);

        outfitService.outfit(memberId, request.getName(), request.getDescription(), request.getStyle(), request.getTop_id(), request.getBottom_id(), request.getOuterwear_id());

        return "redirect:/outfit/success";
    }

    @GetMapping("/outfit/success")
    public String joinSuccess(){
        return "success";
    }
    @GetMapping("/outfit/failure")
    public String joinFailure(){
        return "failure";
    }

}
