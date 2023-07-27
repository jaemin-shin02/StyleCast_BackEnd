package toyproject.stylecast.weather;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import toyproject.stylecast.domain.WeatherData;
import toyproject.stylecast.service.GeocodingService;
import java.net.URLEncoder;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WeatherService {

    private final GeocodingService geocodingService;
    private final String apiKey = "23fe151cdb3b30d73c1d46a274a04037";
    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    public void getWeatherData(String lat, String lon) {
        StringBuilder urlBuilder = new StringBuilder(BASE_URL);
        try {
            urlBuilder.append("?" + URLEncoder.encode("lat", "UTF-8") + "=" + lat);
            urlBuilder.append("&" + URLEncoder.encode("lon", "UTF-8") + "=" + lon);
            urlBuilder.append("&" + URLEncoder.encode("appid", "UTF-8") + "=" + apiKey);
            urlBuilder.append("&" + URLEncoder.encode("lang", "UTF-8") + "=kr");
            urlBuilder.append("&" + URLEncoder.encode("units", "UTF-8") + "=metric");

            System.out.println("urlBuilder = " + urlBuilder);

            RestTemplate restTemplate = new RestTemplate();
            WeatherData response = restTemplate.getForObject(urlBuilder.toString(), WeatherData.class);

            System.out.println("urlBuilder = " + urlBuilder);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
