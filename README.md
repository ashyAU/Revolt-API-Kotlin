# Revolt API Kotlin 
The purpose of this project is to create a more streamlined approach utilizing the API with Kotlin, with seperate packages being available depending on the type of data you need. 
All API requests are developed using Ktor and OKHttp.

There are simple tests within each API function to ensure everything is in working order
## Authentication - On Going
  ### Account - Next Planned
  ```text
  POST/auth/account/create
  POST/auth/account/reverify
  PUT/auth/account/delete - todo
  POST/auth/account/delete
  // all below are currently ongoing
  GET/auth/account/
  POST/auth/account/disable
  PATCH/auth/account/change/password
  PATCH/auth/account/change/email
  POST/auth/account/verify/{code}
  POST/auth/account/reset_password
  PATCH/auth/account/reset_password
```
  ### Session - Completed
  ```text
  POST/auth/session/login
  POST/auth/session/logout
  GET/auth/session/all
  DELETE/auth/session/all
  DELETE/auth/session/{id}
  PATCH/auth/session/{id}
  ```
