# Day 16 --- Frontend Development Begins

## Overview

Day 16 marks the transition of the Pharma MES project from a
backend-focused Spring Boot application into a full-stack MES
application.

The backend APIs, entities, services, repositories, security, JWT
authentication, and testing were already completed. Day 16 started the
frontend using React, Vite, Material UI, React Router, and Axios.

The objective was to establish a clean frontend foundation that can
securely consume the existing Spring Boot REST APIs.

------------------------------------------------------------------------

## 1. Frontend Technology Stack

  Technology      Purpose
  --------------- -------------------------------------
  React           UI development
  Vite            Frontend development and build tool
  Material UI     UI components and styling
  React Router    Client-side navigation
  Axios           REST API communication
  JWT             Authentication
  Local Storage   Current client-side token storage

Frontend:

``` text
http://localhost:5173
```

Backend:

``` text
http://localhost:8080
```

Architecture:

``` text
React Frontend :5173
        |
        | REST API
        v
Spring Boot :8080
```

------------------------------------------------------------------------

## 2. React + Vite Project

The frontend project was created as:

``` text
pharma-mes-frontend/
```

The default Vite content was cleaned so the application could be built
specifically for Pharma MES.

------------------------------------------------------------------------

## 3. Dependencies Installed

Material UI:

``` bash
npm install @mui/material @emotion/react @emotion/styled
```

Material UI icons:

``` bash
npm install @mui/icons-material
```

React Router:

``` bash
npm install react-router-dom
```

Axios:

``` bash
npm install axios
```

### Purpose

-   **Material UI** --- reusable enterprise-style UI components.
-   **Icons** --- navigation and action icons.
-   **React Router** --- frontend page navigation.
-   **Axios** --- communication with Spring Boot APIs.

------------------------------------------------------------------------

## 4. Frontend Folder Architecture

The frontend structure was established before creating the application
modules.

``` text
src/
├── assets/
├── components/
│   ├── dashboard/
│   └── navigation/
├── layouts/
├── pages/
│   ├── auth/
│   └── dashboard/
├── services/
├── context/
├── hooks/
├── routes/
├── utils/
├── App.jsx
├── index.css
└── main.jsx
```

### Folder responsibilities

  Folder          Responsibility
  --------------- ----------------------------
  `components/`   Reusable UI components
  `layouts/`      Common application layouts
  `pages/`        Application screens
  `services/`     Backend API communication
  `context/`      Future global React state
  `hooks/`        Future custom React hooks
  `routes/`       React Router configuration
  `utils/`        Helper functions
  `assets/`       Images and other assets

------------------------------------------------------------------------

## 5. React Router

Initial routes:

``` text
/              → /login
/login         → Login
/dashboard     → Dashboard
/invalid-path  → /login
```

React Router provides the foundation for future MES modules:

``` text
/production
/work-orders
/batches
/inventory
/quality
/equipment
/reports
/notifications
```

------------------------------------------------------------------------

## 6. Login Page

Created:

``` text
src/pages/auth/Login.jsx
```

The login page contains:

``` text
Pharma MES
Manufacturing Execution System

Username
Password

Login
```

Material UI components were used for the form.

The login form captures:

``` text
username
password
```

and sends them to the existing Spring Boot authentication endpoint.

------------------------------------------------------------------------

## 7. Existing JWT Authentication Integration

The actual backend login endpoint is:

``` http
POST /auth/login
```

The frontend therefore calls:

``` text
http://localhost:8080/auth/login
```

The successful response has the structure:

``` json
{
  "accessToken": "...",
  "tokenType": "Bearer",
  "username": "admin",
  "role": "ROLE_ADMIN"
}
```

The React frontend successfully received this response.

------------------------------------------------------------------------

## 8. Authentication Service

Created:

``` text
src/services/authService.js
```

Responsibilities:

-   Send login request.
-   Receive JWT response.
-   Store authentication information.
-   Retrieve the token.
-   Check authentication state.
-   Remove authentication information during logout.

Stored values:

``` text
accessToken
tokenType
username
role
```

------------------------------------------------------------------------

## 9. Axios Configuration

Created:

``` text
src/services/api.js
```

Axios uses:

``` text
http://localhost:8080
```

as its base URL.

A request interceptor checks for:

``` javascript
localStorage.getItem("accessToken")
```

When a token exists, the request receives:

``` http
Authorization: Bearer <JWT>
```

This means protected APIs do not require every component to manually
construct the authorization header.

------------------------------------------------------------------------

## 10. CORS Integration

The initial frontend-to-backend request was blocked by CORS because:

``` text
Frontend → http://localhost:5173
Backend  → http://localhost:8080
```

are different origins.

CORS was configured in the Spring Boot security configuration to allow
the React development server.

After the CORS configuration, the browser could reach Spring Security.

The error then changed to:

``` text
401 Unauthorized
```

This confirmed that the network/CORS problem was solved and that
authentication was the remaining requirement.

------------------------------------------------------------------------

## 11. Protected Batch API

Created:

``` text
src/services/batchService.js
```

The service exposes the batch API through the centralized Axios
instance.

Example:

``` javascript
export const getAllBatches = async () => {
    const response = await api.get("/api/batches");
    return response.data;
};
```

The request is:

``` http
GET /api/batches
```

The JWT interceptor automatically adds:

``` http
Authorization: Bearer <JWT>
```

------------------------------------------------------------------------

## 12. Authentication Flow

The completed flow is:

``` text
Login.jsx
    |
    | username + password
    v
POST /auth/login
    |
    v
Spring Security
    |
    v
JWT response
    |
    v
Local Storage
    |
    v
Axios interceptor
    |
    | Authorization: Bearer <JWT>
    v
Protected MES API
    |
    v
Spring Boot
    |
    v
Database
```

