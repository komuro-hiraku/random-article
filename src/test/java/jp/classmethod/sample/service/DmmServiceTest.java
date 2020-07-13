package jp.classmethod.sample.service;

import jp.classmethod.sample.repository.DmmItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DmmServiceTest {

    @Mock
    DmmItemRepository repository;

    @InjectMocks
    DmmService sut;

    @Test
    void getItems() {
        when(repository.fetchDmmItems(eq(10)))
                .thenReturn(createList());

        var result = sut.getItems(10);
        assertNotNull(result);
        assertEquals(2, result.size());

        assertAll(() -> {
            for (var item : result) {
                assertNotNull(item);
                assertTrue(item.getTitle().startsWith("title"));
                assertTrue(item.getUrl().startsWith("url"));

                assertNotNull(item.getImageUrl());
            }
        });
    }

    @Test
    void getRandomItem() {
        when(repository.fetchAllItems())
                .thenReturn(createList());

        var result = sut.getRandomItem();
        assertFalse(result.isEmpty());
        assertTrue(result.get().getTitle().startsWith("title"));
    }

    private static List<DmmItem> createList() {
        return List.of(new DmmItem("title1", "url1", "serviceName1", "affiliateUrl1", new ImageUrl("", "", ""))
                , new DmmItem("title2", "url2", "serviceName2", "affiliateUrl2", new ImageUrl("", "", "")));
    }
}