package toyproject.stylecast.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.geocode.Location;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class GeoCodeServiceTest {
    @Autowired
    private GeocodingService geocodingService;

    @Test
    public void getGeoCode() throws Exception {
        //given
        String str = geocodingService.getCoordinates("충북 제천");
        //when
        Location location = geocodingService.getLocation(str);
        //then
        System.out.println("location.getLat() = " + location.getLat());
        System.out.println("location.getLot() = " + location.getLon());
    }
}
