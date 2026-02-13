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







