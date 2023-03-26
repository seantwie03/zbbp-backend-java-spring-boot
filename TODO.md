# TODO

- Test BudgetController::createBudgetFor Flow
  - This flow is completely untested.
  - Update the BudgetControllerApi.http file
- Consolidate Name of budgetYearMonth
  - In some place it is called yearMonth, budgetYearMonth, budgetDate, etc.
  - All these names mean the same thing, the Year and Month for this Budget or LineItem
- Add authentication and authorization with Spring Security
- Implement OpenApi documentation
  - Have it somehow use javadoc to add additional details
- Add at least one @SpringBootTest to ensure the application context starts
  - Maybe a 'happy-path' e2e test for Transaction and LineItem would be beneficial.
    - Call API endpoint to POST a new Transaction
    - Call API endpoint to get the posted Transaction by date
    - Call API endpoint to get the posted Transaction by id
    - Call API endpoint to PUT an update to the Transaction
    - Call API endpoint to DELETE the Transaction
    - Repeat for LineItem
- Make it clear through the type system what amounts are in dollars and what amounts are in cents.
  - Possibly switch to a monetary type that can easily convert between them
- Integration Test GlobalExceptionHandler
  The GlobalExceptionHandler not tested. If I were to unit and integration test it, there would be a lot of overlap. It
  is probably best to just write integration tests. I could pick a controller at random, then mock the service throwing
  all the different types of exceptions; however, changes to the random controller would break these integration tests.
  There should be a way to create an 'GlobalExceptionHandlerTestController' that is only active during integration
  tests. The way the GlobalExceptionHandler integration tests would only have one reason to fail.
