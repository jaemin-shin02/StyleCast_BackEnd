package toyproject.stylecast.domain.geocode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {
    private String lat; //위도
    private String lon; //경도

    public static Location creatLocation(String lat, String lon){
        Location location = new Location();
        location.setLat(lat);
        location.setLon(lon);

        return location;
    }
}
