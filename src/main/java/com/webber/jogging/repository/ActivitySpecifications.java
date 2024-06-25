package com.webber.jogging.repository;

import com.webber.jogging.domain.Activity;
import com.webber.jogging.domain.ActivityFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ActivitySpecifications {

    public static Specification<Activity> forActivityFilter(ActivityFilter filter) {
        return new Specification<Activity>() {
            @Override
            public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("user"), filter.getUser()));
                if (!StringUtils.isEmpty(filter.getCourse())) {
                    predicates.add(cb.equal(root.get("course"), filter.getCourse()));
                }
                if (null != filter.getStartDate()) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("date"), filter.getStartDate()));
                }
                if (null != filter.getEndDate()) {
                    predicates.add(cb.lessThan(root.get("date"),filter.getEndDate()));
                }

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

    }

}