------------------------------------------------------------------------

## 13. Dashboard

Created:

``` text
src/pages/dashboard/Dashboard.jsx
```

The dashboard consumes real backend data.

The Batch API returned:

``` text
3 batches
```

The dashboard displays:

``` text
MES Dashboard

Total Batches
3

Retrieved from MES
```

This confirms that the dashboard is connected to the backend rather than
using hardcoded batch data.

------------------------------------------------------------------------

## 14. Main Layout

Created:

``` text
src/layouts/MainLayout.jsx
```

The main layout combines:

``` text
Navbar
Sidebar
Main Content
```

Conceptually:

``` text
┌─────────────────────────────────────────────────────┐
│ Navbar                                              │
├──────────────┬──────────────────────────────────────┤
│ Sidebar      │ Main Content                         │
│              │                                      │
│              │ Dashboard                            │
│              │                                      │
└──────────────┴──────────────────────────────────────┘
```

------------------------------------------------------------------------

## 15. Sidebar

Created:

``` text
src/components/navigation/Sidebar.jsx
```

Initial navigation:

``` text
Dashboard
Production
Work Orders
Inventory
Quality
Equipment
Reports
Notifications
```

The navigation structure is ready for the corresponding MES pages to be
implemented in future days.

------------------------------------------------------------------------

## 16. Navbar

Created:

``` text
src/components/navigation/Navbar.jsx
```

The navbar displays:

``` text
Manufacturing Execution System
```

along with:

``` text
admin
```

and provides:

-   Notification icon
-   Logout icon

------------------------------------------------------------------------

## 17. Logout

Logout uses:

``` text
authService.logout()
```

The authentication values are removed:

``` text
accessToken
tokenType
username
role
```

The user is redirected to:

``` text
/login
```

Flow:

``` text
Dashboard
    |
    v
Logout
    |
    v
JWT removed
    |
    v
Login
```

------------------------------------------------------------------------

## 18. Reusable Dashboard Card

Created:

``` text
src/components/dashboard/StatCard.jsx
```

The component accepts:

``` text
title
value
subtitle
```

Initial cards:

``` text
Total Batches
Production Orders
Quality Inspections
Notifications
```

Currently, only **Total Batches** is connected to a backend API.

The other cards are placeholders and will be connected to their
corresponding APIs later.

------------------------------------------------------------------------

## 19. Final Frontend Structure

At the end of Day 16:

``` text
pharma-mes-frontend/
└── src/
    ├── assets/
    │
    ├── components/
    │   ├── dashboard/
    │   │   └── StatCard.jsx
    │   └── navigation/
    │       ├── Navbar.jsx
    │       └── Sidebar.jsx
    │
    ├── layouts/
    │   └── MainLayout.jsx
    │
    ├── pages/
    │   ├── auth/
    │   │   └── Login.jsx
    │   └── dashboard/
    │       └── Dashboard.jsx
    │
    ├── services/
    │   ├── api.js
    │   ├── authService.js
    │   └── batchService.js
    │
    ├── context/
    ├── hooks/
    ├── routes/
    │   └── AppRoutes.jsx
    ├── utils/
    │
    ├── App.jsx
    ├── index.css
    └── main.jsx
```

------------------------------------------------------------------------

## 20. Day 16 Testing

The following were successfully verified:

-   React + Vite application runs.
-   Material UI renders correctly.
-   React Router works.
-   Login UI works.
-   `POST /auth/login` works.
-   JWT access token is received.
-   Authentication information is stored.
-   Axios interceptor attaches the JWT.
-   CORS is configured.
-   Protected `/api/batches` works.
-   Backend returned **3 batches**.
-   Dashboard displays the backend batch count.
-   Sidebar renders.
-   Navbar renders.
-   Logged-in username is displayed.
-   Logout functionality is implemented.

------------------------------------------------------------------------

## 21. Full-Stack Architecture After Day 16

``` text
                         PHARMA MES
                             |
             ┌───────────────┴───────────────┐
             |                               |
       REACT FRONTEND                   SPRING BOOT
             |                               |
       React + Vite                     REST APIs
             |                               |
       Material UI                    Spring Security
             |                               |
       React Router                         JWT
             |                               |
          Axios ─────────────────────────────┤
             |                               |
      JWT Interceptor                        |
             |                               |
             └────── Protected API ──────────┘
                             |
                          Database
```

------------------------------------------------------------------------

## 22. Important Development Pattern

Day 16 established the frontend pattern that will be reused for all
future MES modules:

``` text
Page
  ↓
Service
  ↓
Axios
  ↓
JWT Interceptor
  ↓
REST API
  ↓
Spring Security
  ↓
Backend Service
  ↓
Repository
  ↓
Database
```

For example:

``` text
Quality Page
    ↓
qualityService.js
    ↓
api.js
    ↓
/api/quality-inspections
```

and:

``` text
Work Order Page
    ↓
workOrderService.js
    ↓
api.js
    ↓
/api/work-orders
```

This keeps API communication separate from UI components and makes the
frontend easier to maintain.

------------------------------------------------------------------------

# Day 16 Status

## COMPLETE

Day 16 officially marks the **start of frontend development** for the
Pharma MES project.

The project has progressed from:

``` text
Backend APIs
    ↓
Database
```

to:

``` text
React Frontend
    ↓
JWT Authentication
    ↓
Axios
    ↓
Protected Spring Boot APIs
    ↓
Database
```

The next development days can focus on converting the existing MES
backend modules into actual frontend screens.
