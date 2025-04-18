package org.worldcubeassociation.statistics.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.List;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.Type;
import org.worldcubeassociation.statistics.dto.recordevolution.EvolutionDto;

@Data
@Entity
@FieldNameConstants(asEnum = true)
public class RecordEvolution {

    @Id
    @Column(name = "event_id")
    private String eventId;

    @Type(JsonType.class)
    @Column(columnDefinition = "json", name = "evolution")
    private List<EvolutionDto> evolution;
}
