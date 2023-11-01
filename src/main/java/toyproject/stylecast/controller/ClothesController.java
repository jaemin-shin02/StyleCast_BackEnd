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
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.clothes.Category;
import toyproject.stylecast.dto.clothes.ClothesDto;
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
    private final FileRepository fileRepository;

    @GetMapping("/clothes/create")
    public String AddClothesPage(Model model){

        return "clothesUpdate";
    }

    @PostMapping("/clothes/create")
    public String AddClothes(@Valid CreateClothesRequest request) throws IOException {
        log.info("옷 추가 시도");
        System.out.println("request = " + request);

//        Long photoId = fileService.saveFile(request.getFile());
//        FileInfo photo = fileRepository.findById(photoId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사진파일 입니다."));

        Long clothesId = clothesService.clothes(request.getMemberId(), request.getName(), request.getCategory(), request.getColor(), request.getSeason());
//        clothesService.setPhoto(clothesId, photo);

        return "redirect:/main";
    }

    @GetMapping("/clothes/all")
    public String viewClothes(Model model){
        // Spring Security를 사용하여 현재 사용자의 Authentication 객체를 얻어옴
        Long memberId = getMemberId();

        List<Clothes> clothesList = clothesService.findClothesByMemberId(memberId);
        List<ClothesDto> collect = getClothesDtos(clothesList);

        model.addAttribute("memberId", memberId);
        model.addAttribute("clothesList", collect);

        return "/clothes";
    }

    @GetMapping("/clothes/top")
    public String viewTop(Model model){
        Long memberId = getMemberId();

        List<Clothes> clothesList = clothesService.findClothesByMemberIdWithCategory(memberId, Category.상의);
        List<ClothesDto> collect = getClothesDtos(clothesList);

        model.addAttribute("memberId", memberId);
        model.addAttribute("clothesList", collect);

        return "/clothes";
    }

    @GetMapping("/clothes/bottom")
    public String viewBottom(Model model){
        Long memberId = getMemberId();

        List<Clothes> clothesList = clothesService.findClothesByMemberIdWithCategory(memberId, Category.바지);
        List<ClothesDto> collect = getClothesDtos(clothesList);

        model.addAttribute("memberId", memberId);
        model.addAttribute("clothesList", collect);

        return "/clothes";
    }
    @GetMapping("/clothes/outer")
    public String viewOuter(Model model){
        Long memberId = getMemberId();

        List<Clothes> clothesList = clothesService.findClothesByMemberIdWithCategory(memberId, Category.아우터);
        List<ClothesDto> collect = getClothesDtos(clothesList);

        model.addAttribute("memberId", memberId);
        model.addAttribute("clothesList", collect);

        return "/clothes";
    }
    @GetMapping("/clothes/skirt")
    public String viewSkirt(Model model){
        Long memberId = getMemberId();

        List<Clothes> clothesList = clothesService.findClothesByMemberIdWithCategory(memberId, Category.스커트);
        List<ClothesDto> collect = getClothesDtos(clothesList);

        model.addAttribute("memberId", memberId);
        model.addAttribute("clothesList", collect);

        return "/clothes";
    }

    @GetMapping("/clothes/shoes")
    public String viewShoes(Model model){
        Long memberId = getMemberId();

        List<Clothes> clothesList = clothesService.findClothesByMemberIdWithCategory(memberId, Category.신발);
        List<ClothesDto> collect = getClothesDtos(clothesList);

        model.addAttribute("memberId", memberId);
        model.addAttribute("clothesList", collect);

        return "/clothes";
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
    private static List<ClothesDto> getClothesDtos(List<Clothes> clothesList) {
        List<ClothesDto> collect = clothesList.stream()
                .map(c -> new ClothesDto(c.getName(), c.getCategory(), c.getColor(), c.getSeason()))
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
