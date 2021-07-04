package org.worldcubeassociation.statistics.model;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@FieldNameConstants(asEnum = true)
public class Event {
    @Id
    private String id;
    private String name;
    private Integer rank;
}
