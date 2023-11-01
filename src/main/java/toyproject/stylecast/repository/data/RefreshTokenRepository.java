package toyproject.stylecast.repository.data;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.stylecast.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    boolean existsByKeyEmail(String userEmail);
    void deleteByKeyEmail(String userEmail);
}
