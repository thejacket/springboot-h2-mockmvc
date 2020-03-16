package com.thejacket.springbootH2Mockmvc.model;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/* Use JPA Criteria API to build flexible search query abstraction */

public class ProductSpecificationsBuilder {

    private final List<SearchCriteria> params;

    public ProductSpecificationsBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public ProductSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Product> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = new ArrayList<>();
        for (SearchCriteria param : params) {   // get all params from REST call
            ProductSpecification productSpecification = new ProductSpecification(param);
            specs.add(productSpecification);
        }

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i)
                    .isOrPredicate()
                    ? Specification.where(result)
                    .or(specs.get(i))
                    : Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }
}