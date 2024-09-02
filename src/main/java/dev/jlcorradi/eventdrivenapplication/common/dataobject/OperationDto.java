package dev.jlcorradi.eventdrivenapplication.common.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationDto {
    private UUID id;
    private Date operationDate;
    private String securitySymbol;
    private BigDecimal price;
    private int quantity;
    private Date executionDate;
}
