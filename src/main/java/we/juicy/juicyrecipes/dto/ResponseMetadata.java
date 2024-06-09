package we.juicy.juicyrecipes.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ResponseMetadata {
    private String requestId;
    private int httpStatusCode;
    private Map<String, String> httpHeaders;
    private int retryAttempts;

}
