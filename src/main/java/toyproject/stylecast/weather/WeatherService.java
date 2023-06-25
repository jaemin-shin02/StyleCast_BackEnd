package toyproject.stylecast.weather;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class WeatherService {

    private final String apiKey = "23fe151cdb3b30d73c1d46a274a04037";
    private final String apiUrl = "http://api.openweathermap.org/data/2.5/weather";
    private final String lang = "kr";


    public WeatherResponse getWeatherByCity(String city) {
        String encodedCityName = encodeCityName(city);
        String url = apiUrl + "?q=" + encodedCityName + "&appid=" + apiKey + "&lang" + lang + "&units=metric";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WeatherResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                WeatherResponse.class
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            // API 요청이 실패한 경우 예외 처리 등을 수행
            throw new RuntimeException("Failed to get weather data from API");
        }
    }

    private String encodeCityName(String cityName) {
        try {
            return URLEncoder.encode(cityName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void setRestTemplate(RestTemplate restTemplateMock) {
    }
}
