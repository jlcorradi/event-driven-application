package dev.jlcorradi.eventdrivenapplication.common.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityDto {
    private String symbol;
    private String description;
    private String sectorId;
    private String sectorDescription;
}
