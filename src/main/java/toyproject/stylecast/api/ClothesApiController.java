package toyproject.stylecast.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import toyproject.stylecast.domain.Clothes;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.dto.clothes.ClothesDto;
import toyproject.stylecast.dto.clothes.CreateClothesRequest;
import toyproject.stylecast.service.ClothesService;
import toyproject.stylecast.service.MemberService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ClothesApiController {
    private final ClothesService clothesService;
    private final MemberService memberService;

    @GetMapping("/api/clothes/{id}")
    @ResponseBody
    public Result clothesList(@PathVariable("id") Long memberId){
        List<Clothes> findClothes = clothesService.findClothesByMemberId(memberId);
        List<ClothesDto> collect = findClothes.stream()
                .map(c -> new ClothesDto(c.getName(), c.getCategory(), c.getColor(), c.getSeason()))
                .collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }

    @PostMapping("/api/clothes/create/{id}")
    public CreateClothesResponse saveClothes(@RequestBody @Valid CreateClothesRequest request, @PathVariable("id") Long memberId){
        Member member = memberService.findOne(memberId);
        Clothes clothes = Clothes.creatClothes(member, request.getName(), request.getCategory(), request.getColor(), request.getSeason());

        Long clothesId = clothesService.clothes(clothes);

        return new CreateClothesResponse(clothesId);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T date;
    }

    @Data
    static class CreateClothesResponse {
        private Long id;

        public CreateClothesResponse(Long id) {
            this.id = id;
        }
    }
}
