package dev.jlcorradi.eventdrivenapplication.web;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUtils {

    public static void addCustomHeader(String headerName, Object value) {
        getHttpServletResponse()
                .setHeader(headerName, value.toString());
    }

    public static void addMessageHeader(MessageType messageType, String message) {
        getHttpServletResponse()
                .setHeader(messageType.getHeader(), message);
    }


    private static HttpServletResponse getHttpServletResponse() {
        HttpServletResponse response =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                        .getResponse();
        assert response != null;
        return response;
    }
}