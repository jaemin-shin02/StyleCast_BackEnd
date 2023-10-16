package toyproject.stylecast.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.stylecast.domain.Clothes;
import toyproject.stylecast.domain.FileInfo;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.dto.CreateClothesRequest;
import toyproject.stylecast.repository.FileRepository;
import toyproject.stylecast.service.ClothesDataService;
import toyproject.stylecast.service.FileService;
import toyproject.stylecast.service.MemberDataService;
import toyproject.stylecast.service.MemberService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ClothesController {

    private final MemberDataService memberService;
    private final ClothesDataService clothesService;

    private final FileService fileService;
    private final FileRepository fileRepository;

    @GetMapping("/clothes/create/{id}")
    public String AddClothesPage(Model model, @PathVariable("id") Long memberId){

        // 경로 변수 'id'를 모델에 추가하여 뷰로 전달
        model.addAttribute("memberId", memberId);

        return "clothesUpdate";
    }

    @PostMapping("/clothes/create/{id}")
    public String AddClothes(@PathVariable("id") Long memberId, @Valid CreateClothesRequest request) throws IOException {
        log.info("옷 추가 시도");
        System.out.println("request = " + request);

        Long photoId = fileService.saveFile(request.getFile());
        FileInfo photo = fileRepository.findById(photoId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사진파일 입니다."));

        Long clothesId = clothesService.clothes(memberId, request.getName(), request.getCategory(), request.getColor(), request.getSeason());
//        clothesService.setPhoto(clothesId, photo);

        return "redirect:/clothes/success";
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
