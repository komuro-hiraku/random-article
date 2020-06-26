package jp.classmethod.sample.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Item詳細のResponse
 */
@Data
public class DmmItem {
    private String title;
    private String url;
    private String serviceName;

    @JsonProperty("affiliateURL")
    private String affiliateUrl;
    @JsonProperty("imageURL")
    private ImageUrl imageUrl;
}
