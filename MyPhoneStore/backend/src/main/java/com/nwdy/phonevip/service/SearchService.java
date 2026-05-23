package com.nwdy.phonevip.service;

import com.nwdy.phonevip.dto.response.ProductSearchResponse;
import com.nwdy.phonevip.mapper.ProductMapper;
import com.nwdy.phonevip.model.Product;
import com.nwdy.phonevip.model.ProductDocument;
import com.nwdy.phonevip.repository.ProductRepository;
import com.nwdy.phonevip.repository.ProductSearchRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {

    private final ProductRepository productRepository;

    private final ProductSearchRepository productSearchRepository;

    private final ElasticsearchOperations elasticsearchOperations;

    public List<ProductSearchResponse> search(String keyword, int page, int size, String sort) {
        Pageable pageable;
        if (sort.isEmpty() ) {
            pageable = PageRequest.of(page, size);
        } else {
            String[] sortParams = sort.split(",");
            String sortField = sortParams[0];
            String sortDirection = sortParams.length > 1 ? sortParams[1] : "asc";

            Sort.Direction direction = sortDirection
                    .equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

            pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        }

        CriteriaQuery query = new CriteriaQuery(
                new Criteria("name").contains(keyword),
                pageable
        );

        SearchHits<ProductDocument> searchHits = elasticsearchOperations.search(query, ProductDocument.class);

        List<ProductDocument> productDocuments = searchHits.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .toList();

        return productDocuments.stream()
                .map(ProductMapper.INSTANCE::toProductSearchResponse)
                .toList();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void syncProductsToElasticSearch() {
        productSearchRepository.deleteAll();
        System.out.println("Syncing products to elastic search");
        List<Product> products = productRepository.findAll();
        List<ProductDocument> productDocuments = products.stream()
                .map(ProductMapper.INSTANCE::toProductDocument)
                .toList();
        productSearchRepository.saveAll(productDocuments);
        System.out.println("Synced " + productDocuments.size() + " products to elastic search completed");
    }

    public void saveProductToElasticSearch(Product product) {
        ProductDocument productDocument = ProductMapper.INSTANCE.toProductDocument(product);
        productSearchRepository.save(productDocument);
    }

    public void deleteProductFromElasticSearch(Long productId) {
        productSearchRepository.deleteById(productId);
    }

}
