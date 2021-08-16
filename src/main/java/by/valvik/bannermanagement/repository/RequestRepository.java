package by.valvik.bannermanagement.repository;

import by.valvik.bannermanagement.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    @Query(value = """
                 SELECT r.banner_id
                 FROM request r
                    WHERE r.user_agent = :userAgent
                      AND r.ip_address = :ipAddress
                      AND date(r.date) = current_date
            """, nativeQuery = true)
    List<Integer> findBannerIdsByUserAgentAndIpAddress(@Param("userAgent") String userAgent,
                                                       @Param("ipAddress") String ipAddress);

}
