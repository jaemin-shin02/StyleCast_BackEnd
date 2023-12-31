package toyproject.stylecast.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.RefreshToken;
import toyproject.stylecast.domain.Token;
import toyproject.stylecast.repository.data.RefreshTokenRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    @Transactional
    public void login(Token tokenDto){

        RefreshToken refreshToken = RefreshToken.builder().keyEmail(tokenDto.getKey()).refreshToken(tokenDto.getRefreshToken()).build();
        String loginUserEmail = refreshToken.getKeyEmail();
        if(refreshTokenRepository.existsByKeyEmail(loginUserEmail)){
            log.info("기존의 존재하는 refresh 토큰 삭제");
            refreshTokenRepository.deleteByKeyEmail(loginUserEmail);
        }
        refreshTokenRepository.save(refreshToken);

    }

    @Transactional
    public void logout(String key){
        if(refreshTokenRepository.existsByKeyEmail(key)){
            log.info("기존의 존재하는 refresh 토큰 삭제");
            refreshTokenRepository.deleteByKeyEmail(key);
        }
    }

    public Optional<RefreshToken> getRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

    public Map<String, String> validateRefreshToken(String refreshToken){
        RefreshToken refreshToken1 = getRefreshToken(refreshToken).get();
        String createdAccessToken = jwtTokenProvider.validateRefreshToken(refreshToken1);

        return createRefreshJson(createdAccessToken);
    }

    public Map<String, String> createRefreshJson(String createdAccessToken){
        Map<String, String> map = new HashMap<>();
        if(createdAccessToken == null){
            map.put("errorType", "Forbidden");
            map.put("status", "402");
            map.put("message", "Refresh 토큰이 만료되었습니다. 로그인이 필요합니다.");

            return map;
        }

        //기존에 존재하는 accessToken 제거
        map.put("status", "200");
        map.put("message", "Refresh 토큰을 통한 Access Token 생성이 완료되었습니다.");
        map.put("accessToken", createdAccessToken);

        return map;
    }
}
