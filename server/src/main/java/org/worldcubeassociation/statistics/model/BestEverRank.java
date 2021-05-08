package org.worldcubeassociation.statistics.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.worldcubeassociation.statistics.dto.besteverrank.RankDTO;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@Entity(name = "best_ever_ranks")
@EqualsAndHashCode(callSuper = true)
public class BestEverRank extends BaseEntity {
    @EmbeddedId
    private BestEverRankPK bestEverRankPK;

    @Column(name = "person_id", insertable = false, updatable = false)
    private String personId;

    @Column(name = "country_id", insertable = false, updatable = false)
    private String countryId;

    @Column(insertable = false, updatable = false)
    private String continent;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "best_ever_rank")
    private List<RankDTO> ranksDTO;
}
