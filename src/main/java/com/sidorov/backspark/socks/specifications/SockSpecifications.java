package com.sidorov.backspark.socks.specifications;

import com.sidorov.backspark.socks.models.Sock;
import org.springframework.data.jpa.domain.Specification;

public class SockSpecifications {

    public static Specification<Sock> colorEquals(String color) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("color"), color);
    }

    public static Specification<Sock> cottonPercentageGreaterOrEqualsThan(short minCotton) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("cottonPercentage"), minCotton);
    }

    public static Specification<Sock> cottonPercentageLessOrEqualsThan(short maxCotton) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("cottonPercentage"), maxCotton);
    }
}
