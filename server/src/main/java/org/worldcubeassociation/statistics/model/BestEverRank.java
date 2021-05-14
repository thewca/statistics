package org.worldcubeassociation.statistics.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.worldcubeassociation.statistics.dto.besteverrank.EventRankDTO;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "best_ever_ranks")
@EqualsAndHashCode(callSuper = true)
public class BestEverRank extends BaseEntity {
    @Id
    @Column(name = "person_id")
    private String personId;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "best_ever_rank")
    private List<EventRankDTO> eventRanks;
}
