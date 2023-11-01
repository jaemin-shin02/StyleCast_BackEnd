package toyproject.stylecast.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.stylecast.domain.Clothes;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Outfit;
import toyproject.stylecast.domain.clothes.Category;
import toyproject.stylecast.dto.outfit.CreateOutfitRequest;
import toyproject.stylecast.dto.outfit.ViewOutfit;
import toyproject.stylecast.service.ClothesDataService;
import toyproject.stylecast.service.MemberDataService;
import toyproject.stylecast.service.OutfitDataService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OutfitController {
    
    private final MemberDataService memberService;
    private final ClothesDataService clothesService;
    private final OutfitDataService outfitService;
    
    @GetMapping("/outfit/create")
    public String AddClothesPage(Model model){
        Long memberId = getMemberId();

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

    @PostMapping("/outfit/create")
    public String AddClothes(@Valid CreateOutfitRequest request){
        log.info("옷 추가 시도");
        System.out.println("request = " + request);

        outfitService.outfit(request.getMemberId(), request.getName(), request.getDescription(), request.getStyle(), request.getTop_id(), request.getBottom_id(), request.getOuterwear_id());

        return "redirect:/main";
    }


    @GetMapping("/outfit/all")
    public String viesOutfits(Model model){
        Long memberId = getMemberId();
        List<Outfit> outfitList = outfitService.findOutfitByMember(memberId);
        List<ViewOutfit> collect = outfitList.stream()
                .map(o -> new ViewOutfit(o.getName(), o.getDescription(), o.getStyle()
                        , clothesService.getName(o.getTop_id())
                        , clothesService.getName(o.getBottom_id())
                        , clothesService.getName(o.getOuterwear_id())
                        , clothesService.getName(o.getShoe_id())))
                .collect(Collectors.toList());
        for (ViewOutfit viewOutfit : collect) {
            System.out.println("viewOutfit = " + viewOutfit.toString());
        }
        model.addAttribute("memberId", memberId);
        model.addAttribute("outfitList", collect);

        return "outfitList";
    }

    private Long getMemberId() {
        // Spring Security를 사용하여 현재 사용자의 Authentication 객체를 얻어옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Authentication 객체에서 사용자 이름 또는 기타 정보를 추출
        String memberEmail = authentication.getName();
        Member member = memberService.findByEmail(memberEmail);
        Long memberId = member.getId();
        return memberId;
    }

}
