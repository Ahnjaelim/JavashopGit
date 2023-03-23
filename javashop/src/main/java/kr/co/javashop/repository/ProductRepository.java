package kr.co.javashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.co.javashop.domain.Product;
import kr.co.javashop.repository.search.ProductSearch;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch {

    @Query(value = "select now()", nativeQuery = true)
    String getTime();
}
