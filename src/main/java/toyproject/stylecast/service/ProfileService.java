package toyproject.stylecast.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.Profile;
import toyproject.stylecast.repository.data.MemberDataRepository;
import toyproject.stylecast.repository.data.ProfileRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {
    private final MemberDataRepository memberDataRepository;
    private final ProfileRepository profileRepository;

    public Long save(Profile profile){
        profileRepository.save(profile);
        return profile.getId();
    }

    public Optional<Profile> findById(Long id){
        return profileRepository.findById(id);
    }

}
