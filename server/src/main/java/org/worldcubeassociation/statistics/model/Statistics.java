package org.worldcubeassociation.statistics.model;

import lombok.Data;
import org.worldcubeassociation.statistics.enums.DisplayModeEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Statistics {
    @Id
    private String path;

    private String title;

    private String explanation;

    @Column(name = "display_mode")
    private DisplayModeEnum displayMode;

    private String group;
}
