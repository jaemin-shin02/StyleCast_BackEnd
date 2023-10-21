package toyproject.stylecast.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.geocode.Location;
import toyproject.stylecast.domain.recommendframe.Weather;
import toyproject.stylecast.dto.WeatherDto;
import toyproject.stylecast.weather.WeatherService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class WeatherServiceTest {
    @Autowired
    private GeocodingService geocodingService;
    @Autowired
    private WeatherService weatherService;

    @Test
    public void getWeather() throws Exception {
        //given
        String coordinates = geocodingService.getCoordinates("서울");
        Location location = geocodingService.getLocation(coordinates);
        String lat = location.getLat();
        String lon = location.getLon();
        System.out.println("lat = " + lat);
        System.out.println("lon = " + lon);
        WeatherDto weatherData = weatherService.getWeatherData(lat, lon);
        System.out.println("weatherData = " + weatherData);
        Weather weather = Weather.valueOf(weatherData.getMain());
        System.out.println("weather = " + weather);
        //when

        //then
    }
}
