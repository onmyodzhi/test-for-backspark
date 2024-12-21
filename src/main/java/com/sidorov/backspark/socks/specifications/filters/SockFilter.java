package com.sidorov.backspark.socks.specifications.filters;

import com.sidorov.backspark.socks.models.Sock;
import com.sidorov.backspark.socks.specifications.SockSpecifications;
import liquibase.pro.packaged.S;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SockFilter {

    Specification<Sock> spec;
    final String filterParams;

    public SockFilter(Map<String, String> params) {
        spec = Specification.where(null);
        StringBuilder paramsBuilder = new StringBuilder();

        String colorParam = params.get("color");
        if (colorParam != null && !colorParam.isEmpty()) {
            spec = spec.and(SockSpecifications.colorEquals(colorParam));
            paramsBuilder.append("&color=").append(colorParam);
        }

        String minCottonParam = params.get("minCottonPercentage");
        if (minCottonParam != null && !minCottonParam.isEmpty()) {
            short minCotton = Short.parseShort(minCottonParam);
            spec = spec.and(SockSpecifications.cottonPercentageGreaterOrEqualsThan(minCotton));
            paramsBuilder.append("&minCottonPercentage=").append(minCotton);
        }

        String maxCottonParam = params.get("maxCottonPercentage");
        if (maxCottonParam != null && !maxCottonParam.isEmpty()) {
            short maxCotton = Short.parseShort(maxCottonParam);
            spec = spec.and(SockSpecifications.cottonPercentageLessOrEqualsThan(maxCotton));
            paramsBuilder.append("&maxCottonPercentage=").append(maxCotton);
        }

        filterParams = paramsBuilder.toString();
    }
}
