package toyproject.stylecast.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Outfit;
import toyproject.stylecast.dto.outfit.CreateOutfitRequest;
import toyproject.stylecast.dto.outfit.OutfitDto;
import toyproject.stylecast.dto.outfit.OutfitSearchCondition;
import toyproject.stylecast.service.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OutfitApiController {

    private final OutfitService outfitService;
    private final MemberService memberService;
    private final MemberDataService memberDataService;
    private final OutfitDataService outfitDataService;

    @GetMapping("/api/outfit/{id}")
    @ResponseBody
    public Result clothesList(@PathVariable("id") Long memberId){
        List<Outfit> outfitList = outfitDataService.findOutfitByMember(memberId);
        List<OutfitDto> collect = outfitList.stream()
                .map(o -> new OutfitDto(o.getName(), o.getDescription(), o.getStyle(), o.getTop_id(), o.getBottom_id(), o.getOuterwear_id()))
                .collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }

    @PostMapping("/api/outfit/create/{id}")
    public CreateOutfitResponse saveOutfit(@RequestBody @Valid CreateOutfitRequest request, @PathVariable("id") Long memberId){
        Member member = memberDataService.findOne(memberId);
        Outfit outfit = Outfit.creatOutfit(member, request.getName(), request.getStyle(), request.getDescription(), request.getTop_id(), request.getBottom_id(), request.getOuterwear_id());
        Long outfitId = outfitDataService.outfit(outfit);

        return new CreateOutfitResponse(outfitId);
    }

    @PostMapping("/api/outfit/recommend/basic/{id}")
    public Result recommendByWeather(@PathVariable("id") Long memberId){

        List<OutfitDto> outfitDtoList = outfitDataService.recommendOutfitBasic(memberId);

        return new Result(outfitDtoList.size(), outfitDtoList);
    }

    @PostMapping("/api/outfit/recommend/style/{id}")
    public Result recommendByStyle(@RequestBody @Valid OutfitSearchCondition request, @PathVariable("id") Long memberId){

        List<OutfitDto> outfitDtoList = outfitDataService.recommendOutfitByStyle(memberId, request.getStyle());

        return new Result(outfitDtoList.size(), outfitDtoList);
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
