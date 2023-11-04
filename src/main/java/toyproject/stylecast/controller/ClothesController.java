package toyproject.stylecast.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.stylecast.domain.Clothes;
import toyproject.stylecast.domain.FileInfo;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.clothes.Category;
import toyproject.stylecast.dto.clothes.ClothesDto;
import toyproject.stylecast.dto.clothes.ClothesPostDto;
import toyproject.stylecast.dto.clothes.CreateClothesRequest;
import toyproject.stylecast.repository.data.FileRepository;
import toyproject.stylecast.service.ClothesDataService;
import toyproject.stylecast.service.FileService;
import toyproject.stylecast.service.MemberDataService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ClothesController {

    private final MemberDataService memberService;
    private final ClothesDataService clothesService;

    private final FileService fileService;

    @GetMapping("/clothes/create")
    public String AddClothesPage(Model model){

        return "clothesUpdate";
    }

    @PostMapping("/clothes/create")
    public String AddClothes(@Valid CreateClothesRequest request) throws IOException {
        log.info("옷 추가 시도", request);

        Long photoId = fileService.saveFile(request.getFile());
        FileInfo photo = fileService.findById(photoId);

        Long clothesId = clothesService.clothes(request.getMemberId(), request.getName(), request.getCategory(), request.getColor(), request.getSeason());
        clothesService.setPhoto(clothesId, photo);
        clothesService.setTop(clothesId, request.getTop());
        clothesService.setPants(clothesId, request.getPants());
        clothesService.setOuter(clothesId, request.getOuter());
        clothesService.setShoes(clothesId, request.getShoes());
        clothesService.setOnepiece(clothesId, request.getOnepiece());
        clothesService.setSkirt(clothesId, request.getSkirt());


        return "redirect:/main";
    }

    @GetMapping("/my/closet/all")
    public String myCloset(Model model){
        Long memberId = getMemberId();
        List<Clothes> clothesList = clothesService.findClothesByMemberId(memberId);
        List<ClothesPostDto> collect = getClothesPostDto(clothesList);

        model.addAttribute("memberId", memberId);
        model.addAttribute("clothesList", collect);
        return "/clothesBoard";
    }

    @GetMapping("/my/closet/top")
    public String viewTop(Model model){
        Long memberId = getMemberId();

        List<Clothes> clothesList = clothesService.findClothesByMemberIdWithCategory(memberId, Category.상의);
        List<ClothesPostDto> collect = getClothesPostDto(clothesList);

        model.addAttribute("memberId", memberId);
        model.addAttribute("clothesList", collect);

        return "/clothesBoard";
    }

    @GetMapping("/my/closet/bottom")
    public String viewBottom(Model model){
        Long memberId = getMemberId();

        List<Clothes> clothesList = clothesService.findClothesByMemberIdWithCategory(memberId, Category.바지);
        List<ClothesPostDto> collect = getClothesPostDto(clothesList);

        model.addAttribute("memberId", memberId);
        model.addAttribute("clothesList", collect);

        return "/clothesBoard";
    }
    @GetMapping("/my/closet/outer")
    public String viewOuter(Model model){
        Long memberId = getMemberId();

        List<Clothes> clothesList = clothesService.findClothesByMemberIdWithCategory(memberId, Category.아우터);
        List<ClothesPostDto> collect = getClothesPostDto(clothesList);

        model.addAttribute("memberId", memberId);
        model.addAttribute("clothesList", collect);

        return "/clothesBoard";
    }
    @GetMapping("/my/closet/skirt")
    public String viewSkirt(Model model){
        Long memberId = getMemberId();

        List<Clothes> clothesList = clothesService.findClothesByMemberIdWithCategory(memberId, Category.스커트);
        List<ClothesPostDto> collect = getClothesPostDto(clothesList);

        model.addAttribute("memberId", memberId);
        model.addAttribute("clothesList", collect);

        return "/clothesBoard";
    }

    @GetMapping("/my/closet/shoes")
    public String viewShoes(Model model){
        Long memberId = getMemberId();

        List<Clothes> clothesList = clothesService.findClothesByMemberIdWithCategory(memberId, Category.신발);
        List<ClothesPostDto> collect = getClothesPostDto(clothesList);

        model.addAttribute("memberId", memberId);
        model.addAttribute("clothesList", collect);

        return "/clothesBoard";
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

    @NotNull
    private static List<ClothesPostDto> getClothesPostDto(List<Clothes> clothesList) {
        List<ClothesPostDto> collect = clothesList.stream()
                .map(c -> new ClothesPostDto(c.getPhoto().getId(), c.getName(), c.getCategory(), c.getColor(), c.getSeason()))
                .collect(Collectors.toList());
        return collect;
    }

    @GetMapping("/clothes/success")
    public String joinSuccess(){
        return "success";
    }
    @GetMapping("/clothes/failure")
    public String joinFailure(){
        return "failure";
    }

}
