package toyproject.stylecast.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Outfit;
import toyproject.stylecast.domain.Style;
import toyproject.stylecast.domain.geocode.Location;
import toyproject.stylecast.domain.recommendframe.Weather;
import toyproject.stylecast.dto.outfit.OutfitDto;
import toyproject.stylecast.dto.outfit.OutfitSearchBasic;
import toyproject.stylecast.dto.WeatherDto;
import toyproject.stylecast.dto.outfit.OutfitSearchPersonal;
import toyproject.stylecast.repository.data.MemberDataRepository;
import toyproject.stylecast.repository.data.OutfitDataRepository;
import toyproject.stylecast.weather.WeatherService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OutfitDataService {
    private final OutfitDataRepository outfitDataRepository;
    private final MemberDataRepository memberDataRepository;
    private final GeocodingService geocodingService;
    private final WeatherService weatherService;

    @Transactional
    public Long outfit(Outfit outfit){
        outfitDataRepository.save(outfit);
        return outfit.getId();
    }

    @Transactional
    public Long outfit(Long memberId, String name, String description, Style style, Long top, Long bottom, Long outer){
        Member member = memberDataRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Outfit outfit = Outfit.creatOutfit(member, name, style, description, top, bottom, outer);
        outfitDataRepository.save(outfit);

        return outfit.getId();
    }

    public List<Outfit> findAllOutfit(){
        return outfitDataRepository.findAll();
    }

    public Outfit findOutfit(Long outfitId){
        return outfitDataRepository.findById(outfitId).get();
    }


    public List<Outfit> findOutfitByMember(Long memberId){
        return outfitDataRepository.findOutfitsByMember_Id(memberId);
    }

    public List<Outfit> findOutfitByMemberWithStyle(Long memberId, Style style){
        return outfitDataRepository.findOutfitByMemberIdAndStyle(memberId, style);
    }

    public List<OutfitDto> recommendOutfitBasic(Long memberId){
        Member member = memberDataRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        String coordinates = geocodingService.getCoordinates(member.getLocationList().get(0));
        Location location = geocodingService.getLocation(coordinates);
        WeatherDto weatherData = weatherService.getWeatherDataByRecommend(location.getLat(), location.getLon());

        OutfitSearchBasic condition = new OutfitSearchBasic();
        condition.setWeather(Weather.valueOf(weatherData.getMain()));
        condition.setTemperature(weatherData.getTemp());

        return outfitDataRepository.RecommendOutfitBasic(condition);
    }

    public List<OutfitDto> recommendOutfitByStyle(Long memberId, Style style){
        Member member = memberDataRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        String coordinates = geocodingService.getCoordinates(member.getLocationList().get(0));
        Location location = geocodingService.getLocation(coordinates);
        WeatherDto weatherData = weatherService.getWeatherDataByRecommend(location.getLat(), location.getLon());

        OutfitSearchPersonal condition = new OutfitSearchPersonal();
        condition.setProfile(member.getProfile());
        condition.setStyle(style);

        return outfitDataRepository.RecommendOutfitByPersonalized(condition);
    }

    public List<OutfitDto> recommendBySimilarStyle(Style style){
        List<Member> memberList = memberDataRepository.findByPreferStyle(style);

        List<Outfit> outfitList = new ArrayList<>();
        for (Member member : memberList) {
            outfitList.addAll(member.getOutfitList());
        }
        List<OutfitDto> outfitDtoList = outfitList.stream()
                .map(o -> new OutfitDto(o.getName(), o.getDescription(), o.getStyle(), o.getTop_id(), o.getBottom_id(), o.getOuterwear_id()))
                .collect(Collectors.toList());

        return outfitDtoList;
    }

    @Transactional
    public void updateName(Long outfitId, String name){
        Outfit outfit = outfitDataRepository.findById(outfitId).get();
        outfit.setName(name);
    }

    @Transactional
    public void updateDescription(Long outfitId, String description){
        Outfit outfit = outfitDataRepository.findById(outfitId).get();
        outfit.setDescription(description);
    }

    @Transactional
    public void updateTop(Long outfitId, Long id){
        Outfit outfit = outfitDataRepository.findById(outfitId).get();
        outfit.setTop_id(id);
    }
    @Transactional
    public void updateBottom(Long outfitId, Long id){
        Outfit outfit = outfitDataRepository.findById(outfitId).get();
        outfit.setBottom_id(id);
    }
    @Transactional
    public void updateOuter(Long outfitId, Long id){
        Outfit outfit = outfitDataRepository.findById(outfitId).get();
        outfit.setOuterwear_id(id);
    }
}
