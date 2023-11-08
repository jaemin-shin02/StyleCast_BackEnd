package toyproject.stylecast.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.*;
import toyproject.stylecast.domain.clothes.Outwear;
import toyproject.stylecast.domain.clothes.Pants;
import toyproject.stylecast.domain.clothes.Skirt;
import toyproject.stylecast.domain.clothes.Top;
import toyproject.stylecast.domain.geocode.Location;
import toyproject.stylecast.domain.recommendframe.Weather;
import toyproject.stylecast.dto.outfit.*;
import toyproject.stylecast.dto.WeatherDto;
import toyproject.stylecast.repository.data.MemberDataRepository;
import toyproject.stylecast.repository.data.OutfitDataRepository;
import toyproject.stylecast.weather.WeatherService;

import java.util.*;
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
    public Long outfit(Long memberId, String name, String description, Style style, Long top, Long bottom, Long shoe){
        Member member = memberDataRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Outfit outfit = Outfit.creatOutfit(member, name, style, description, top, bottom, shoe);
        outfitDataRepository.save(outfit);

        return outfit.getId();
    }

    @Transactional
    public void setOuter(Long outfitId, Long outerId){
        Outfit outfit = outfitDataRepository.findById(outfitId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코디입니다."));
        outfit.setOuterwear_id(outerId);
    }

    @Transactional
    public void setPhoto(Long outfitId, FileInfo photo){
        Outfit outfit = outfitDataRepository.findById(outfitId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코디입니다."));
        outfit.setPhoto(photo);
    }

    @Transactional
    public void setSeason(Long outfitId, Season season){
        Outfit outfit = outfitDataRepository.findById(outfitId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코디입니다."));
        outfit.setSeason(season);
    }
    @Transactional
    public void setWeatherList(Long outfitId, List<Weather> weatherList){
        Outfit outfit = outfitDataRepository.findById(outfitId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코디입니다."));
        outfit.setWeatherList(weatherList);
    }

    public List<Outfit> findOotdList(){
       return outfitDataRepository.ootdList();
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

    public Outfit recommendOneWithMember(Long memberId){
        Member member = memberDataRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        String coordinates = geocodingService.getCoordinates(member.getLocationList().get(0));
        Location location = geocodingService.getLocation(coordinates);
        WeatherDto weatherData = weatherService.getWeatherDataByRecommend(location.getLat(), location.getLon());

        OutfitSearchMy condition = new OutfitSearchMy();
        condition.setMemberId(memberId);
        condition.setWeather(Weather.valueOf(weatherData.getMain()));
        condition.setTemperature(weatherData.getTemp());

        List<Outfit> outfitDtoList = outfitDataRepository.RecommendOutfitDay(condition);

        if (outfitDtoList.isEmpty()) {
            // outfitDtoList가 비어 있다면 무작위 추천할 수 없음
            return null;
        }
        for (Outfit outfit : outfitDtoList) {
            System.out.println("outfit = " + outfit);
        }

        // Random 객체 생성
        Random random = new Random();

        // outfitDtoList 크기에 대한 무작위 인덱스 생성
        int randomIndex = random.nextInt(outfitDtoList.size());

        // 무작위로 선택한 OutfitDto 반환
        return outfitDtoList.get(randomIndex);
    }

    public List<Outfit> recommendOutfitBasic(Long memberId){
        Member member = memberDataRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        String coordinates = geocodingService.getCoordinates(member.getLocationList().get(0));
        Location location = geocodingService.getLocation(coordinates);
        WeatherDto weatherData = weatherService.getWeatherDataByRecommend(location.getLat(), location.getLon());

        OutfitSearchBasic condition = new OutfitSearchBasic();
        condition.setWeather(Weather.valueOf(weatherData.getMain()));
        condition.setTemperature(weatherData.getTemp());

        return outfitDataRepository.RecommendOutfitBasic(condition);
    }

    public List<Outfit> recommendOutfitByStyle(Long memberId, Style style){
        Member member = memberDataRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        String coordinates = geocodingService.getCoordinates(member.getLocationList().get(0));
        Location location = geocodingService.getLocation(coordinates);
        WeatherDto weatherData = weatherService.getWeatherDataByRecommend(location.getLat(), location.getLon());

        OutfitSearchPersonal condition = new OutfitSearchPersonal();
        condition.setProfile(member.getProfile());
        condition.setStyle(style);

        return outfitDataRepository.RecommendOutfitByPersonalized(condition);
    }

    public OutfitPostDto OutfitByTemperature(float temperature) {
        // 온도 범위에 따른 의상 정보를 매핑하는 Map
        Map<String, OutfitPostDto> temperatureMap = new HashMap<>();

        // 온도 범위별 의상 정보를 Map에 저장
        temperatureMap.put("한여름", new OutfitPostDto(null, "한여름", "여름은 입을 수 있는 아이템이 제한되기 때문에 티셔츠 소재와 그날의 무드를 맞추자", Style.캐주얼, "반팔, 린넨셔츠", "반바지, 와이드팬츠 등", "X", "포인트 신발"));
        temperatureMap.put("늦봄, 초여름", new OutfitPostDto(null,"늦봄, 초여름", "긴 상의와 반바지의 조화를 이룰 수 있는 온도", Style.캐주얼, "반팔티, 긴팔티, 반팔니트 등", "반바지, 긴바지 등", "X", "아무거나"));
        temperatureMap.put("가을과 여름 사이", new OutfitPostDto(null, "가을과 여름 사이", "맨투맨, 후디에 반바지 or 셔츠가 가장 잘 어울리는 온도", Style.캐주얼, "맨투맨, 후드, 셔츠", "반바지, 긴바지 등", "가디건", "아무거나"));
        temperatureMap.put("가을", new OutfitPostDto(null, "가을", "아주 짧은 가을옷 아우터의 황금기이자 가장 멋내기 좋은 온도", Style.캐주얼, "맨투맨, 후드, 가디건, 니트", "긴바지 모두(데님, 슬랙스)", "바람막이, 블레이저, 블루종 등", "아무거나"));
        temperatureMap.put("늦가을, 초겨울", new OutfitPostDto(null, "늦가을, 초겨울", "MA-1과 라이더자켓이 딱 어울리는 온도", Style.캐주얼, "맨투맨, 후드, 니트, 모크넥 등", "긴바지 모두(데님, 슬랙스)", "가죽자켓, 라이더 자켓, ,MA-1 등", "아무거나"));
        temperatureMap.put("겨울", new OutfitPostDto(null, "겨울", "추울땐 따듯하게..하지만 얼죽코 못참지", Style.캐주얼, "맨투맨, 후드, 니트, 모크넥 등", "긴바지 모두(데님, 슬랙스)", "겨울코트, 패딩", "아무거나"));
        // 나머지 온도 범위에 대한 의상 정보를 추가

        // 온도에 따라 옷 정보를 결정
        String outfitKey = getOutfitKey(temperature);
        OutfitPostDto outfitPostDto = temperatureMap.get(outfitKey);

        return outfitPostDto;
    }

    private String getOutfitKey(float temperature) {
        // 온도 범위를 기반으로 해당하는 키를 반환
        if (temperature > 28) {
            return "한여름";
        } else if (temperature > 23) {
            return "늦봄, 초여름";
        }else if (temperature > 20) {
            return "가을과 여름 사이";
        }else if (temperature > 13) {
            return "가을";
        }else if (temperature > 5) {
            return "늦가을, 초겨울";
        }else {
            return "겨울";
        }
    }

    public List<OutfitDto> recommendBySimilarStyle(Style style){
        List<Member> memberList = memberDataRepository.findByPreferStyle(style);

        List<Outfit> outfitList = new ArrayList<>();
        for (Member member : memberList) {
            outfitList.addAll(member.getOutfitList());
        }
        List<OutfitDto> outfitDtoList = outfitList.stream()
                .map(o -> new OutfitDto(o.getId(), o.getPhoto().getId(), o.getName(), o.getDescription(), o.getStyle(), o.getTop_id(), o.getBottom_id(), o.getOuterwear_id(), o.getShoe_id()))
                .collect(Collectors.toList());

        return outfitDtoList;
    }

    @Transactional
    public int Like(Long outfitId, Long memberId) {
        // 코디를 찾아서 좋아요 수 증가 처리
        Outfit outfit = outfitDataRepository.findById(outfitId).orElse(null);
        if (outfit != null && !outfit.getLikeUser().contains(memberId)) {
            outfit.addLike();
            outfit.addUser(memberId);
            return outfit.getLikes();
        }
        return outfit.getLikes();
    }
    @Transactional
    public int unLike(Long outfitId, Long memberId) {
        // 코디를 찾아서 좋아요 수 증가 처리
        Outfit outfit = outfitDataRepository.findById(outfitId).orElse(null);
        if (outfit != null && outfit.getLikeUser().contains(memberId)) {
            outfit.unLike();
            outfit.deleteUser(memberId);
            return outfit.getLikes();
        }
        return outfit.getLikes();
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
