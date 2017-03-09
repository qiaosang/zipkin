package com.lesports.albatross.commons.util;

import com.lesports.albatross.commons.web.domain.ResponseWrapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by litzuhsien on 4/28/16.
 */
public final class ApiResponseEntityUtils {

    private ApiResponseEntityUtils() {
    }

    /**
     * 成功
     *
     * @return
     */
    public static <T extends Serializable> ResponseEntity<T> ok() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> ok(T model) {
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    /**
     * 服务器找不到请求的资源
     *
     * @return
     */
    public static <T> ResponseEntity<ResponseWrapper<T>> notFound(T model) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseWrapper.failed(model).addMessage("Requested resource not found").code(-1).build());
    }

    public static <T> ResponseEntity<ResponseWrapper<T>> notFound() {
        return notFound(null);
    }

    /**
     * 请求成功并且服务器创建了新的资源
     *
     * @param model
     * @return
     */
    public static <T> ResponseEntity<T> created(T model) {
        return new ResponseEntity<T>(model, HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<T> updated(T model) {
        return new ResponseEntity<T>(model, HttpStatus.CREATED);
    }
    // /**
    // * 请求成功 无没任何内容
    // * @return
    // */
    // public static <T> ResponseEntity<T> noContent() {
    // return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    // }
    // public static <T> ResponseEntity<T> noContent(T model) {
    // return new ResponseEntity<>(model, HttpStatus.NO_CONTENT);
    // }

    /**
     * 拒绝访问
     *
     * @param model
     * @return
     */
    public static <T> ResponseEntity<T> forbidden(T model) {
        return new ResponseEntity<>(model, HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<ResponseWrapper<?>> unauthorized(HttpServletRequest request, Throwable throwable) {
        if (throwable != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ResponseWrapper.exception().addMessage(ApiResponseEntityUtils.getRootCauseMessage(throwable))
                            .code(-1).request(request).build());
        } else {
            return null;
        }
    }

    /**
     * 自定义
     *
     * @param code
     * @return
     */
    public static <T> ResponseEntity<T> with(HttpStatus code) {
        return new ResponseEntity<>(code);
    }

    public static <T> ResponseEntity<T> with(HttpStatus code, T model) {
        return new ResponseEntity<>(model, code);
    }

    public static ResponseEntity<ResponseWrapper<?>> status(HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(ResponseWrapper.builder().code(0).build());
    }

    public static ResponseEntity<ResponseWrapper<?>> failed(HttpStatus status, ResponseWrapper<?> failureResponse) {
        return new ResponseEntity<>(failureResponse, status);
    }

    public static ResponseEntity<ResponseWrapper<?>> badRequest(HttpServletRequest request, Errors errors) {
        if (errors.hasErrors()) {
            List<String> errMessages = new ArrayList<>();
            errors.getAllErrors().forEach(err -> errMessages.add(err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(ResponseWrapper.validationErrors(errors)
                    .code(-1).request(request).build());
        } else {
            return null;
        }
    }

    public static ResponseEntity<ResponseWrapper<?>> exception(Throwable throwable, HttpStatus status,
                                                               HttpServletRequest request) {
        if (throwable != null) {
            return ResponseEntity.status(status)
                    .body(ResponseWrapper.exception().addMessage(ApiResponseEntityUtils.getRootCauseMessage(throwable))
                            .code(-1).request(request).build());
        } else {
            return null;
        }
    }

    public static ResponseEntity<ResponseWrapper<?>> exception(int code, Throwable throwable, HttpStatus status,
                                                               HttpServletRequest request) {
        if (throwable != null) {
            return ResponseEntity.status(status)
                    .body(ResponseWrapper.exception().addMessage(ApiResponseEntityUtils.getRootCauseMessage(throwable))
                            .code(code).request(request).build());
        } else {
            return null;
        }
    }

    public static <T> ResponseEntity<ResponseWrapper<T>> exception(Throwable throwable, T data, HttpStatus status,
                                                                   HttpServletRequest request) {
        if (throwable != null) {
            return ResponseEntity.status(status)
                    .body(ResponseWrapper.exception(data)
                            .addMessage(ApiResponseEntityUtils.getRootCauseMessage(throwable)).code(-1).request(request)
                            .build());
        } else {
            return null;
        }
    }

    public static <T> ResponseEntity<ResponseWrapper<T>> exception(int code, Throwable throwable, T data,
                                                                   HttpStatus status, HttpServletRequest request) {
        if (throwable != null) {
            return ResponseEntity.status(status)
                    .body(ResponseWrapper.exception(data)
                            .addMessage(ApiResponseEntityUtils.getRootCauseMessage(throwable)).code(code)
                            .request(request).build());
        } else {
            return null;
        }
    }

    private static String getRootCauseMessage(Throwable throwable) {
        if (throwable != null) {
            Throwable root = ExceptionUtils.getRootCause(throwable);
            root = root == null ? throwable : root;
            return root.getLocalizedMessage();
        }
        return null;
    }
}
