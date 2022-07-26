package shopping.app.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class OrderResponseDto {
    private Long id;
    private List<Long> productOrdersIds;
    private Long userId;
    private LocalDateTime creationTime;
    private boolean isPaid;
}
