# Backend

This folder contains the server-side code for the Pharma MES System.  
It includes APIs, business logic, and integration with the database.  

## Contents
- Core application logic
- API endpoints
- Authentication and authorization

# 🚀 Day 5 - Development Log & Struggle Report

> **Project:** Pharma MES Backend  
> **Day:** 5  
> **Technology Stack:** Spring Boot 3.5.4, Spring Security, JWT, JPA, Hibernate, MySQL, Maven

---

# 📌 Objective

The objective of Day 5 was to implement a secure Equipment Management module with JWT authentication.

Modules planned for Day 5:

- JWT Authentication
- Equipment CRUD
- Equipment Search
- Equipment Filters
- Pagination
- Equipment Maintenance

---

# ⚠️ Major Challenges Faced

## 1. StackOverflowError During Login

### Problem

The login API continuously failed with:

```
java.lang.StackOverflowError
```

Although the request successfully reached the controller, execution never returned after calling:

```java
authenticationManager.authenticate(
    new UsernamePasswordAuthenticationToken(
        request.getUsername(),
        request.getPassword()));
```

The stack trace repeatedly displayed:

```
JdkDynamicAopProxy.invoke()
authenticate()
authenticate()
authenticate()
...
```

---

### Root Cause

The Spring Security authentication flow was recursively calling itself because of an incorrect authentication configuration.

Additionally, the project was initially created using **Spring Boot 4**, which introduced compatibility issues with Spring Security and authentication.

---

### Solution

- Downgraded the project to **Spring Boot 3.5.4**
- Removed unnecessary dependencies
- Recreated the authentication configuration
- Rebuilt the Security Configuration from scratch
- Reconfigured AuthenticationManager

---

# 2. Debugging Authentication Flow

Since the login controller was being reached but authentication failed, debugging statements were added.

Example:

```java
System.out.println("Step 1");
System.out.println("Step 2");
System.out.println("Step 3");
```

Only **Step 1** was printed.

This confirmed that the failure occurred inside:

```java
authenticationManager.authenticate(...)
```

rather than inside the controller.

---

# 3. JWT Principal Casting Exception

### Error

```
class org.springframework.security.core.userdetails.User
cannot be cast to

com.anandh.mes.security.CustomUserDetails
```

---

### Cause

Spring Security returned its default implementation:

```
org.springframework.security.core.userdetails.User
```

instead of:

```
CustomUserDetails
```

The JWT utility was force-casting the authenticated user.

---

### Solution

Modified the JWT generation logic to work with the generic:

```java
UserDetails
```

instead of casting to:

```java
CustomUserDetails
```

This resolved the issue completely.

---

# 4. Role Missing in JWT Response

After login, the response looked like:

```json
{
    "accessToken": "...",
    "username": "admin",
    "role": ""
}
```

---

### Cause

The role was not being extracted correctly from the authenticated user's authorities.

---

### Solution

Used:

```java
userDetails.getAuthorities()
```

to retrieve the user's assigned role before constructing the response.

---

# 5. Equipment CRUD Module

Successfully developed:

- Equipment Entity
- Equipment DTO
- Equipment Repository
- Equipment Service
- Equipment Service Implementation
- Equipment Controller

Implemented APIs:

- Create Equipment
- Update Equipment
- Delete Equipment
- Get Equipment
- Get All Equipment

Additional features:

- Search by Name
- Filter by Status
- Filter by Type
- Filter by Location
- Pagination

---

# 6. Equipment Maintenance Module

Created:

- EquipmentMaintenance Entity
- EquipmentMaintenanceDTO
- EquipmentMaintenanceRepository
- EquipmentMaintenanceService
- EquipmentMaintenanceController

---

# 7. Application Failed to Start

### Error

```
APPLICATION FAILED TO START

Parameter 0 of constructor in
EquipmentMaintenanceController

required a bean of type

EquipmentMaintenanceService
```

---

### Cause

The implementation class was missing.

Only this interface existed:

```
EquipmentMaintenanceService.java
```

There was no:

```
EquipmentMaintenanceServiceImpl.java
```

---

### Solution

Created:

```
EquipmentMaintenanceServiceImpl
```

Annotated with:

```java
@Service
```

and implemented all required service methods.

Application started successfully afterward.

---

# 8. Authorization Errors

While testing protected APIs, Spring Security returned:

```
AuthorizationDeniedException
```

---

### Cause

Protected endpoints required JWT authentication.

---

### Solution

Sent the JWT token with every protected request:

```
Authorization: Bearer <JWT_TOKEN>
```

Authentication and authorization then worked correctly.

---

# 🧪 Testing Completed

## Authentication

- ✅ Register User
- ✅ Login
- ✅ JWT Generation
- ✅ JWT Validation
- ✅ Role Retrieval

---

## Equipment Module

- ✅ Create Equipment
- ✅ Get Equipment
- ✅ Update Equipment
- ✅ Delete Equipment
- ✅ Search Equipment
- ✅ Filter by Status
- ✅ Filter by Type
- ✅ Filter by Location
- ✅ Pagination

---

## Equipment Maintenance

- ✅ Create Maintenance Record
- ✅ Retrieve Maintenance History

---

# 📚 Key Learnings

Day 5 provided a deep understanding of:

- Spring Security Architecture
- AuthenticationManager
- UserDetailsService
- JWT Authentication Flow
- Role-Based Authentication
- Dependency Injection
- Bean Creation
- Debugging Spring Boot Applications
- REST API Design
- JPA Relationships
- Pagination
- Service Layer Architecture
- DTO Mapping
- Exception Handling

---

# 📂 Modules Completed

```
Authentication
│
├── Register
├── Login
├── JWT Authentication
├── JWT Authorization
└── Role-Based Access

Equipment
│
├── CRUD
├── Search
├── Filters
├── Pagination
└── Maintenance
```

---

# 📈 Project Progress

```
Day 1  ██████████ 100%
Day 2  ██████████ 100%
Day 3  ██████████ 100%
Day 4  ██████████ 100%
Day 5  ██████████ 100%

Overall Progress

████████████████████░░░░░░░░░░░░
≈ 40% Complete
```

---

# 🎯 Biggest Takeaway

Day 5 was the most technically demanding stage of the project so far. The majority of the time was spent diagnosing authentication issues rather than writing new features. Resolving problems such as recursive authentication (`StackOverflowError`), JWT principal casting, missing service beans, and authorization failures required a deeper understanding of Spring Security, dependency injection, and the application startup lifecycle.

By the end of the day, the backend supported secure JWT-based authentication, role-based authorization, a fully functional Equipment Management module, and an Equipment Maintenance module. These components now form a stable foundation for implementing the remaining MES modules in subsequent development days.

---

# ✅ Day 5 Status

**Status:** Completed Successfully

**Outcome:** Stable JWT authentication, Equipment Management, and Equipment Maintenance modules implemented and verified successfully.