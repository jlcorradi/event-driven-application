package dev.jlcorradi.eventdrivenapplication.web;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EndPointConstants {
    public static final String V1 = "/api/v1";
    public static final String OPERATIONS_API = V1 + "/operations";
    public static final String SECURITIES_API = V1 + "/securities";
}
