# stc-assessment
SpringBoot application to download/ upload files.


## Problem Solving
> Time Complexity is O(N) <br>
> Space Complexity is O(N) 

+ where N is the length of the string. worth mentioning that it could be done in constant space O(1) if we are using C++. (so we don't have to create another string since String is immutable in Java.)
+ There is another concern, the statement was not clear because it didn't mention whether there could be nested parentheses or not. (I assumed that it does not since we keep the parentheses)


## Database Query
Nothing to mention.


## System Design
+ I Created all the requested end-points.
+ I also added Swagger for the APIs documentation, if you are interested in having a look
> `http://localhost:8080/swagger-ui/index.html`
+ Did my best to follow the SOLID principles, especially the "SRS".
+ The things I used
  + SpringBoot - PostgreSQL - JUnit - Mockito - Swagger
+ Things TODO
  + Adding a mapper.
  + Practice TDD - Docker more.
  + Try creating a GraphQL end-point.

