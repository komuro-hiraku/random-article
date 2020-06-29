package jp.classmethod.sample.controller;

import jp.classmethod.sample.service.DmmItem;
import jp.classmethod.sample.service.DmmService;
import jp.classmethod.sample.service.TotalResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final DmmService dmmService;

    @GetMapping("/dmm/random")
    public ResponseEntity<List<DmmItem>> getArticle(@RequestParam(value = "hits", required = false) Integer hits) {
        try {
            var response = Optional.ofNullable(dmmService.getDmmContents(hits)).orElseThrow();
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } catch (NoSuchElementException e) {
            log.info("No Response.", e);
            return ResponseEntity.notFound().build();
        }
    }
}
