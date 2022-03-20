package net.prolancer.signature.common.entity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Custom ResponseEntity Object
 * @author jaechulhan
 */
public class ResponseHandler {
    /**
     * Generate success response message
     * @param message
     * @param resObj
     * @param
     * @return
     */
    public static ResponseEntity<Object> ok(String message, Object resObj) {
        return ok(message, HttpStatus.OK, resObj);
    }

    /**
     * Generate success response message
     * @param message
     * @param status
     * @param resObj
     * @param
     * @return
     */
    public static ResponseEntity<Object> ok(String message, HttpStatus status, Object resObj) {
        Map<String, Object> resMap = new LinkedHashMap<>(); // To preserve insertion order, use LinkedHashMap

        resMap.put("status", "success");
        resMap.put("httpStatus", status.value());
        resMap.put("message", message);
        resMap.put("data", resObj);

        return new ResponseEntity<>(resMap, status);
    }

    /**
     * Generate error response message
     * @param message
     * @param resObj
     * @return
     */
    public static ResponseEntity<Object> error(String message, Object resObj) {
        return error(message, HttpStatus.OK, resObj);
    }

    /**
     * Generate error response message
     * @param message
     * @param status
     * @param resObj
     * @return
     */
    public static ResponseEntity<Object> error(String message, HttpStatus status, Object resObj) {
        Map<String, Object> resMap = new LinkedHashMap<>(); // To preserve insertion order, use LinkedHashMap

        resMap.put("status", "error");
        resMap.put("httpStatus", status.value());
        resMap.put("message", message);
        resMap.put("data", resObj);

        return new ResponseEntity<>(resMap, status);
    }
}
