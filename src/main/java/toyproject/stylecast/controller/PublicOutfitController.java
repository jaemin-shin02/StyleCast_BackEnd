package toyproject.stylecast.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Outfit;
import toyproject.stylecast.domain.Style;
import toyproject.stylecast.domain.WeatherData;
import toyproject.stylecast.domain.geocode.Location;
import toyproject.stylecast.dto.outfit.OotdDto;
import toyproject.stylecast.dto.outfit.OutfitDetail;
import toyproject.stylecast.dto.outfit.OutfitDto;
import toyproject.stylecast.dto.outfit.OutfitPostDto;
import toyproject.stylecast.service.*;
import toyproject.stylecast.weather.WeatherService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PublicOutfitController {
    private final MemberDataService memberService;
    private final ClothesDataService clothesService;
    private final OutfitDataService outfitService;
    private final GeocodingService geocodingService;
    private final WeatherService weatherService;

    @GetMapping("/main/weather")
    public String dayOutfit(Model model){
        Long memberId = getMemberId();

        Outfit outfit = outfitService.recommendOneWithMember(memberId);
        if(outfit != null) {
            OutfitPostDto outfitPostDto = new OutfitPostDto(outfit.getPhoto().getId(), outfit.getName(), outfit.getDescription(), outfit.getStyle()
                    , clothesService.getName(outfit.getTop_id())
                    , clothesService.getName(outfit.getBottom_id())
                    , clothesService.getName(outfit.getOuterwear_id())
                    , clothesService.getName(outfit.getShoe_id()));
            model.addAttribute("outfitPostDto", outfitPostDto);
        }
        Member member = memberService.findOne(memberId);
        String coordinates = geocodingService.getCoordinates(member.getLocationList().get(0));
        Location location = geocodingService.getLocation(coordinates);

        WeatherData weatherData = weatherService.getWeatherData(location.getLat(), location.getLon());

        model.addAttribute("memberId", memberId);
        model.addAttribute("weatherData", weatherData);

        return "/subMain";
    }

    @GetMapping("/ootd")
    public String ootdList(Model model){
        List<Outfit> ootdList = outfitService.findOotdList();
        List<OotdDto> collect = ootdList.stream()
                .map(o -> new OotdDto(o.getId(), o.getPhoto().getId(), o.getMember().getNickname(), o.getName(), o.getLikes()))
                .collect(Collectors.toList());

        model.addAttribute("ootdList", collect);

        return "/ootdList";
    }

    @GetMapping("/ootd/detail/{id}")
    public String ootdDetail(Model model, @PathVariable Long id){

        Outfit outfit = outfitService.findOutfit(id);
        OutfitDetail outfitDetail = new OutfitDetail(outfit.getPhoto().getId(), outfit.getMember().getNickname()
                , outfit.getName(), outfit.getDescription(), outfit.getStyle()
                , clothesService.getName(outfit.getTop_id())
                , clothesService.getName(outfit.getBottom_id())
                , clothesService.getName(outfit.getOuterwear_id())
                , clothesService.getName(outfit.getShoe_id())
                , outfit.getSeason(), outfit.getWeatherList(), outfit.getLikes()
        );

        model.addAttribute("outfitId", id);
        model.addAttribute("outfitDetail", outfitDetail);

        return "/outfitDetail";
    }

    @PostMapping("/ootd/like/{id}")
    public ResponseEntity<?> likeOutfit(@PathVariable Long id) {
        // 코디를 찾아서 좋아요 수를 증가시키는 서비스 메서드 호출
        Long memberId = getMemberId();
        int likeCount = outfitService.Like(id, memberId);
        return ResponseEntity.ok(likeCount); // 업데이트된 좋아요 수 응답
    }
    @PostMapping("/ootd/unlike/{id}")
    public ResponseEntity<?> unlikeOutfit(@PathVariable Long id) {
        // 코디를 찾아서 좋아요 수를 감소시키는 서비스 메서드 호출
        Long memberId = getMemberId();
        int likeCount = outfitService.unLike(id, memberId);
        return ResponseEntity.ok(likeCount); // 업데이트된 좋아요 수 응답
    }

    @GetMapping("/recommend/basic")
    public String recommendBasic(Model model){
        Long memberId = getMemberId();
        List<Outfit> outfitList = outfitService.recommendOutfitBasic(memberId);
        List<OotdDto> collect = outfitList.stream()
                .map(o -> new OotdDto(o.getId(), o.getPhoto().getId(), o.getMember().getNickname(), o.getName(), o.getLikes()))
                .collect(Collectors.toList());

        model.addAttribute("recommendList", collect);

        return "/recommendList";
    }

    @GetMapping("/recommend/style/select")
    public String selectStyle(){

        return "/selectStyle";
    }

    @GetMapping("/recommend/style")
    public String recommendStyle(@RequestParam(name = "style", required = false) Style selectedStyle, Model model){
        Long memberId = getMemberId();
        Member member = memberService.findOne(memberId);

        if (selectedStyle == null) {
            // 기본 스타일 (예: 캐주얼)을 설정합니다.
            selectedStyle = Style.캐주얼;
        }

        List<Outfit> outfitList = outfitService.recommendOutfitByStyle(memberId, selectedStyle);
        List<OotdDto> collect = outfitList.stream()
                .map(o -> new OotdDto(o.getId(), o.getPhoto().getId(), o.getMember().getNickname(), o.getName(), o.getLikes()))
                .collect(Collectors.toList());

        model.addAttribute("recommendList", collect);
        model.addAttribute("selectedStyle", selectedStyle);

        return "recommendList";
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
