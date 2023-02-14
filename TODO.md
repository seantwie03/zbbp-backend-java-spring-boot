# TODO

- Integration Test GlobalExceptionHandler
  The GlobalExceptionHandler not tested. If I were to unit and integration test it, there would be a lot of overlap. It
  is probably best to just write integration tests. I could pick a controller at random, then mock the service throwing
  all the different types of exceptions; however, changes to the random controller would break these integration tests.
  There should be a way to create an 'GlobalExceptionHandlerTestController' that is only active during integration
  tests. The way the GlobalExceptionHandler integration tests would only have one reason to fail.
