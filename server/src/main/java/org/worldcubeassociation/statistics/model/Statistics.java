package org.worldcubeassociation.statistics.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.worldcubeassociation.statistics.dto.StatisticsGroupResponseDTO;
import org.worldcubeassociation.statistics.enums.DisplayModeEnum;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Statistics extends BaseEntity {
    @Id
    private String path;

    private String title;

    private String explanation;

    @Column(name = "display_mode")
    private DisplayModeEnum displayMode;

    private String group;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<StatisticsGroupResponseDTO> statistics;
}
