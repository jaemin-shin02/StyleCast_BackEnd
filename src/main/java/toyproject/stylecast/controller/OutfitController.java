package toyproject.stylecast.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import toyproject.stylecast.domain.Clothes;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Outfit;
import toyproject.stylecast.dto.ClothesDto;
import toyproject.stylecast.dto.CreateClothesRequest;
import toyproject.stylecast.dto.CreateOutfitRequest;
import toyproject.stylecast.dto.OutfitDto;
import toyproject.stylecast.service.ClothesService;
import toyproject.stylecast.service.MemberService;
import toyproject.stylecast.service.OutfitService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OutfitController {

    private final OutfitService outfitService;
    private final ClothesService clothesService;
    private final MemberService memberService;

    @GetMapping("/api/outfit/{id}")
    @ResponseBody
    public Result clothesList(@PathVariable("id") Long memberId){
        List<Outfit> outfitList = outfitService.findOutfitByMember(memberId);
        List<OutfitDto> collect = outfitList.stream()
                .map(o -> new OutfitDto(o.getName(), o.getDescription(), o.getStyle(), o.getTop_id(), o.getBottom_id(), o.getOuterwear_id()))
                .collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }

    @PostMapping("/api/outfit/create/{id}")
    public CreateOutfitResponse saveOutfit(@RequestBody @Valid CreateOutfitRequest request, @PathVariable("id") Long memberId){
        Member member = memberService.findOne(memberId);
        Outfit outfit = Outfit.creatOutfit(member, request.getName(), request.getStyle(), request.getDescription(), request.getTop_id(), request.getBottom_id(), request.getOuterwear_id());
        Long outfitId = outfitService.outfit(outfit);

        return new CreateOutfitResponse(outfitId);
    }


    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T date;
    }

    @Data
    static class CreateOutfitResponse {
        private Long id;

        public CreateOutfitResponse(Long id) {
            this.id = id;
        }
    }
}
