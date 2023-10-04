package toyproject.stylecast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.stylecast.domain.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
