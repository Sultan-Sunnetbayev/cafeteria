package tm.salam.cafeteria3.service.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.models.Product;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductSpecification {

    public Specification<Product> ProductFilterByParameter(String parameter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (parameter != null && !parameter.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + parameter.toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

//    public Specification<Product> ProductFilterByCode(String parameter) {
//        return (root, query, criteriaBuilder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            if (parameter != null && !parameter.isEmpty()) {
//                predicates.add(criteriaBuilder.like(root.get("code"),
//                        "%" + parameter + "%"));
//            }
//
//            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//        };
//    }

    public Specification<Product> ProductFilterByAmount(String parameter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (parameter != null && !parameter.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("amount"), Integer.valueOf(parameter)));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<Product> ProductFilterByPrice(String parameter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (parameter != null && !parameter.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("sellPrice"), Double.valueOf(parameter)));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


}