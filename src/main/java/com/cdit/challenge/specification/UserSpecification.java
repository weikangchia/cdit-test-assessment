package com.cdit.challenge.specification;

import com.cdit.challenge.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class UserSpecification {

    public static Specification<User> hasValidSalary() {
        return (root, query, cb) -> {
            Predicate greaterThanOrEqualTo0 = cb.greaterThanOrEqualTo(root.get("salary"), 0);

            // salary is stored in cents
            Predicate lessThanOrEqualTo4000 = cb.lessThanOrEqualTo(root.get("salary"), 400000);

            return cb.and(greaterThanOrEqualTo0, lessThanOrEqualTo4000);
        };
    }
}
