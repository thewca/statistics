package org.worldcubeassociation.statistics.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.worldcubeassociation.statistics.dto.StatisticsGroupResponseDTO;
import org.worldcubeassociation.statistics.enums.DisplayModeEnum;

@Data
@Entity
public class Statistics {

    @Id
    private String path;

    private String title;

    private String explanation;

    @Enumerated(EnumType.STRING)
    @Column(name = "display_mode")
    private DisplayModeEnum displayMode;

    @Column(name = "group_name")
    private String groupName;

    @Type(JsonType.class)
    private List<StatisticsGroupResponseDTO> statistics;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @Column(name = "export_date")
    private LocalDateTime exportDate;
}
