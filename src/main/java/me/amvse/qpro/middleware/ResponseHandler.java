package me.amvse.qpro.middleware;

import me.amvse.qpro.typings.RestException;
import me.amvse.qpro.typings.RestResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

@ControllerAdvice
public class ResponseHandler implements ResponseBodyAdvice<Object> {
  @Override
  public boolean supports (MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public RestResponse<Object> handleControllerException(HttpServletRequest request, Throwable exception) {
    if (exception instanceof RestException) return new RestResponse<>(((RestException)exception).getStatus(), ((RestException)exception).getMessage());

    return new RestResponse<>(500, exception.getMessage());
  }

  @Override
  public Object beforeBodyWrite (Object body, MethodParameter returnType, MediaType contentType, Class<? extends HttpMessageConverter<?>> converterType, ServerHttpRequest req, ServerHttpResponse res) {
    if (body instanceof RestResponse) return body;
    return new RestResponse<>(200, body);
  }
}
