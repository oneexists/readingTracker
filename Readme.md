# Reading Tracker Spring Boot Application

Provides a hypermedia RESTful API and web interface to create a digital bookshelf of books.
Keep track of the books your bookshelf and the books you've read by adding books and reading
logs to record the start and finish dates.

# Process Analysis

- Initial securitly configuration provided by JWT web tokens were implemented utilizing the
Intercepting Filter Pattern to allow for the definition of custom authentication and
authorization filters to manage requests to the API.

  [Spring Boot and Spring Security with JWT including Access and Refresh Tokens](https://youtu.be/VVn9OG9nfH0)

  [Intercepting Filter Pattern Introduction](https://www.baeldung.com/intercepting-filter-pattern-in-java)

- Reconfiguration of the security implementation was integrated into the application to support
login functionality for both the RESTful API and custom login form of the web interface.
Further research into implementation options led to the definition of multiple security
configurations to serve both login functionalities simultaneously.

  [Spring Security Reference - Multiple HttpSecurity](https://docs.spring.io/spring-security/site/docs/5.4.2/reference/html5/#multiple-httpsecurity)

  ![Login / Logout Page](https://github.com/oneexists/readingTracker/blob/main/img/login_logout_page.png)

- Hypermedia REST API integrated through leaveraging Spring Data REST

  [Spring Data REST Reference Guide](https://docs.spring.io/spring-data/rest/docs/current/reference/html/)

# Target Process

- Provide reading summary statistics

- Improve UI interaction in web interface

# Functional Requirements

- Create, view, update, and delete books
- Create reading logs

# Non-Functional Requirements

- Look and Feel: improve front-end development skills by learning new technologies to
improve UI/UX of web interface

- Usability: provide both RESTful API for extension and integrated front-end interface

- Security: integrate security configuration with custom login and registration forms

# Resources

[Spring Rest Tutorial](https://spring.io/guides/tutorials/rest/)

[How to Use Encrypted Property Placeholders in Spring Boot](https://access.redhat.com/documentation/zh-cn/red_hat_fuse/7.9/html/deploying_into_spring_boot/how-to-use-encrypted-property-placeholders-sping-boot)

[Spring Boot and Spring Security with JWT including Access and Refresh Tokens](https://youtu.be/VVn9OG9nfH0)

[Intercepting Filter Pattern Introduction](https://www.baeldung.com/intercepting-filter-pattern-in-java)

[Spring Security Reference - Multiple HttpSecurity](https://docs.spring.io/spring-security/site/docs/5.4.2/reference/html5/#multiple-httpsecurity)

[Spring Data REST Reference Guide](https://docs.spring.io/spring-data/rest/docs/current/reference/html/)
