package me.seantwiehaus.zbbp.dto.response;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExceptionResponseTest {
  HttpServletRequest request = mock(HttpServletRequest.class);

  String contextPath = "/api";
  String servletPath = "/servlet/path/1";

  @Test
  void extractsCorrectPathFromHttpServletRequestWhenQueryStringIsNull() {
    // Given a null query string
    when(request.getQueryString()).thenReturn(null);
    // And valid context & servlet paths
    when(request.getContextPath()).thenReturn(contextPath);
    when(request.getServletPath()).thenReturn(servletPath);

    // When an ExceptionResponse is created
    ExceptionResponse response = new ExceptionResponse(400, "Error Message", request);

    // Then the path should be the contextPath and servletPath combined
    assertEquals(contextPath + servletPath, response.getPath());
  }

  @Test
  void extractsCorrectPathFromHttpServletRequestWhenQueryStringIsNotNull() {
    // Given a non-null query string
    when(request.getQueryString()).thenReturn("abc=1");
    // And valid context & servlet paths
    when(request.getContextPath()).thenReturn(contextPath);
    when(request.getServletPath()).thenReturn(servletPath);

    // When an ExceptionResponse is created
    ExceptionResponse response = new ExceptionResponse(400, "Error Message", request);

    // Then the path should be the contextPath, servletPath, and queryString combined
    assertEquals(contextPath + servletPath + "?abc=1", response.getPath());
  }
}
