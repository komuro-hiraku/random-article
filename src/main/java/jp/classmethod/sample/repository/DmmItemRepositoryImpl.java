package jp.classmethod.sample.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.classmethod.sample.service.DmmItem;
import jp.classmethod.sample.service.DmmResponse;
import jp.classmethod.sample.service.TotalResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class DmmItemRepositoryImpl implements DmmItemRepository {

    private static final int DEFAULT_ITEM_HIT = 20;

    private static final String DMM_API =
            "https://api.dmm.com/affiliate/v3/ItemList?api_id=%s&affiliate_id=%s&site=FANZA&service=digital&floor=videoa&hits=%s&sort=date&output=json";

    @Value("${dmm.api-id}")
    private String apiId;

    @Value("${dmm.affiliate-id}")
    private String affiliateId;

    private final RestOperations restOperations;

    private final ObjectMapper mapper;

    // Redis操作用のTemplate
    private final StringRedisTemplate redisTemplate;

    @Override
    public List<DmmItem> fetchDmmItems(int hits) {

        try {
            // 全アイテムを取得
            var response = fetchAllItemFromRemote();

            // List から最大サイズ切り出して end
            return response.getItems().stream()
                    .limit(hits)
                    .collect(Collectors.toList());
        } catch (JsonProcessingException | IllegalArgumentException e) {
            log.error("Error fetch All Items", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<DmmItem> fetchAllItems() {
        try {
            return new ArrayList<>(fetchAllItemFromRemote().getItems());
        } catch (JsonProcessingException e) {
            log.error("Error fetch All Items", e);
            return Collections.emptyList();
        }
    }

    private DmmResponse fetchAllItemFromRemote() throws JsonProcessingException, IllegalArgumentException {

        // Redis から今日の日付をキーにデータを取得
        var key = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd", Locale.JAPAN));
        var ops = redisTemplate.opsForValue();

        log.info("Search Key: {}", key);
        // key を探索して存在しなければRemoteから取得してRedisに叩き込む
        if (redisTemplate.hasKey(key) == Boolean.FALSE) {
            log.info("Not exists cache. Fetch all item from Remote");
            getAllContents(null).ifPresent( json -> ops.set(key, json, Duration.ofDays(1)));
        }

        var value = ops.get(key);
        var response = mapper.readValue(value, TotalResponse.class);

        return response.getResult();
    }

    /**
     * Remote から指定された数だけコンテンツを取得する
     * @param hit 取得数
     * @return {@link Optional} Response の JSON 文字列
     */
    private Optional<String> getAllContents(Integer hit) {
        try {
            var pageSize = Optional.ofNullable(hit).orElse(DEFAULT_ITEM_HIT);
            var uri = String.format(DMM_API, apiId, affiliateId, pageSize);
            var response = restOperations.getForEntity(URI.create(uri), String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                Assert.notNull(response.getBody(), "Response must not null");
                return Optional.of(response.getBody());
            } else {
                log.warn("status code: {}", response.getStatusCode());
                return Optional.empty();
            }
        } catch (RestClientException | AssertionError ex) {
            log.error("Cannot Access DMM API", ex);
            return Optional.empty();
        }
    }
}
