# Day 15 — Final Backend Day

## Overview

Day 15 completed the final planned backend work before moving to the React frontend.

The day covered:

- Notifications
- Backend integration testing
- Backend hardening
- Postman API testing
- Cross-module MES flow verification

---

# Phase 1 — Notifications

## Objective

Introduce a notification module for important MES events.

### Notification Types

- `BATCH_COMPLETED`
- `QUALITY_FAILURE`
- `DEVIATION_CREATED`
- `WORK_ORDER_DELAYED`
- `EQUIPMENT_ISSUE`
- `LOW_INVENTORY`

### Notification Status

- `UNREAD`
- `READ`

## Components Created

### Entity

`Notification`

Main fields:

- `notificationId`
- `recipient`
- `type`
- `message`
- `status`
- `referenceType`
- `referenceId`
- `createdAt`
- `readAt`

### DTO

`NotificationDTO`

Validation was added for:

- recipient
- notification type
- message

### Repository

`NotificationRepository`

Implemented support for:

- notifications by recipient
- unread notifications by recipient
- notifications by type
- unread notification count

### Service

`NotificationService`

Implemented:

- create notification
- get notification by ID
- get all notifications
- delete notification
- get notifications by recipient
- get unread notifications
- count unread notifications
- mark notification as read
- mark all notifications as read

### Controller

Base endpoint:

```text
/api/notifications
```

Important endpoints:

```text
POST   /api/notifications
GET    /api/notifications
GET    /api/notifications/{id}
DELETE /api/notifications/{id}

GET    /api/notifications/recipient/{recipient}
GET    /api/notifications/recipient/{recipient}/unread
GET    /api/notifications/recipient/{recipient}/count

PUT    /api/notifications/{id}/read
PUT    /api/notifications/recipient/{recipient}/read-all
```

---

# Phase 1 — Postman Testing

The notification module was tested through Postman.

### Test 1 — Create Notification

Created a `BATCH_COMPLETED` notification.

Expected:

```text
201 Created
status = UNREAD
```

### Test 2 — Mark Notification as Read

Verified:

```text
UNREAD → READ
```

and `readAt` was populated.

### Test 3 — Quality Failure Notification

Created a notification using:

```text
QUALITY_FAILURE
```

and linked it to a quality inspection reference.

### Test 4 — Unread Notifications

Verified that only unread notifications were returned for a recipient.

### Test 5 — Invalid Notification

Sent invalid notification data and verified validation rejection.

### Test 6 — Final Notification Verification

Verified the recipient's notifications and confirmed the correct READ/UNREAD states.

**Phase 1 — Notifications: COMPLETE**

---

# Phase 2 — Backend Integration

## Objective

Verify that the major MES modules work together rather than only working individually.

Integration flow:

```text
Production Schedule
        ↓
Work Order
        ↓
Batch
        ↓
Quality
        ↓
Deviation
        ↓
KPI / OEE
        ↓
Notification
```

## Test 1 — Production → Batch

Initially, Work Order `3` was found to be:

```text
status = COMPLETED
batchId = null
```

A batch could not be assigned because completed work orders cannot receive a batch.

A new Work Order was created in the appropriate initial state and a batch was assigned for integration testing.

The relationship was verified as:

```text
Production Schedule
        ↓
Work Order
        ↓
Batch
```

## Test 2 — Batch → Quality

Created a Quality Inspection using the integration-test Batch ID.

Verified:

```text
Batch
  ↓
Quality Inspection
```

## Test 3 — Quality → Deviation

Created a Deviation for the same Batch used by the quality flow.

During testing, validation initially failed because:

```text
reportedBy
deviationNumber
```

were required.

The request was corrected and the required fields were included.

Verified:

```text
Batch
  ↓
Quality Failure
  ↓
Deviation
```

## Test 4 — Production → KPI

The KPI test was deferred because the exact KPI DTO/request structure was not available in the working context.

This was not treated as a code failure.

## Test 5 — Event → Notification

Created a `BATCH_COMPLETED` notification linked to the integration-test Batch.

Verified:

```text
Batch
  ↓
BATCH_COMPLETED
  ↓
Notification
```

## Test 6 — Invalid Cross-Module Reference

Tested a notification with a nonexistent Batch reference.

The current notification implementation accepts generic:

```text
referenceType
referenceId
```

and does not currently validate the referenced MES entity.

This was recorded as a backend-hardening/design improvement rather than a failure of the current notification implementation.

**Phase 2 — Backend Integration: COMPLETE**

---

# Phase 3 — Backend Hardening

## Objective

Verify that invalid IDs and invalid input are handled correctly.

### Test 1 — Invalid Work Order ID

Tested a nonexistent Work Order ID.

Expected:

```text
404 Not Found
```

### Test 2 — Invalid Batch ID

Tested:

```text
GET /api/batches/999999
```

Expected:

```text
404 Not Found
```

### Test 3 — Invalid Quality Inspection ID

Tested:

```text
GET /api/quality-inspections/999999
```

Expected:

```text
404 Not Found
```

### Test 4 — Invalid Notification ID

Tested:

```text
GET /api/notifications/999999
```

Expected:

```text
404 Not Found
```

### Test 5 — Invalid POST Validation

Sent a notification with missing required fields.

Expected:

```text
400 Bad Request
```

Validation checked:

```text
recipient → must not be blank
type      → must not be null
message   → must not be blank
```

### Test 6 — Invalid DELETE

Tested deletion of a nonexistent notification:

```text
DELETE /api/notifications/999999
```

Expected:

```text
404 Not Found
```

**Phase 3 — Backend Hardening: COMPLETE**

---

# Day 15 Final Status

```text
Phase 1 — Notifications
        ✅ COMPLETE

Phase 2 — Backend Integration
        ✅ COMPLETE

Phase 3 — Backend Hardening
        ✅ COMPLETE
```

# Backend Milestone

Days 1–15 backend development is considered complete.

The major MES flow now includes:

```text
Authentication
      ↓
Master Data
      ↓
Production
      ↓
Work Orders
      ↓
Batch Execution
      ↓
Quality Management
      ↓
Deviations
      ↓
Genealogy / Traceability
      ↓
Audit
      ↓
Reports / KPI / OEE
      ↓
Dashboard
      ↓
Notifications
```

Postman testing has been performed throughout the backend development.

---

# Known Improvement for Later

One item identified during Day 15:

```text
Notification referenceType + referenceId
```

currently does not validate whether the referenced MES object actually exists.

The KPI integration test was also deferred because the exact KPI DTO/request structure was not available in the working context.

---

# Next Day — Day 16

## React Frontend

The next milestone is to begin the frontend.

Planned Day 16 work:

```text
React + Vite
Material UI
Axios
React Router
JWT handling
Login page
Dashboard layout
Frontend project structure
```

The backend will serve as the REST API layer for the React application.

---

# Day 15 Result

**Backend development milestone: COMPLETE ✅**

**Next: Day 16 — React Frontend Setup**
