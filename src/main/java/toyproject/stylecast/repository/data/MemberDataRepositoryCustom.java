package toyproject.stylecast.repository.data;

import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Style;

import java.util.List;

public interface MemberDataRepositoryCustom {
    List<Member> findByPreferStyle(Style style);
}
