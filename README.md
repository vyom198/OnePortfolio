# One Portfolio 📊

## Inspiration
The inspiration for **One Portfolio** came directly from a challenge posed by Josh @VisualFaktory regarding the growing fragmentation of personal wealth. In today’s economy, investors don't just hold stocks; they manage a complex mix of digital assets, traditional bank accounts, and physical investments like real estate or gold. 

We realized that while many "wealth trackers" exist for the stock market, there is no single, unified **"Source of Truth"** where a user can log everything from a crypto wallet to a physical property deed. We set out to build a bridge between the digital and the physical, ensuring that no asset is forgotten and every investment is tracked in one place.

## What it does
One Portfolio is a comprehensive wealth management mobile app that empowers users to take control of their entire net worth:

* **Unified Logging:** Track diverse assets including stocks, crypto, real estate, and physical commodities (gold, collectibles)and fixed income assets.
* **Smart Reminders:** Set automated alerts for maturity dates, mortgage payments, or dividend payouts so nothing slips through the cracks.
* **Asset Management:** View a high-level breakdown of net worth and real-time asset allocation through intuitive visualizations. 
* **Physical Asset Tracking:** Unlike standard apps that rely solely on live tickers, we provide a dedicated interface for managing physical assets that don't have a live API.

## How I built it
I developed the core of the application using a modern tech stack focused on security, scalability, and real-time updates:
* **Frontend:** Built with **Kotlin** and **Jetpack Compose**, ensuring a modern, fluid, and reactive declarative UI.
* **Local Database:** Implemented **Room Database** to ensure user data remains on-device, providing high security and a robust **offline-first** feature.
* **Backend & Cloud:** Utilized **Firebase** for secure cloud storage, user authentication, and profile synchronization.
* **AI Logic:** Developed custom logic to calculate **portfolio risk analysis** and **diversification scores**, helping users make informed financial decisions.
* **APIs:** Integrated the **Finnhub API** for real-time stock and crypto data, and a specialized **Gold API** for live precious metal pricing.
* **Monetization:** Integrated **RevenueCat** to seamlessly handle in-app purchases .

## Challenges we ran into
* **Interactive Notification Logic:** Developing the "Actionable Notification" system was a major hurdle. We had to build logic where a user could click "Yes" or "No" on a reminder (e.g., "Did you pay your mortgage installment?") and have the app automatically reduce the liability balance in the Room database without the user even opening the app.
* **API Constraints:** We faced significant restrictions with free-tier financial APIs. Most lacked **WebSocket** support for real-time streaming, forcing us to optimize a polling-based system with standard REST APIs while maintaining data accuracy.
* **Data Normalization:** Combining dynamic "live" data with static "physical" data into a single, cohesive net-worth visualization required complex mathematical transformations.

## Accomplishments that we're proud of
* **The "All-In-One" View:** Successfully creating a unified dashboard that visualizes a user's total net worth across both digital and physical realms, providing a holistic financial picture that few apps offer.
* **The Reminder Engine:** Building a robust, interactive notification system. We successfully implemented a logic flow that handles recurring physical asset payments, allowing users to update their liability balances directly through notification actions.
* **AI-Driven Financial Insights:** We are particularly proud of our recommendation engine. By building custom logic to analyze portfolio diversification and risk, we provide users with actionable AI-generated insights to help them optimize their asset allocation and minimize exposure.


## What we learned
* **AI & Cloud Logic:** Learned how to implement intelligent portfolio analysis and risk assessment using custom logic within the Firebase ecosystem.
* **Monetization Mastery:** Gained hands-on experience integrating **RevenueCat** to manage complex in-app purchase flows and subscription states.
* **Precision Scheduling:** Mastered the Android **AlarmManager** to trigger high-priority, interactive reminders for physical asset management.
* **Offline-First Data:** Developed a deep understanding of syncing local **Room** databases with cloud storage while maintaining a high-performance UI.

## What's next for One Portfolio
* **Top Performers Gallery:** A dedicated section to highlight a user's best-performing assets and historical winners at a glance.
* **Family Wealth Sharing:** Support for "Family Accounts" where members can securely log and view collective assets in a shared dashboard.
* **Global Markets & Multi-Currency:** Expanding support to all international stock exchanges and implementing real-time multi-currency conversion.
* **WebSocket Integration:** Upgrading from REST APIs to WebSockets for true, second-by-second live price streaming.
* **Document Vault:** Adding high-security encrypted storage for uploading PDFs of land deeds and insurance papers directly to asset logs.


