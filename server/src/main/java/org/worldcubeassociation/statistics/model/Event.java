package org.worldcubeassociation.statistics.model;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Data
@Entity
@FieldNameConstants(asEnum = true)
public class Event {
    @Id
    private String id;
    private String name;
    private Integer rank;
}
