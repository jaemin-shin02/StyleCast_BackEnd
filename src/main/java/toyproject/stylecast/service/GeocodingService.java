package toyproject.stylecast.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import toyproject.stylecast.domain.geocode.Location;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GeocodingService {

    public String getCoordinates(String address) {
        System.out.println("하윙");
        try {
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey("AIzaSyCtS4mulaD3WtF93diBvSQvnWq4NPYEaYA") // Google Cloud Platform에서 생성한 API 키를 입력하세요.
                    .build();

            GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
            if (results.length > 0) {
                double latitude = results[0].geometry.location.lat;
                double longitude = results[0].geometry.location.lng;
                return latitude + "," + longitude;
            } else {
                return "No results found";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }

    public Location getLocation(String location){
        String[] split = StringUtils.split(location, ",");
        Location result = Location.creatLocation(split[0], split[1]);

        return result;
    }

}

