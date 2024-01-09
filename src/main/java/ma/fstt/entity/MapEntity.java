package ma.fstt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "map")
public class MapEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mapId;
    private String location;
    private String details;

    private Long logisticId;

    public MapEntity() {
    }

    public MapEntity(Long mapId, String location, String details, Long logisticId) {
        this.mapId = mapId;
        this.location = location;
        this.details = details;
        this.logisticId = logisticId;
    }

    public Long getMapId() {
        return mapId;
    }

    public void setMapId(Long mapId) {
        this.mapId = mapId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Long getLogisticId() {
        return logisticId;
    }

    public void setLogisticId(Long logisticId) {
        this.logisticId = logisticId;
    }
}
