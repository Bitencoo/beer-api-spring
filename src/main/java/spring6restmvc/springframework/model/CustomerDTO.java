package spring6restmvc.springframework.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class CustomerDTO {
    private UUID uuid;
    private String customerName;
    private Integer version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
