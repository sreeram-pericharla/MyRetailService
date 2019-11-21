package com.myretail.casestudy.configuration;

import com.myretail.casestudy.model.ErrorResponse;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class ServiceExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger("ServiceExceptionHandler");
    public static final String EXTERNAL_SERVICE_ERRORMESSAGE = "One of the external service call failed for the request";
    public static final String INTERNAL_SERVER_ERRORMESSAGE = "Something went wrong, Please try again later after sometime";


    /**
     * handle all service level exception with respective httpstutu code and error message
     * @param ex
     * @return ResponseEntity
     */
    @ExceptionHandler
    @ResponseBody
    ResponseEntity<ErrorResponse> handleException(Exception ex){
        LOGGER.error("Exception occured: ", ex);
        HttpStatus httpStatus;
        String errorMessage;
        ErrorResponse errorResponse = new ErrorResponse();

        if(ex instanceof MethodArgumentNotValidException){
            httpStatus = HttpStatus.BAD_REQUEST;
            BindingResult result = ((MethodArgumentNotValidException) ex).getBindingResult();
            List<FieldError> fieldErrors = result.getFieldErrors();
            errorResponse.setErrorMessage(String.join(", ", fieldErrors.stream().map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage()).collect(Collectors.toList())));
        } else if(ex instanceof MethodArgumentTypeMismatchException){
            httpStatus = HttpStatus.BAD_REQUEST;
            errorResponse.setErrorMessage(ex.getMessage());
        } else if(ex instanceof HttpMessageNotReadableException){
            httpStatus = HttpStatus.BAD_REQUEST;
            errorResponse.setErrorMessage(StringUtils.substringBefore(ex.getMessage(), ";"));
        } else if(ex instanceof ConstraintViolationException){
            httpStatus = HttpStatus.BAD_REQUEST;
            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) ex).getConstraintViolations();
            errorResponse.setErrorMessage(String.join(", ", violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList())));
        } else if(ex instanceof ResourceNotFoundException){
            httpStatus = HttpStatus.NOT_FOUND;
            errorResponse.setErrorMessage(ex.getMessage());
        } else if(ex instanceof HystrixRuntimeException){
            httpStatus = HttpStatus.FAILED_DEPENDENCY;
            errorResponse.setErrorMessage(EXTERNAL_SERVICE_ERRORMESSAGE);
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            errorResponse.setErrorMessage(INTERNAL_SERVER_ERRORMESSAGE);
        }
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
