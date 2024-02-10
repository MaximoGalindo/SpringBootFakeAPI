package SpringBootFakeAPI.repositories;

import SpringBootFakeAPI.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity,Long> {
}
