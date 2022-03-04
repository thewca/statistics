package org.worldcubeassociation.statistics.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.Type;
import org.worldcubeassociation.statistics.dto.recordevolution.EvolutionDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Data
@Entity
@FieldNameConstants(asEnum = true)
@EqualsAndHashCode(callSuper = true)
public class RecordEvolution extends BaseEntity {
    @Id
    private String region;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "evolution")
    private List<EvolutionDto> evolution;
}
