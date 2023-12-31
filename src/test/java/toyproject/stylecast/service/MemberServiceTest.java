package toyproject.stylecast.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.*;
import toyproject.stylecast.domain.geocode.Location;
import toyproject.stylecast.domain.recommendframe.Weather;
import toyproject.stylecast.dto.WeatherDto;
import toyproject.stylecast.repository.MemberRepository;
import toyproject.stylecast.weather.WeatherService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    private MemberDataService memberDataService;
}
