# Reading Tracker Spring Boot Application

Combines the tracking capabilities of services like [Goodreads](https://www.goodreads.com/)
to keep track of reading habits and books on your bookshelf with the intention
of tracking reading as a goal of language learning similar to the statistics
provided through reading with [LinQ](https://www.lingq.com/en/). This provides a
centralized location for keeping track of reading and see progress as the
bookshelf fills with finished books.

Sign in and view your bookshelf:

![Homepage](https://github.com/oneexists/readingTracker/blob/main/img/homepage.png)

Add books:

![Add Book Page](https://github.com/oneexists/readingTracker/blob/main/img/add_book.png)

View and edit book details:

![View Book Page](https://github.com/oneexists/readingTracker/blob/main/img/view_book.png)

![Edit Book Page](https://github.com/oneexists/readingTracker/blob/main/img/edit_book.png)

Add logs to track your reading:

![Add Log Page](https://github.com/oneexists/readingTracker/blob/main/img/add_log.png)

# Domain Model

![Reading Tracker Domain Model](https://github.com/oneexists/readingTracker/blob/main/img/reading_tracker_domain.png)

# Process Analysis

- Initial planning of interface was provided through Thymeleaf and Bootstrap.
  This required separate security configuration to support both the integrated
  front-end and the RESTful API. With the creation of a React front-end the
  security configuration was considerably simplified. This allows for simply
  supporting the RESTful API and creating a new web interface using React.

- Hypermedia driven RESTful API provides information dynamically with link relations
  to facilitate interaction

  [Spring Data REST Reference Guide](https://docs.spring.io/spring-data/rest/docs/current/reference/html/)

  ![RESTful API](https://github.com/oneexists/readingTracker/blob/main/img/restful_api.png)

# Target Process

- Implement React front-end to support interaction with RESTful API

- Provide additional reading summary statistics

- Provide a dynamic list of logs

- Create administrative dashboard of users and administrator account settings

# Functional Requirements

- RESTful API with JWT web token authorization

- Create, view, update, and delete books

- Create and delete reading logs

# Non-Functional Requirements

- Look and Feel:
    - addition of Bootstrap to improve UI display of forms

- Usability:
    - provide hypermedia driven RESTful API
    - React/Bootstrap front-end interface

- Security:
    - endpoint security of RESTful API using JWT web tokens

# Resources

[Spring Rest Tutorial](https://spring.io/guides/tutorials/rest/)

[How to Use Encrypted Property Placeholders in Spring Boot](https://access.redhat.com/documentation/zh-cn/red_hat_fuse/7.9/html/deploying_into_spring_boot/how-to-use-encrypted-property-placeholders-sping-boot)

[Spring Boot and Spring Security with JWT including Access and Refresh Tokens](https://youtu.be/VVn9OG9nfH0)

[Intercepting Filter Pattern Introduction](https://www.baeldung.com/intercepting-filter-pattern-in-java)

[Spring Security Reference - Multiple HttpSecurity](https://docs.spring.io/spring-security/site/docs/5.4.2/reference/html5/#multiple-httpsecurity)

[Spring Data REST Reference Guide](https://docs.spring.io/spring-data/rest/docs/current/reference/html/)
