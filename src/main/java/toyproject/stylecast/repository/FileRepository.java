package toyproject.stylecast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.stylecast.domain.FileInfo;

public interface FileRepository extends JpaRepository<FileInfo, Long> {
}
