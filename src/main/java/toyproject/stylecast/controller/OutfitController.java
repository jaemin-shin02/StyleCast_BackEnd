package toyproject.stylecast.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.stylecast.domain.*;
import toyproject.stylecast.domain.clothes.Category;
import toyproject.stylecast.dto.outfit.CreateOutfitRequest;
import toyproject.stylecast.dto.outfit.OutfitPostDto;
import toyproject.stylecast.dto.outfit.ViewOutfit;
import toyproject.stylecast.repository.data.FileRepository;
import toyproject.stylecast.service.ClothesDataService;
import toyproject.stylecast.service.FileService;
import toyproject.stylecast.service.MemberDataService;
import toyproject.stylecast.service.OutfitDataService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OutfitController {
    
    private final MemberDataService memberService;
    private final ClothesDataService clothesService;
    private final OutfitDataService outfitService;
    private final FileService fileService;

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
    public String AddClothes(@Valid CreateOutfitRequest request) throws IOException {
        log.info("옷 추가 시도");
        System.out.println("request = " + request);

        Long photoId = fileService.saveFile(request.getFile());
        FileInfo photo = fileService.findById(photoId);

        Long outfitId = outfitService.outfit(request.getMemberId(), request.getName(), request.getDescription(), request.getStyle(), request.getTop_id(), request.getBottom_id(), request.getOuterwear_id());
        outfitService.setPhoto(outfitId, photo);

        return "redirect:/main";
    }

    @GetMapping("/my/outfit")
    public String myOutfit(Model model){
        Long memberId = getMemberId();
        List<Outfit> outfitList = outfitService.findOutfitByMember(memberId);
        List<OutfitPostDto> collect = outfitList.stream()
                .map(o -> new OutfitPostDto(o.getPhoto().getId(), o.getName(), o.getDescription(), o.getStyle()
                        , clothesService.getName(o.getTop_id())
                        , clothesService.getName(o.getBottom_id())
                        , clothesService.getName(o.getOuterwear_id())
                        , clothesService.getName(o.getShoe_id()))
                ).collect(Collectors.toList());

        model.addAttribute("memberId", memberId);
        model.addAttribute("outfitList", collect);

        return "/outfitBoard";
    }

    @GetMapping("/outfit/all")
    public String viewOutfits(Model model){
        Long memberId = getMemberId();
        List<Outfit> outfitList = outfitService.findOutfitByMember(memberId);
        List<ViewOutfit> collect = getViewOutfits(outfitList);
        for (ViewOutfit viewOutfit : collect) {
            System.out.println("viewOutfit = " + viewOutfit.toString());
        }
        model.addAttribute("memberId", memberId);
        model.addAttribute("outfitList", collect);

        return "outfitList";
    }

    @GetMapping("/outfit/casual")
    public String viewCasual(Model model){
        Long memberId = getMemberId();
        List<Outfit> outfitList = outfitService.findOutfitByMemberWithStyle(memberId, Style.캐주얼);
        List<ViewOutfit> collect = getViewOutfits(outfitList);

        model.addAttribute("memberId", memberId);
        model.addAttribute("outfitList", collect);

        return "outfitList";
    }

    @GetMapping("/outfit/chic")
    public String viewChic(Model model){
        Long memberId = getMemberId();
        List<Outfit> outfitList = outfitService.findOutfitByMemberWithStyle(memberId, Style.시크);
        List<ViewOutfit> collect = getViewOutfits(outfitList);

        model.addAttribute("memberId", memberId);
        model.addAttribute("outfitList", collect);

        return "outfitList";
    }

    @GetMapping("/outfit/dandy")
    public String viewDandy(Model model){
        Long memberId = getMemberId();
        List<Outfit> outfitList = outfitService.findOutfitByMemberWithStyle(memberId, Style.댄디);
        List<ViewOutfit> collect = getViewOutfits(outfitList);

        model.addAttribute("memberId", memberId);
        model.addAttribute("outfitList", collect);

        return "outfitList";
    }
    @GetMapping("/outfit/formal")
    public String viewFormal(Model model){
        Long memberId = getMemberId();
        List<Outfit> outfitList = outfitService.findOutfitByMemberWithStyle(memberId, Style.포멀);
        List<ViewOutfit> collect = getViewOutfits(outfitList);

        model.addAttribute("memberId", memberId);
        model.addAttribute("outfitList", collect);

        return "outfitList";
    }
    @GetMapping("/outfit/girlish")
    public String viewGirlish(Model model){
        Long memberId = getMemberId();
        List<Outfit> outfitList = outfitService.findOutfitByMemberWithStyle(memberId, Style.걸리시);
        List<ViewOutfit> collect = getViewOutfits(outfitList);

        model.addAttribute("memberId", memberId);
        model.addAttribute("outfitList", collect);

        return "outfitList";
    }
    @GetMapping("/outfit/retro")
    public String viewRetro(Model model){
        Long memberId = getMemberId();
        List<Outfit> outfitList = outfitService.findOutfitByMemberWithStyle(memberId, Style.레트로);
        List<ViewOutfit> collect = getViewOutfits(outfitList);

        model.addAttribute("memberId", memberId);
        model.addAttribute("outfitList", collect);

        return "outfitList";
    }
    @GetMapping("/outfit/sporty")
    public String viewSporty(Model model){
        Long memberId = getMemberId();
        List<Outfit> outfitList = outfitService.findOutfitByMemberWithStyle(memberId, Style.스포츠);
        List<ViewOutfit> collect = getViewOutfits(outfitList);

        model.addAttribute("memberId", memberId);
        model.addAttribute("outfitList", collect);

        return "outfitList";
    }
    @GetMapping("/outfit/street")
    public String viewStreet(Model model){
        Long memberId = getMemberId();
        List<Outfit> outfitList = outfitService.findOutfitByMemberWithStyle(memberId, Style.스트릿);
        List<ViewOutfit> collect = getViewOutfits(outfitList);

        model.addAttribute("memberId", memberId);
        model.addAttribute("outfitList", collect);

        return "outfitList";
    }
    @GetMapping("/outfit/gorpcore")
    public String viewGorpcore(Model model){
        Long memberId = getMemberId();
        List<Outfit> outfitList = outfitService.findOutfitByMemberWithStyle(memberId, Style.고프코어);
        List<ViewOutfit> collect = getViewOutfits(outfitList);

        model.addAttribute("memberId", memberId);
        model.addAttribute("outfitList", collect);

        return "outfitList";
    }

    @NotNull
    private List<ViewOutfit> getViewOutfits(List<Outfit> outfitList) {
        List<ViewOutfit> collect = outfitList.stream()
                .map(o -> new ViewOutfit(o.getName(), o.getDescription(), o.getStyle()
                        , clothesService.getName(o.getTop_id())
                        , clothesService.getName(o.getBottom_id())
                        , clothesService.getName(o.getOuterwear_id())
                        , clothesService.getName(o.getShoe_id())))
                .collect(Collectors.toList());
        return collect;
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
