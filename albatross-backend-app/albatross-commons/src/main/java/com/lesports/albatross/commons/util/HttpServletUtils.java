package com.lesports.albatross.commons.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lesports.albatross.commons.web.domain.ResponseWrapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Convenience class for setting and retrieving cookies.
 */

public final class HttpServletUtils {

    private HttpServletUtils() {
    }

    public static String getRemoteIPAddress(HttpServletRequest request) {

        String remoteIP = request.getHeader("x-forwarded-for");
        if (remoteIP == null || remoteIP.length() == 0 || "unknown".equalsIgnoreCase(remoteIP)) {
            remoteIP = request.getHeader("Proxy-Client-IP");
        }
        if (remoteIP == null || remoteIP.length() == 0 || "unknown".equalsIgnoreCase(remoteIP)) {
            remoteIP = request.getHeader("WL-Proxy-Client-IP");
        }
        if (remoteIP == null || remoteIP.length() == 0 || "unknown".equalsIgnoreCase(remoteIP)) {
            remoteIP = request.getRemoteAddr();
        }
        if (remoteIP != null && remoteIP.length() > 0) {
            String[] ips = remoteIP.split(",");
            for (int i = 0; i < ips.length; i++) {
                if (ips[i].trim().length() > 0 && !"unknown".equalsIgnoreCase(ips[i].trim())) {
                    remoteIP = ips[i].trim();
                    break;
                }
            }
        }
        return remoteIP;
    }

    /**
     * Convenience method to toPageable the application's URL based on request
     * variables.
     *
     * @param request the current request
     * @return URL to application
     */

    public static String getAppURL(HttpServletRequest request) {
        if (request == null)
            return "";

        StringBuffer url = new StringBuffer();
        int port = request.getServerPort();
        if (port < 0) {
            port = 80; // Work around java.net.URL bug
        }
        String scheme = request.getScheme();
        url.append(scheme);
        url.append("://");
        url.append(request.getServerName());
        if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
            url.append(':');
            url.append(port);
        }
        url.append(request.getContextPath());
        return url.toString();
    }

    public static void sendForbiddenError(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        String reason = (ex == null) ? null : ExceptionUtils.getRootCauseMessage(ex);

        ResponseWrapper<?> responseWrapper = ResponseWrapper
                .exception(reason)
                .addMessage(reason)
                .code(4030)
                .request(request)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), responseWrapper);
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    public static void sendBadRequestError(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    public static void sendUnauthorizedError(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        String reason = ExceptionUtils.getRootCauseMessage(ex);

        ResponseWrapper<?> responseWrapper = ResponseWrapper
                .exception(reason)
                .addMessage(reason)
                .addMessage("Authentication token was either missing or invalid")
                .code(4000)
                .request(request)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), responseWrapper);
    }

    public static String getRequestBody(HttpServletRequest request) {
        StringBuffer buffer = new StringBuffer();
        try {
            InputStream in = request.getInputStream();
            BufferedReader bin = new BufferedReader(new InputStreamReader(in, "UtF-8"));
            String line = "";
            while ((line = bin.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    public static String getRequestParam(HttpServletRequest request) {
        Iterator iterator = request.getParameterMap().entrySet().iterator();
        StringBuffer param = new StringBuffer();
        int i = 0;
        while (iterator.hasNext()) {
            i++;
            Entry entry = (Entry) iterator.next();
            if (i == 1)
                param.append("?").append(entry.getKey()).append("=");
            else
                param.append("&").append(entry.getKey()).append("=");
            if (entry.getValue() instanceof String[]) {
                param.append(((String[]) entry.getValue())[0]);
            } else {
                param.append(entry.getValue());
            }
        }
        return param.toString();
    }
}
