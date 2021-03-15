package org.worldcubeassociation.statistics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AvatarDTO {
    private String url;

    @JsonProperty("thumb_url")
    private String thumbUrl;
}
