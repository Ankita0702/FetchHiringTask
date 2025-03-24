# Fetch Rewards - Mobile Coding Exercise

This is a native Android app written in **Kotlin** that fetches and displays a list of items from a remote JSON API. The list is:

- ✅ Grouped by `listId`
- ✅ Sorted **numerically** by item name (e.g., Item 2, Item 10, Item 100)
- ✅ Filters out any items with null or blank `name`

---

## 📱 Features

- Grouped view with section headers (`List ID`)
- Scrollable `RecyclerView` with clean item separation
- Follows **MVVM architecture**
- Fully unit-tested filtering and sorting logic

---

## 🛠️ Requirements

- Android Studio (latest stable)
- Java 17
- Gradle 7.5
- Tested on: **Pixel 8 (API 34)**

---

## 🧱 Architecture Overview

[ Retrofit API Service ]
      ↓
[ ItemRepository ]
      ↓
[ ItemViewModel ]
      ↓
[ RecyclerView Adapter ]

### Layer Responsibilities

| Layer                    | Responsibility                                                                                  |
|--------------------------|-------------------------------------------------------------------------------------------------|
| **ApiService**           | Fetches raw JSON data from the provided URL via Retrofit                                        |
| **ItemRepository**       | Retrieves data from the API (no filtering or sorting)                                           |
| **ItemViewModel**        | Handles all business logic: filters blank/null names, groups by `listId`, and sorts numerically |
| **RecyclerView Adapter** | Displays pre-processed grouped items without performing extra logic                             |
