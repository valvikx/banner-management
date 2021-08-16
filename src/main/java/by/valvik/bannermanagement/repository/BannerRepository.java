package by.valvik.bannermanagement.repository;

import by.valvik.bannermanagement.domain.Banner;
import by.valvik.bannermanagement.domain.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BannerRepository extends ExtendedJpaRepository<Banner> {

    @Query("""
            FROM Banner srb
                WHERE srb.category.reqName = :reqName
                    AND (:#{#bannerIds.size() == 0} = true OR (srb.id NOT IN :bannerIds))        
                    AND srb.price = (SELECT MAX(frb.price)
                                     FROM Banner frb
                                        WHERE frb.category.reqName = :reqName
                                            AND (:#{#bannerIds.size() == 0} = true OR (frb.id NOT IN :bannerIds)))    
            """)
    List<Banner> findAllWithMaxPrice(@Param("reqName") String reqName,
                                     @Param("bannerIds") List<Integer> bannerIds);

    @Query("""
            FROM Banner b
                WHERE b.category = :category
                    AND b.isDeleted = false             
            """)
    List<Banner> findAllByCategoryAndIsDeletedFalse(@Param("category") Category category);

}
