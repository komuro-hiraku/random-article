package jp.classmethod.sample.service;

import jp.classmethod.sample.repository.DmmItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DmmService {

    private final DmmItemRepository repository;

    public static final int DEFAULT_ITEM_SIZE = 5;

    public List<DmmItem> getDmmContents(Integer hit) {

        var hitCount = Optional.ofNullable(hit).orElse(DEFAULT_ITEM_SIZE);
        return repository.fetchDmmItem(hitCount);
    }
}
