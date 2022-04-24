package org.worldcubeassociation.statistics.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.worldcubeassociation.statistics.dto.StatisticsGroupResponseDTO;
import org.worldcubeassociation.statistics.enums.DisplayModeEnum;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name = "export_date")
    private LocalDateTime exportDate;
}
