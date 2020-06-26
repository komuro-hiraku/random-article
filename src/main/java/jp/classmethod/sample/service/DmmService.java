package jp.classmethod.sample.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DmmService {

    private final RestOperations restOperations;

    private final ObjectMapper mapper;

    @Value("${dmm.api-id}")
    private String apiId;

    @Value("${dmm.affiliate-id}")
    private String affiliateId;

    private static final int DEFAULT_ITEM_HIT = 5;

    private static final String DMM_API =
            "https://api.dmm.com/affiliate/v3/ItemList?api_id=%s&affiliate_id=%s&site=FANZA&service=digital&floor=videoa&hits=%s&sort=date&output=json";

    public TotalResponse getDmmContents(Integer hit) {
        try {
            var pageSize = Optional.ofNullable(hit).orElse(DEFAULT_ITEM_HIT);
            var uri = String.format(DMM_API, apiId, affiliateId, pageSize);
            var response = restOperations.getForEntity(URI.create(uri), String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                Assert.notNull(response.getBody(), "Response must not null");
                return mapper.readValue(response.getBody(), TotalResponse.class);
            } else {
                log.warn("status code: {}", response.getStatusCode());
                return null;
            }
        } catch (RestClientException | JsonProcessingException | AssertionError ex) {
            log.error("Cannot Access DMM API", ex);
            return null;
        }
    }
}
