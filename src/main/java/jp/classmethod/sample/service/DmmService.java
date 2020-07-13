package jp.classmethod.sample.service;

import jp.classmethod.sample.repository.DmmItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class DmmService {

    private final DmmItemRepository repository;

    public static final int DEFAULT_ITEM_SIZE = 5;

    /**
     * 指定した数だけ先頭から引っ張ってくる
     * @param hit 引っ張ってくる数
     * @return {@link List} of {@link DmmItem}
     */
    public List<DmmItem> getItems(Integer hit) {

        var hitCount = Optional.ofNullable(hit).orElse(DEFAULT_ITEM_SIZE);
        return repository.fetchDmmItems(hitCount);
    }

    /**
     * 取得したアイテムリストからランダムで一つだけ取得する
     * @return {@link Optional} of {@link DmmItem}
     */
    public Optional<DmmItem> getRandomItem() {
        var random = new Random(System.currentTimeMillis()).nextInt();
        var items = repository.fetchAllItems();

        if (items.size() > 0) {
            var index = Math.abs(random) % items.size();
            return Optional.of(items.get(index));
        } else {
            return Optional.empty();
        }
    }
}
