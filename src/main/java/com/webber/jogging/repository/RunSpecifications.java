package com.webber.jogging.repository;

import com.webber.jogging.domain.Run;
import com.webber.jogging.domain.RunFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class RunSpecifications {

    public static Specification<Run> forRunFilter(RunFilter filter) {
        return new Specification<Run>() {
            @Override
            public Predicate toPredicate(Root<Run> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
