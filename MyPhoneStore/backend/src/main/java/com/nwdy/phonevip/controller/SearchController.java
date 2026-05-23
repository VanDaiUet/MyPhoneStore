package com.nwdy.phonevip.controller;

import com.nwdy.phonevip.dto.response.ApiResponse;
import com.nwdy.phonevip.dto.response.ProductSearchResponse;
import com.nwdy.phonevip.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProductSearchResponse>>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sort
    ){
        return ResponseEntity.ok(ApiResponse.success(
                "Products found",
                searchService.search(keyword, page, size, sort)
        ));
    }
}
