package me.seantwiehaus.zbbp.dto.response;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

import java.time.Instant;

@Getter
// Kept this as a class because if it is converted to a record the timestamp would need to be passed in everytime an
// ExceptionResponse is created. In all the situations this class is currently used, that would mean passing
// 'Instant.now()' into the constructor. Keeping this as a class allows me to set this value on the field without
// requiring it as a constructor parameter.
public class ExceptionResponse {
  private final Instant timestamp = Instant.now();
  private final Integer status;
  private final String error;
  private final String path;

  public ExceptionResponse(Integer status,
                           String error,
                           HttpServletRequest request) {
    this.status = status;
    this.error = error;
    this.path = formatFullPath(request);
  }

  private String formatFullPath(HttpServletRequest request) {
    if (request.getQueryString() == null) {
      return request.getContextPath() + request.getServletPath();
    }
    return request.getContextPath() + request.getServletPath() + "?" + request.getQueryString();
  }
}
