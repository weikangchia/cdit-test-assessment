package com.cdit.challenge.specification;

import com.cdit.challenge.model.UrlShortener;
import org.springframework.data.jpa.domain.Specification;

public class UrlSpecification {
    public static Specification<UrlShortener> getLongUrl(String shortUrl) {
        return (root, query, cb) -> {

            return cb.equal(root.get("shortUrl"), shortUrl);
        };
    }

    public static Specification<UrlShortener> getShortUrl(String longUrl) {
        return (root, query, cb) -> {

            return cb.equal(root.get("longUrl"), longUrl);
        };
    }
}
