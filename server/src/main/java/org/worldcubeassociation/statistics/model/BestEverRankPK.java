package org.worldcubeassociation.statistics.model;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
public class BestEverRankPK implements Serializable {

    @Column(name = "person_id")
    private String personId;

    @Column(name = "country_id")
    private String countryId;

    private String continent;
}
