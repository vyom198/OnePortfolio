# One Portfolio 📊

## Introduction
**One Portfolio** is a high-performance, native wealth management platform designed to solve the problem of fragmented personal finance. In a world where investors juggle stocks on one app, crypto on another, and physical deeds in a folder, One Portfolio serves as the single **"Source of Truth."** It bridges the gap between digital and physical investments, providing a unified dashboard for everything from real estate and gold to traditional equities and digital assets.

## What it does
One Portfolio empowers users to take total control of their net worth through a suite of intelligent features:

* **Unified Logging:** Track diverse assets including stocks, crypto, real estate, physical commodities (gold, collectibles), and fixed-income instruments in one place.
* **Interactive Smart Reminders:** Set automated alerts for maturity dates or dividend payouts. For liabilities like mortgages, users can update balances directly from notifications using "Yes/No" action logic.
* **AI Risk Analysis:** Employs custom intelligent logic to provide a deep dive into portfolio risk, volatility, and diversification levels.
* **Asset Management:** High-level breakdown of net worth and real-time asset allocation through intuitive, reactive visualizations.
* **Physical Asset Tracking:** A dedicated interface for managing non-liquid assets that don't have a live API, supporting manual value updates and installment tracking.

## Technical Details 
The application is engineered for security, speed, and offline reliability using a modern Android stack:

* **Frontend:** Developed with **Kotlin 1.9** and **Jetpack Compose** for a fluid, declarative UI experience.
* **Architecture:** Implements **MVVM Clean Architecture**, ensuring a strict separation between the UI, business logic (AI Engine), and data sources.

* **Local Database:** Utilizes **Room Database** for high-security, on-device data persistence, enabling a robust **offline-first** experience.
* **Cloud & Auth:** Leverages **Firebase** for secure user authentication, profile synchronization, and cloud storage.
* **Data Sources:** Integrated the **Finnhub API** for stocks/crypto and a specialized **Gold API** for live precious metal pricing.
* **Monetization:** Integrated **RevenueCat** to manage "Lifetime Pro" access for AI features with secure, cached entitlement checks .

### Architecture: MVVM + Clean Architecture
* **Presentation Layer:** Developed with **Kotlin 1.9** and **Jetpack Compose** for a fluid, reactive UI. It utilizes a **Unidirectional Data Flow (UDF)** where ViewModels expose a single `StateFlow` to the UI.
* **Domain Layer:** Contains pure Kotlin **Use Cases** (e.g., `GetRiskAnalysisUseCase`). This layer is independent of the Android Framework, ensuring the core AI logic is reusable and unit-testable.
* **Data Layer:** Implements the **Repository Pattern** to coordinate between the local **Room** database and **Firebase**. Mappers are used to convert raw API DTOs into Domain Entities.

### Monetization Flow: RevenueCat Integration
I implemented a **Freemium** model with a **"Lifetime Pro"** tier handled via **RevenueCat**:
* **Entitlement Management:** We define a `pro_access` entitlement that unlocks AI-driven risk features.
* **Lifecycle Check:** The app performs a secure check via the RevenueCat SDK whenever a user attempts to access premium logic.
* **Offline Persistence:** Utilizing RevenueCat’s caching, the app maintains the user's "Pro" status during offline sessions, ensuring the local logic remains functional.

## Challenges I ran into
* **Interactive Notification Logic:** Developing an "Actionable Notification" system using **AlarmManager** and **BroadcastReceivers**. I built logic where users can confirm payments on a reminder to automatically reduce liability balances in the Room database without opening the app.
* **API Constraints:** Navigating the limitations of free-tier financial APIs. To compensate for the lack of WebSockets, I optimized a lifecycle-aware polling system to maintain data accuracy while preserving battery life.
* **Data Normalization:** Synthesizing dynamic "live" price data with static "physical" data into a single, cohesive net-worth visualization required complex mathematical transformations.

## UI
<img src="https://github.com/user-attachments/assets/8777a9e8-1013-428d-b324-af213a751fc6" width="200">

<img src="https://github.com/user-attachments/assets/400615cd-af49-4bc9-bd75-097fd0e4d2de" width="200">

<img src="https://github.com/user-attachments/assets/62c7274e-b860-46f1-9456-73f9f9224e3d" width="200">

<img src="https://github.com/user-attachments/assets/6fe52e47-58c2-49b9-b16e-3baf96cdceaf" width="200">

<img src="https://github.com/user-attachments/assets/49806b58-16e5-44ad-96c9-c03563d2d9fb" width="200">

<img src="https://github.com/user-attachments/assets/ec0fcf99-0434-4e99-97c5-56163bff24a8" width="200">

<img src="https://github.com/user-attachments/assets/9756acc6-6f81-4c4d-9318-a72b0660b34f" width="200">

<img src="https://github.com/user-attachments/assets/90ffd57f-498b-4220-8b94-9e5985020da7" width="200">

<img src="https://github.com/user-attachments/assets/9756acc6-6f81-4c4d-9318-a72b0660b34f" width="200">

<img src="https://github.com/user-attachments/assets/4b505740-63bf-4b15-adf8-66583a20a5fd" width="200">
















