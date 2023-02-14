# TODO

- Integration Test GlobalExceptionHandler
  The GlobalExceptionHandler is unit tested but has no integration tests. I could pick a controller at random, then mock
  the service throwing all the different types of exceptions; however, changes to the random controller would break
  these integration tests. There should be a way to create an 'GlobalExceptionHandlerTestController' that is only active
  during integration tests. The way the GlobalExceptionHandler integration tests would only have one reason to fail.
