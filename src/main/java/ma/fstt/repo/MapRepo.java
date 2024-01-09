package ma.fstt.repo;

import ma.fstt.entity.MapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapRepo extends JpaRepository<MapEntity,Long> {

    @Query("SELECT a FROM MapEntity a WHERE a.logisticId = :logisticId")
    List<MapEntity> findByLogisticId(@Param("logisticId") Long logisticId);

}
