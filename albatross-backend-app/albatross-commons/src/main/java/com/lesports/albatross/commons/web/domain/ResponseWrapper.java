package com.lesports.albatross.commons.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseWrapper<T> implements Serializable {

    private static final long serialVersionUID = -1696727488987157682L;

    @ApiModelProperty(value = "Requested Resource Path")
    @JsonProperty("path")
    private String path;

    @ApiModelProperty(value = "Business Response Code")
    @JsonProperty("code")
    private int code;

    @ApiModelProperty(value = "Additional Messages for Current Response")
    @JsonProperty("messages")
    private List<String> messages = new ArrayList<>();

    @ApiModelProperty(value = "Requested Resource Data")
    @JsonProperty("data")
    private T data;

    public ResponseWrapper(int code, String message) {
        this.code = code;
        if (message != null) this.messages.add(message);
    }

    public ResponseWrapper(int code, String message, T data) {
        this.code = code;
        if (message != null) this.messages.add(message);
        this.data = data;
    }

    public static <T> ResponseWrapperBuilder<T> builder() {
        ResponseWrapperBuilder<T> builder = new ResponseWrapperBuilder<>();
        return builder;
    }

    public static <T> ResponseWrapperBuilder<T> request(HttpServletRequest request) {
        ResponseWrapperBuilder<T> builder = new ResponseWrapperBuilder<>();
        builder.request(request);
        return builder;
    }

    public static <T> ResponseWrapperBuilder<T> ok() {
        ResponseWrapperBuilder<T> builder = new ResponseWrapperBuilder<>();
        builder.code(1).data(null).addMessage("success");
        return builder;
    }

    public static <T> ResponseWrapperBuilder<T> ok(T data) {
        ResponseWrapperBuilder<T> builder = new ResponseWrapperBuilder<>();
        builder.code(1).data(data).addMessage("success");
        return builder;
    }

    public static <T> ResponseWrapperBuilder<T> failed(T data) {
        ResponseWrapperBuilder<T> builder = new ResponseWrapperBuilder<>();
        builder.code(-1).data(data);
        return builder;
    }

    public static <T> ResponseWrapperBuilder<T> exception(T data) {
        ResponseWrapperBuilder<T> builder = new ResponseWrapperBuilder<>();
        builder.code(-1).data(data);
        return builder;
    }

    public static <T> ResponseWrapperBuilder<T> exception() {
        ResponseWrapperBuilder<T> builder = new ResponseWrapperBuilder<>();
        builder.code(-1).data(null);
        return builder;
    }

    public static <T> ResponseWrapperBuilder<T> refused(String realm) {
        ResponseWrapperBuilder<T> builder = new ResponseWrapperBuilder<>();
        builder.code(-3).data(null).addMessage(realm + " refused accessing");
        return builder;
    }

    public static <T> ResponseWrapperBuilder<T> unauthorized(String realm) {
        ResponseWrapperBuilder<T> builder = new ResponseWrapperBuilder<>();
        builder.code(-4).data(null).addMessage(realm + " requires authenticating first");
        return builder;
    }

    public static <T> ResponseWrapperBuilder<T> validationErrors(Errors errors) {
        ResponseWrapperBuilder<T> builder = new ResponseWrapperBuilder<>();
        List<String> errMessages = new ArrayList<>();
        errors.getAllErrors().forEach(err -> errMessages.add(err.getDefaultMessage()));
        builder.code(-1).data(null).addMessages(errMessages);
        return builder;
    }

    public static class ResponseWrapperBuilder<T> {
        private int code;
        private List<String> messages = new ArrayList<>();
        private T data;
        private String path;

        public ResponseWrapperBuilder<T> request(HttpServletRequest request) {
            this.path = request.getRequestURI();
            return this;
        }

        public ResponseWrapperBuilder<T> code(int code) {
            this.code = code;
            return this;
        }

        public ResponseWrapperBuilder<T> addMessage(String message) {
            if (null != message)
                this.messages.add(message);
            return this;
        }

        public ResponseWrapperBuilder<T> addMessages(List<String> messages) {
            if (null != messages && !messages.isEmpty()) {
                this.messages.addAll(messages);
            }
            return this;
        }

        public ResponseWrapperBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ResponseWrapper<T> build() {
            return new ResponseWrapper<>(this.path, this.code, this.messages, this.data);
        }
    }
}
