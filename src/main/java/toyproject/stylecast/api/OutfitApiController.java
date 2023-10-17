package toyproject.stylecast.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Outfit;
import toyproject.stylecast.domain.Style;
import toyproject.stylecast.domain.geocode.Location;
import toyproject.stylecast.domain.recommendframe.Weather;
import toyproject.stylecast.dto.CreateOutfitRequest;
import toyproject.stylecast.dto.OutfitDto;
import toyproject.stylecast.dto.OutfitSearchCondition;
import toyproject.stylecast.dto.WeatherDto;
import toyproject.stylecast.repository.OutfitDataRepository;
import toyproject.stylecast.service.ClothesService;
import toyproject.stylecast.service.GeocodingService;
import toyproject.stylecast.service.MemberService;
import toyproject.stylecast.service.OutfitService;
import toyproject.stylecast.weather.WeatherService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OutfitApiController {

    private final OutfitService outfitService;
    private final ClothesService clothesService;
    private final MemberService memberService;

    private final GeocodingService geocodingService;
    private final WeatherService weatherService;
    private final OutfitDataRepository outfitRepository;

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

    @PostMapping("/api/outfit/recommend/{id}")
    public Result recommendByWeather(@PathVariable("id") Long memberId){

        Member member = memberService.findOne(memberId);
        String coordinates = geocodingService.getCoordinates(member.getLocationList().get(0));
        Location location = geocodingService.getLocation(coordinates);
        WeatherDto weatherData = weatherService.getWeatherData(location.getLat(), location.getLon());

        OutfitSearchCondition condition = new OutfitSearchCondition();
        condition.setProfile(member.getProfile());
        condition.setWeather(Weather.valueOf(weatherData.getMain()));
        condition.setTemperature(weatherData.getTemp());

        List<OutfitDto> outfitDtoList = outfitRepository.RecommendOutfit(condition);

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
