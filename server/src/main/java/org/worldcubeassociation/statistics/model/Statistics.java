package org.worldcubeassociation.statistics.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.worldcubeassociation.statistics.dto.StatisticsGroupResponseDTO;
import org.worldcubeassociation.statistics.enums.DisplayModeEnum;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Statistics extends BaseEntity {
    @Id
    private String path;

    private String title;

    private String explanation;

    @Enumerated(EnumType.STRING)
    @Column(name = "display_mode")
    private DisplayModeEnum displayMode;

    @Column(name = "group_name")
    private String groupName;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<StatisticsGroupResponseDTO> statistics;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;
}
