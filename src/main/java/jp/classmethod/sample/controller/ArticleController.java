package jp.classmethod.sample.controller;

import jp.classmethod.sample.service.DmmService;
import jp.classmethod.sample.service.TotalResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final DmmService dmmService;

    @GetMapping("/dmm/random")
    public ResponseEntity<TotalResponse> getArticle() {
           return ResponseEntity.of(Optional.ofNullable(dmmService.getDmmContents()));
    }

}
