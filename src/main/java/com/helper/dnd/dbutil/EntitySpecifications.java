package com.helper.dnd.dbutil;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.domain.Specification;

public class EntitySpecifications {

  public static <T> Specification<T> buildSpecification(
      Class<T> entityClass, Map<String, String> queryParams) {
    return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      queryParams.entrySet().stream()
          .forEach(
              entry -> {
                if (entry.getValue().contains(",")) {
                  String[] splitString = entry.getValue().split(",");
                  List<Predicate> orPredicates = new ArrayList<>();
                  for (String value : splitString) {
                    orPredicates.add(criteriaBuilder.equal(root.get(entry.getKey()), value));
                  }
                  predicates.add(
                      criteriaBuilder.or(orPredicates.toArray(new Predicate[orPredicates.size()])));
                } else {
                  predicates.add(criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue()));
                }
              });

      return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    };
  }
}
