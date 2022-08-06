package app.controller;

import static java.util.Map.entry;

import app.data.controller.response.ErrorResponse;
import app.data.controller.response.ImmutableErrorResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

import javax.validation.ConstraintViolationException;

abstract class ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    private static final Map<Class<? extends Exception>, HttpStatus> clientErrorMap = Map.ofEntries(
            entry(HttpRequestMethodNotSupportedException.class, HttpStatus.METHOD_NOT_ALLOWED),
            entry(HttpMediaTypeNotSupportedException.class, HttpStatus.UNSUPPORTED_MEDIA_TYPE),
            entry(HttpMediaTypeNotAcceptableException.class, HttpStatus.NOT_ACCEPTABLE),
            entry(MissingPathVariableException.class, HttpStatus.BAD_REQUEST),
            entry(MissingServletRequestParameterException.class, HttpStatus.BAD_REQUEST),
            entry(ServletRequestBindingException.class, HttpStatus.BAD_REQUEST),
            entry(ConversionNotSupportedException.class, HttpStatus.BAD_REQUEST),
            entry(TypeMismatchException.class, HttpStatus.BAD_REQUEST),
            entry(HttpMessageNotReadableException.class, HttpStatus.BAD_REQUEST),
            entry(HttpMessageNotWritableException.class, HttpStatus.BAD_REQUEST),
            entry(MethodArgumentNotValidException.class, HttpStatus.BAD_REQUEST),
            entry(MissingServletRequestPartException.class, HttpStatus.BAD_REQUEST),
            entry(BindException.class, HttpStatus.BAD_REQUEST),
            entry(NoHandlerFoundException.class, HttpStatus.NOT_FOUND),
            entry(ConstraintViolationException.class, HttpStatus.BAD_REQUEST),
            entry(MethodArgumentTypeMismatchException.class, HttpStatus.BAD_REQUEST));

    protected <T> T handle(final Exception ex, final WebRequest request,
            final BiFunction<HttpStatus, ErrorResponse, T> responseFactory) {
        final HttpStatus status;
        final String message;

        if (ex instanceof ResponseStatusException) {
            status = ((ResponseStatusException) ex).getStatus();
            if (status.is4xxClientError()) {
                message = ex.getMessage();
                logger.warn("{}: {}", message, request);
            } else {
                message = status.getReasonPhrase();
                logger.error(String.format("%s: %s", message, request), ex);
            }
        } else {
            final HttpStatus clientErrorHttpStatus = clientErrorMap.get(ex.getClass());
            if (clientErrorHttpStatus != null) {
                status = clientErrorHttpStatus;
                message = ex.getMessage();
                logger.warn("{}: {}", message, request);
            } else {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                message = status.getReasonPhrase();
                logger.error(String.format("%s: %s", message, request), ex);
            }
        }

        return responseFactory.apply(status,
                ImmutableErrorResponse.builder().id(createErrorId()).message(message).build());
    }

    protected String createErrorId() {
        return UUID.randomUUID().toString();
    }

}
