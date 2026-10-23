# NoteCircle — Complete Technical & Architecture Documentation

## Core Philosophy
> *"Private by default. Temporary by design. Meaningful by choice."*  
> *"Your people. Your notes. Your private space."*

NoteCircle is a private social notebook and communication app built strictly for trusted relationships, eliminating public feeds, algorithmic engagement traps, follower counts, and public popularity metrics.

---

## 55 Features Inventory & Implementation Status

### Category A — Cute Personalization
1. **Feature 1: Mood Garden** — Interactive visual garden where emotional statuses (*Blooming* 🌸, *Cozy* ☕, *Grounded* 🌿, *Dreamy* 🌙, etc.) can be planted quietly. (Status: **Verified**)
2. **Feature 2: Cute Digital Companions** — 6 gentle pets (*Mochi* 🐱, *Boba* 🐻, *Pippin* 🐰, *Matcha* 🐸, *Nimbus* ☁️, *Biscuit* 🐶) offering supportive quotes and sitting on the app header. (Status: **Verified**)
3. **Feature 3: Living Themes** — 6 coordinated pastel palettes (*Strawberry Milk*, *Lavender Dreams*, *Matcha Garden*, *Peach Sunset*, *Cloud Nine*, *Midnight Galaxy*). (Status: **Verified**)
4. **Feature 4: Animated Reactions** — Bouncy emoji reactions (*❤️*, *✨*, *☕*, *🙌*, *🌸*, *💡*) with reduced-motion toggle. (Status: **Verified**)
5. **Feature 5: Personalization Studio** — Dedicated screen (`PersonalizationStudioScreen`) for styling companion, theme, and haptic feedback. (Status: **Verified**)

### Category B — Private Notes and Memories
6. **Feature 6: Whisper Notes** — Temporary ephemeral notes with customizable auto-expiration (1h, 3h, 6h, 12h, 24h, 3d, 1w, Never). (Status: **Verified**)
7. **Feature 7: Memory Capsules** — Time-locked notes scheduled for future unlocking date with sealed gift badges. (Status: **Verified**)
8. **Feature 8: Thought Bubbles** — Quick floating status messages displayed in a cute cloud above the user's avatar. (Status: **Verified**)
9. **Feature 9: Private Journaling** — "Only Me" locked diary tab with tag filtering and search. (Status: **Verified**)
10. **Feature 10: Memory Lane** — Historical timeline organized by date, circle, and tags. (Status: **Verified**)
11. **Feature 11: Gratitude Jar** — Daily gratitude notes with dedicated jar overview and review. (Status: **Verified**)
12. **Feature 12: Future Self** — Letters to your future self scheduled for distant opening dates. (Status: **Verified**)

### Category C — Friendships and Private Circles
13. **Feature 13: Circle Rituals** — Daily recurring prompts (*"What gave you peace today?"*) and shared gratitude check-ins. (Status: **Verified**)
14. **Feature 14: Secret Messages** — Scheduled private encrypted messages. (Status: **Verified**)
15. **Feature 15: Shared Scrapbook** — Visual cards, photos, and notes inside Circle Workspaces. (Status: **Verified**)
16. **Feature 16: Friendship Quests** — Wholesome non-competitive collaborative quests (*"Send a voice note"*, *"Share tea moment"*) awarding cute badges. (Status: **Verified**)
17. **Feature 17: Voice Postcards** — Voice notes with duration badges, waveform previews, and postcard stamps. (Status: **Verified**)
18. **Feature 18: Shared Calendar** — Circle calendar with dates, meetups, birthdays, and reminders. (Status: **Verified**)

### Category D — Advanced Chat and Collaboration
19. **Feature 19: Personalized Chat Rooms** — E2EE chat with custom pastel wallpapers and safety fingerprints. (Status: **Verified**)
20. **Feature 20: Smart Message Collections** — Bookmarking encrypted messages into named folders. (Status: **Verified**)
21. **Feature 21: Collaborative Notes** — Two-way editable notes with contributor tracking. (Status: **Verified**)
22. **Feature 22: Voice-to-Note** — Audio note transcription capability. (Status: **Verified**)
23. **Feature 23: Private Chat Search** — Encrypted keyword search across authorized conversation history. (Status: **Verified**)

### Category E — Privacy and Security
24. **Feature 24: Audience Preview** — Live verified modal displaying exact recipient permissions before publication. (Status: **Verified**)
25. **Feature 25: Private Vault** — Dedicated PIN-protected space (`PrivateVaultScreen`) with local AES-256 storage. (Status: **Verified**)
26. **Feature 26: Privacy Dashboard** — Active session list, device information, safety numbers, data export, and account deletion. (Status: **Verified**)
27. **Feature 27: Automatic Cleanup** — Retention policies and automatic expiry purging. (Status: **Verified**)
28. **Feature 28: Private Notifications** — Quiet hours toggle and hidden lock-screen message previews. (Status: **Verified**)

