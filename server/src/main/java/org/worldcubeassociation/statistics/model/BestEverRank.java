package org.worldcubeassociation.statistics.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.Type;
import org.worldcubeassociation.statistics.dto.besteverrank.EventRankDTO;

@Data
@Entity
@FieldNameConstants(asEnum = true)
public class BestEverRank implements Comparable<BestEverRank> {

    @Id
    @Column(name = "person_id")
    private String personId;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @Type(JsonType.class)
    @Column(columnDefinition = "json", name = "best_ever_rank")
    private List<EventRankDTO> eventRanks;

    @Override
    public int compareTo(BestEverRank o) {
        return personId.compareTo(o.getPersonId());
    }
}
