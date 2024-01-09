
package ma.fstt.dto;

public class LogisticDTO {
    private Long logisticId;

    private String type;

    private String status;

    public LogisticDTO() {
    }

    public LogisticDTO(Long logisticId, String type, String status) {
        this.logisticId = logisticId;
        this.type = type;
        this.status = status;
    }

    public Long getLogisticId() {
        return logisticId;
    }

    public void setLogisticId(Long logisticId) {
        this.logisticId = logisticId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
