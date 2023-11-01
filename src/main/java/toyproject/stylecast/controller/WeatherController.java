package toyproject.stylecast.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.WeatherData;
import toyproject.stylecast.domain.geocode.Location;
import toyproject.stylecast.dto.WeatherDto;
import toyproject.stylecast.service.GeocodingService;
import toyproject.stylecast.service.MemberDataService;
import toyproject.stylecast.weather.WeatherService;


@Slf4j
@Controller
@RequiredArgsConstructor
public class WeatherController {

    private final MemberDataService memberDataService;
    private final GeocodingService geocodingService;
    private final WeatherService weatherService;

    @GetMapping("/weather")
    public String weatherData(Model model){
        Long memberId = getMemberId();

        Member member = memberDataService.findOne(memberId);
        String coordinates = geocodingService.getCoordinates(member.getLocationList().get(0));
        Location location = geocodingService.getLocation(coordinates);

        WeatherData weatherData = weatherService.getWeatherData(location.getLat(), location.getLon());

        model.addAttribute("memberId", memberId);
        model.addAttribute("weatherData", weatherData);

        return "weather";
    }

    private Long getMemberId() {
        // Spring Security를 사용하여 현재 사용자의 Authentication 객체를 얻어옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Authentication 객체에서 사용자 이름 또는 기타 정보를 추출
        String memberEmail = authentication.getName();
        Member member = memberDataService.findByEmail(memberEmail);
        Long memberId = member.getId();

        return memberId;
    }
}