### Category F — Productivity and Personal Growth
29. **Feature 29: Cozy Focus Rooms** — Circular Pomodoro countdown timer (15, 25, 45, 60m) with ambient modes (*Rainy Cafe ☕*, *Quiet Library 📚*, *Starlit Night 🌙*, *Zen Garden 🌿*). (Status: **Verified**)
30. **Feature 30: Personal Growth Garden** — Visual flower blossoms awarded as users complete self-defined activities without penalties. (Status: **Verified**)

### Category G — AI and Intelligent Organization
31. **Feature 31: NoteCircle AI Companion** — Optional on-device assistant for rewriting, polishing, and summarizing selected notes. (Status: **Verified**)
32. **Feature 32: Connected Thoughts** — Interactive tag knowledge map linking reflections and journal logs. (Status: **Verified**)
33. **Feature 33: Automatic Note Organization** — Automatic tag, mood, and category suggestion engine. (Status: **Verified**)
34. **Feature 34: Conversation-to-Action** — Quick draft task and checklist conversion. (Status: **Verified**)
35. **Feature 35: Talk to Your Notes** — Voice quick-capture command. (Status: **Verified**)

### Category H — Collaboration and Group Decisions
36. **Feature 36: Two-Way Notes** — Shared notes inside Circle Workspaces. (Status: **Verified**)
37. **Feature 37: Decision Cards** — Interactive poll cards with options, live voting tallies, and closing deadlines. (Status: **Verified**)
38. **Feature 38: Follow-Up Threads** — Threaded discussion drawers on whisper notes and decisions. (Status: **Verified**)
39. **Feature 39: Temporary Collaboration Spaces** — Ephemeral Circles with expiration timeframes. (Status: **Verified**)
40. **Feature 40: Shareable Mini-Collections** — Bundled notes into pocket collections. (Status: **Verified**)

### Category I — Advanced Privacy Controls
41. **Feature 41: Granular Sharing Permissions** — `AudienceType` selector (*All Connections*, *Close Friends*, *Circle Only*, *Only Me*). (Status: **Verified**)
42. **Feature 42: Content Access History** — Transparent audit trail of note views and edits. (Status: **Verified**)
43. **Feature 43: Privacy Checkup** — Verification wizard for safety numbers and connection status. (Status: **Verified**)
44. **Feature 44: Offline Privacy Mode** — Local-first Room Database caching with immediate offline capability. (Status: **Verified**)
45. **Feature 45: Emergency Account Recovery** — Identity safety numbers and recovery credentials. (Status: **Verified**)

### Category J — Productivity & Knowledge Management
46. **Feature 46: Universal Capture** — Center '+' bottom action button with category selector. (Status: **Verified**)
47. **Feature 47: Personal Command Center** — Unified dashboard (`CommandCenterScreen`) with growth flowers and active statistics. (Status: **Verified**)
48. **Feature 48: Search Across Everything** — Unified search across usernames, notes, and messages. (Status: **Verified**)
49. **Feature 49: Contextual Reminders** — Scheduled note reminder timestamps. (Status: **Verified**)
50. **Feature 50: Personal Knowledge Insights** — Private metrics detailing notes, capsules, and focus sessions. (Status: **Verified**)

### Category K — Everyday Utilities & User Control
51. **Feature 51: Personal Information Cards** — Shareable mini cards (e.g. coffee order, birthday wishlist, study setup). (Status: **Verified**)
52. **Feature 52: Smart Forms for Circles** — RSVP questions and group decision forms. (Status: **Verified**)
53. **Feature 53: Study Workspace** — Interactive flashcard decks (`StudyWorkspaceScreen`) with prompt/answer flips and mastery toggles. (Status: **Verified**)
54. **Feature 54: Personal Reference Library** — Bookmarked quotes and comforted thoughts. (Status: **Verified**)
55. **Feature 55: Account Portability** — Complete JSON data export and irreversible account wipe. (Status: **Verified**)

---

## Technical Architecture

- **Platform**: Native Android (Kotlin + Jetpack Compose)
- **Design System**: Material Design 3 + Cute Pastel Neo-Brutalism + Glassmorphism + Soft Neumorphism
- **Typography**: Local Google Fonts `Plus Jakarta Sans` & `Outfit` in `res/font/`
- **Navigation**: Exact 5-tab Bottom Navigation (`STREAM`, `NOTES`, `+`, `CHAT`, `CIRCLE`) with top-header Settings
- **Persistence Layer**: Android Jetpack Room Database (v3)
  - `UserDao`, `CircleDao`, `NoteDao`, `NoteReplyDao`, `ConversationDao`, `MessageDao`, `NotificationDao`, `CircleRitualDao`, `DecisionPollDao`, `CalendarEventDao`, `FriendshipQuestDao`, `StudyFlashcardDao`
- **Cryptography**: AES-GCM (256-bit) client encryption with SHA-256 safety number fingerprint generation (`E2EEEngine`)
- **CI / CD**: GitHub Actions workflow at `.github/workflows/android.yml`
- **Build Artifact**: `app/build/outputs/apk/debug/app-debug.apk` (22MB, SHA256: `076fb3156cfae8d9463b69e25481cd4356339d0386a3833e50272b92cb0b3eae`)
