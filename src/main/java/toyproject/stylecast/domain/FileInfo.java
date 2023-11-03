package toyproject.stylecast.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class FileInfo {
    @Id
    @GeneratedValue
    @Column(name="file_id")
    private Long id;

    private String orgNm;

    private String savedNm;

    private String savedPath;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "photo")
    private Clothes clothes;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "photo")
    private Outfit outfit;

    @Builder
    public FileInfo(Long id, String orgNm, String savedNm, String savedPath) {
        this.id = id;
        this.orgNm = orgNm;
        this.savedNm = savedNm;
        this.savedPath = savedPath;
    }
}
