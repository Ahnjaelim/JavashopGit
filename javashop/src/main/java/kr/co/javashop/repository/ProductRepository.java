package kr.co.javashop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.co.javashop.domain.Product;
import kr.co.javashop.repository.search.ProductSearch;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch {

    @Query(value = "select now()", nativeQuery = true)
    String getTime();
    
    @EntityGraph(attributePaths = {"imageSet"})
    @Query("select p from Product p where p.prodId =:prodId")
    Optional<Product> findByIdWithImages(@Param("prodId") Long prodId);
}
