# TODO

- Add tests for Budget flow
  - OR
  - Do this logic on the frontend
- Implement OpenApi documentation
  - Have it somehow use javadoc to add additional details
- Add authentication and authorization with Spring Security
- Add at least one @SpringBootTest to ensure the application context starts
  - Maybe a 'happy-path' e2e test for Transaction and LineItem would be beneficial.
    - Call API endpoint to POST a new Transaction
    - Call API endpoint to get the posted Transaction by date
    - Call API endpoint to get the posted Transaction by id
    - Call API endpoint to PUT an update to the Transaction
    - Call API endpoint to DELETE the Transaction
    - Repeat for LineItem
- Integration Test GlobalExceptionHandler
  The GlobalExceptionHandler not tested. If I were to unit and integration test it, there would be a lot of overlap. It
  is probably best to just write integration tests. I could pick a controller at random, then mock the service throwing
  all the different types of exceptions; however, changes to the random controller would break these integration tests.
  There should be a way to create an 'GlobalExceptionHandlerTestController' that is only active during integration
  tests. The way the GlobalExceptionHandler integration tests would only have one reason to fail.
- Make it clear through the type system what amounts are in dollars and what amounts are in cents.
- Change out If-Unmodified-Since for eTag?
  - Nothing stopping caller from putting Instant.now() in If-Unmodified-Since header and overwriting database entry.
  - But that is a malicious action. Is If-Unmodified-Since supposed to protect against malicious users? I don't think
    so.
  - eTag may be a bit better. I could say is the record in the DB have exactly this eTag. If no, do not overwrite.
  - That way a malicious user would have to perform a get request first.
  - Really don't think this is worth worrying about.
