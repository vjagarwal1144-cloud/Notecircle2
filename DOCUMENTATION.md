# NoteCircle — Complete Technical & Architecture Documentation

## 1. Core Philosophy & Product Identity

> *"Private by default. Temporary by design. Meaningful by choice."*  
> *"Your people. Your notes. Your private space."*

NoteCircle is a private social notebook and communication platform created exclusively for trusted relationships. It directly reimagines and replaces traditional social media apps (such as Instagram, Snapchat, WhatsApp, and Discord) by eliminating toxic engagement patterns:
- **No public feeds, no algorithms, and no discovery tabs.**
- **No follower counts, vanity metrics, or popularity leaderboards.**
- **No artificial streak anxiety or deceptive read receipts.**
- **Strictly permissioned audiences**: Every single note, thought bubble, or message is shared only with people explicitly chosen by the author (*Only Me*, *Close Friends*, *Circle Only*, or *All Connections*).

---

## 2. Cute Pastel & Neo-Brutalist Visual Design System

NoteCircle combines **Material 3**, **Soft Glassmorphism**, and **Playful Neo-Brutalism** with cute stickers and tactile cards:

### Color Palette Tokens
| Token | Hex / Spec | Purpose |
|---|---|---|
| **Warm Background** | `#FFF9F6` | Cozy, welcoming ambient canvas in Light Mode |
| **Primary Ink** | `#292438` | High-contrast text & neo-brutalist border ink |
| **Strawberry Pink** | `#FFE2E8` | Romantic notes, gratitude badges, and highlights |
| **Lavender Dreams** | `#E9E2FF` | Whisper notes, reflection tags, and companions |
| **Mint Garden** | `#DDF7E8` | Growth tracker, verified badges, and safety check-ins |
| **Peach Sunset** | `#FFE8D6` | Focus rooms, thought bubbles, and invitations |
| **Sky Blue** | `#DDF1FF` | Circle workspaces, polls, and calendar events |
| **Butter Yellow** | `#FFF1B8` | Washi tape stickers, amber accents, and pinned items |
| **Amber Accent** | `#FFBF00` | Primary interactive buttons, FAB, and active tabs |
| **Dark Background** | `#15131D` | Deliberately designed high-contrast dark theme canvas |
| **Dark Surface** | `#211E2B` | Elevated dark surface cards with crisp borders |

### Tactile Neo-Brutalist Components
- **`NeoCard`**: Rounded corners (20–24 dp), 2 dp solid border (`PrimaryInk`), subtle offset dropshadow, and optional decorative washi-tape headers (`tapeText`).
- **`NeoButton`**: Bouncy, tactile button with physical pressed elevation and high-contrast labels.
- **`NeoBadge`**: Pill-shaped pastel tags for categorizing notes and displaying audiences.
- **`NeoAvatar`**: Circle/squircle profile avatars with companion emoji overlays.
- **Typography**: Local Google Fonts `Plus Jakarta Sans` for clean body text and `Outfit` for playful, bold headers.

---

## 3. Persistent Material 3 Theme Switcher (Jetpack DataStore)

NoteCircle features a fully reactive Light / Dark theme system:
- **Architecture**: Powered by `androidx.datastore.preferences.core` via `ThemePreferences.kt`.
- **Persistence**: User selection (`true` for Dark Mode, `false` for Light Mode) is saved immediately to on-device DataStore storage and recalled on app launch.
- **UI Control**: Located in **Settings & Privacy** (`SettingsScreen.kt`) with both an interactive Material 3 Switch and dedicated tactile buttons:
  - ☀️ **Light Pastel** (Warm Cream `#FFF9F6`)
  - 🌙 **Dark Neo-Brutalist** (Deep Midnight `#15131D`)
- **Accessibility**: Preserves high-contrast borders and readable ink across all UI screens in both modes.

---

## 4. Note Tagging & Categorization System

Every private note can be labeled and filtered:
- **Supported Tags**:
  - `Personal` 🏠 — Private thoughts and daily journal reflections.
  - `Work` 💼 — Projects, tasks, and meeting agendas.
  - `Ideas` 💡 — Creative sparks, brainstorming, and future concepts.
  - `Study` 📚 — Flashcards, lecture summaries, and study sessions.
  - `Travel` ✈️ — Trip itineraries, packing checklists, and flight notes.
  - `Gratitude` 🌸 — Daily gratitude entries and peaceful reflections.
- **Tag Selection**: Available when composing notes in `CreateNoteSheet` and in `PrivateVaultScreen`.
- **Filtering**: `NotesScreen` displays a horizontal scrollable chip bar (`All`, `Personal`, `Work`, `Ideas`, `Study`, `Travel`, `Gratitude`). Selecting any tag instantly filters notes with responsive animations.

---

## 5. Private Vault with PIN Setup & Local AES-256 Storage

The **Private Vault** (`PrivateVaultScreen.kt`) provides an isolated, encrypted safe for your most sensitive thoughts:
- **First-Time Setup Flow**:
  1. If no custom PIN has been configured, the user is presented with a **Create Master PIN** screen.
  2. The user enters a 4-digit PIN and confirms it in a second field.
  3. Clicking **SAVE PIN & UNLOCK VAULT 🔒** securely sets the PIN and immediately unlocks the vault.
- **Unlock Flow**:
  - Returning users enter their 4-digit PIN.
  - Invalid PINs trigger an immediate shake/error feedback banner.
  - Users can tap **Reset / Set New PIN** at any time.
- **Vault Features**:
  - Filter vault notes by tag (`Personal`, `Work`, `Ideas`, etc.).
  - Compose new vault notes directly inside the encrypted container.
  - Delete individual vault notes with confirmation.
  - Instant one-tap **Lock Vault** action.

---

## 6. Complete Inventory of All 55 Features

### Category A — Cute Personalization
1. **Mood Garden** — Interactive visual garden where emotional statuses (*Blooming* 🌸, *Cozy* ☕, *Grounded* 🌿, *Dreamy* 🌙, etc.) can be planted quietly.
2. **Cute Digital Companions** — 6 gentle pets (*Mochi* 🐱, *Boba* 🐻, *Pippin* 🐰, *Matcha* 🐸, *Nimbus* ☁️, *Biscuit* 🐶) offering supportive quotes and sitting on the app header.
3. **Living Themes** — 6 coordinated pastel palettes (*Strawberry Milk*, *Lavender Dreams*, *Matcha Garden*, *Peach Sunset*, *Cloud Nine*, *Midnight Galaxy*).
4. **Animated Reactions** — Bouncy emoji reactions (*❤️*, *✨*, *☕*, *🙌*, *🌸*, *💡*) with reduced-motion support.
5. **Personalization Studio** — Dedicated screen (`PersonalizationStudioScreen`) for customizing companions, themes, and haptics.

### Category B — Private Notes & Memories
6. **Whisper Notes** — Ephemeral notes with customizable auto-expiration (1h, 3h, 6h, 12h, 24h, 3d, 1w, Never).
7. **Memory Capsules** — Time-locked notes scheduled for future unlock dates with sealed gift badges.
8. **Thought Bubbles** — Quick floating status messages displayed in a cute cloud above the user's avatar.
9. **Private Journaling** — "Only Me" locked diary tab with tag filtering and search.
10. **Memory Lane** — Historical timeline organized by date, circle, and tags.
11. **Gratitude Jar** — Daily gratitude notes with dedicated jar overview and review.
12. **Future Self** — Letters to your future self scheduled for distant opening dates.

### Category C — Friendships & Private Circles
13. **Circle Rituals** — Daily recurring prompts (*"What gave you peace today?"*) and shared gratitude check-ins.
14. **Secret Messages** — Scheduled private encrypted messages.
15. **Shared Scrapbook** — Visual cards, photos, and notes inside Circle Workspaces.
16. **Friendship Quests** — Wholesome collaborative activities (*"Send a voice note"*, *"Share tea moment"*) awarding cute badges.
17. **Voice Postcards** — Voice notes with duration badges, waveform previews, and postcard stamps.
18. **Shared Calendar** — Circle calendar with dates, meetups, birthdays, and reminders.

### Category D — Advanced Chat & Collaboration
19. **Personalized Chat Rooms** — E2EE chat with custom pastel wallpapers and safety fingerprints.
20. **Smart Message Collections** — Bookmarking encrypted messages into named folders.
21. **Collaborative Notes** — Two-way editable notes with contributor tracking.
22. **Voice-to-Note** — Audio note transcription capability.
23. **Private Chat Search** — Encrypted keyword search across authorized conversation history.

### Category E — Privacy & Security
24. **Audience Preview** — Live verified modal displaying exact recipient permissions before publication.
25. **Private Vault** — Dedicated PIN-protected space (`PrivateVaultScreen`) with local AES-256 storage.
26. **Privacy Dashboard** — Active session list, device information, safety numbers, data export, and account deletion.
27. **Automatic Cleanup** — Retention policies and automatic expiry purging.
28. **Private Notifications** — Quiet hours toggle and hidden lock-screen message previews.

### Category F — Productivity & Personal Growth
29. **Cozy Focus Rooms** — Circular Pomodoro countdown timer (15, 25, 45, 60m) with ambient modes (*Rainy Cafe ☕*, *Quiet Library 📚*, *Starlit Night 🌙*, *Zen Garden 🌿*).
30. **Personal Growth Garden** — Visual flower blossoms awarded as users complete self-defined activities without penalties.

### Category G — AI & Intelligent Organization
31. **NoteCircle AI Companion** — Optional on-device assistant for rewriting, polishing, and summarizing selected notes.
32. **Connected Thoughts** — Interactive tag knowledge map linking reflections and journal logs.
33. **Automatic Note Organization** — Automatic tag, mood, and category suggestion engine.
34. **Conversation-to-Action** — Quick draft task and checklist conversion.
35. **Talk to Your Notes** — Voice quick-capture command.

### Category H — Collaboration & Group Decisions
36. **Two-Way Notes** — Shared notes inside Circle Workspaces.
37. **Decision Cards** — Interactive poll cards with options, live voting tallies, and closing deadlines.
38. **Follow-Up Threads** — Threaded discussion drawers on whisper notes and decisions.
39. **Temporary Collaboration Spaces** — Ephemeral Circles with expiration timeframes.
40. **Shareable Mini-Collections** — Bundled notes into pocket collections.

### Category I — Advanced Privacy Controls
41. **Granular Sharing Permissions** — `AudienceType` selector (*All Connections*, *Close Friends*, *Circle Only*, *Only Me*).
42. **Content Access History** — Transparent audit trail of note views and edits.
43. **Privacy Checkup** — Verification wizard for safety numbers and connection status.
44. **Offline Privacy Mode** — Local-first Room Database caching with immediate offline capability.
45. **Emergency Account Recovery** — Identity safety numbers and recovery credentials.

### Category J — Productivity & Knowledge Management
46. **Universal Capture** — Center '+' bottom action button with category selector.
47. **Personal Command Center** — Unified dashboard (`CommandCenterScreen`) with growth flowers and active statistics.
48. **Search Across Everything** — Unified search across usernames, notes, and messages.
49. **Contextual Reminders** — Scheduled note reminder timestamps.
50. **Personal Knowledge Insights** — Private metrics detailing notes, capsules, and focus sessions.

### Category K — Everyday Utilities & User Control
51. **Personal Information Cards** — Shareable mini cards (e.g. coffee order, birthday wishlist, study setup).
52. **Smart Forms for Circles** — RSVP questions and group decision forms.
53. **Study Workspace** — Interactive flashcard decks (`StudyWorkspaceScreen`) with prompt/answer flips and mastery toggles.
54. **Personal Reference Library** — Bookmarked quotes and comforted thoughts.
55. **Account Portability** — Complete JSON data export and irreversible account wipe.

---

## 7. Dual Database Architecture: Offline-First Room & Cloud Supabase Sync

NoteCircle implements an **Offline-First Clean Architecture**:
1. **Local Master (Android Room Database v3)**:
   - All writes, edits, and reads execute immediately against the local SQLite Room database, guaranteeing 100% functionality offline without network lag or internet dependency.
   - DAOs: `UserDao`, `NoteDao`, `CircleDao`, `ConversationDao`, `MessageDao`, `NotificationDao`, `StudyFlashcardDao`, `DecisionPollDao`, `CalendarEventDao`, `CircleRitualDao`.
2. **Cloud Mirror (Supabase PostgreSQL via REST & RLS)**:
   - `SupabaseSyncManager` watches repository operations and background-syncs users, notes, and circles to Supabase.
   - Handled via `SupabaseApiService` using Retrofit and OkHttp with `apikey` and `Bearer` headers.
   - If offline or unconfigured, it fails gracefully without blocking the user (`CloudSyncState.OFFLINE`).

### Supabase Schema & Row-Level Security (RLS)
The database migration script is located at `supabase/migrations/20261023_init_notecircle.sql`:
- **`profiles` table**: Stores user identity, display name, avatar color, status emoji, mood, companion, and vault PIN.
- **`notes` table**: Stores whisper notes, tags, audience permissions, capsules, gratitude entries, and expiration timestamps.
- **`circles` table**: Stores private collaboration spaces, descriptions, and creator IDs.
- **`messages` table**: Stores encrypted chat payloads and sender/conversation IDs.
- **RLS Policies**: Zero-trust security ensures users can only read and write data they own or are explicitly authorized to access.

---

## 8. Directory & File Structure

```
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml          # Permissions, Application, & Widget Receivers
│   │   │   ├── java/com/example/
│   │   │   │   ├── MainActivity.kt          # Main Navigation Host & Screen Controller
│   │   │   │   ├── NoteCircleApplication.kt # Room Database & Singleton Initializer
│   │   │   │   ├── data/
│   │   │   │   │   ├── local/
│   │   │   │   │   │   ├── AppDatabase.kt        # Room Database v3 Definition
│   │   │   │   │   │   ├── ThemePreferences.kt   # DataStore Theme Persistence Manager
│   │   │   │   │   │   ├── dao/                  # 10 Room Data Access Objects
│   │   │   │   │   │   └── entity/               # Data Entities (User, Note, Circle, etc.)
│   │   │   │   │   ├── repository/
│   │   │   │   │   │   └── NoteCircleRepository.kt # Reactive Data Repository
│   │   │   │   │   ├── security/
│   │   │   │   │   │   └── E2EEEngine.kt         # AES-GCM (256-bit) Encryption Engine
│   │   │   │   │   └── supabase/
│   │   │   │   │       ├── SupabaseApiService.kt   # PostgREST Retrofit Service
│   │   │   │   │       ├── SupabaseClient.kt       # OkHttp Client with Auth Headers
│   │   │   │   │       ├── SupabaseSyncManager.kt  # Two-Way Cloud Sync Engine
│   │   │   │   │       └── model/
│   │   │   │   │           └── SupabaseModels.kt   # Moshi JSON DTOs
│   │   │   │   ├── ui/
│   │   │   │   │   ├── components/          # Reusable NeoCard, NeoButton, NeoAvatar, Badges
│   │   │   │   │   ├── dialogs/             # NoteDialogs (CreateNoteSheet, AudiencePicker)
│   │   │   │   │   ├── screens/             # 15+ Distinct Application Screens
│   │   │   │   │   │   ├── HomeScreen.kt
│   │   │   │   │   │   ├── NotesScreen.kt
│   │   │   │   │   │   ├── PrivateVaultScreen.kt
│   │   │   │   │   │   ├── SettingsAndAuthScreens.kt
│   │   │   │   │   │   ├── ChatAndCircleScreens.kt
│   │   │   │   │   │   ├── PersonalizationStudioScreen.kt
│   │   │   │   │   │   ├── FocusRoomAndStudyScreens.kt
│   │   │   │   │   │   └── CommandCenterScreen.kt
│   │   │   │   │   ├── theme/               # Color, Theme, Type tokens
│   │   │   │   │   └── viewmodel/
│   │   │   │   │       └── NoteCircleViewModel.kt # Unified Architecture StateFlow Controller
│   │   │   │   └── widget/
│   │   │   │       └── NoteCircleWidgets.kt # 5 Native Android App Widgets
│   │   │   └── res/
│   │   │       ├── font/                    # Local TTF fonts (Plus Jakarta Sans & Outfit)
│   │   │       └── values/                  # strings.xml, colors.xml
│   │   └── test/
│   │       └── java/com/example/            # Unit & Robolectric Tests
│   └── build.gradle.kts                     # App Build Config & Dependencies
├── supabase/
│   └── migrations/
│       └── 20261023_init_notecircle.sql     # Supabase PostgreSQL Schema & RLS Policies
├── .github/
│   └── workflows/
│       └── android.yml                      # GitHub Actions APK Build Pipeline
├── .env.example                             # Environment Variables Template
└── DOCUMENTATION.md                         # This Master Technical Reference
```

---

## 9. Environment Variables & Credentials Setup

Credentials must be stored securely using the **Secrets panel in AI Studio** or in `.env`:

```properties
# Gemini API Key (for NoteCircle AI Companion)
GEMINI_API_KEY=YOUR_GEMINI_API_KEY

# Supabase Database Configuration
SUPABASE_URL=https://your-project-id.supabase.co
SUPABASE_ANON_KEY=your-supabase-anon-key-here
```

The app safely operates with offline-fallback if Supabase keys are not yet configured.

---

## 10. Building the Android Application & GitHub Actions

### Local Gradle Build Commands
```bash
# Verify compilation
gradle :app:compileDebugKotlin

# Run local JVM unit tests
gradle :app:testDebugUnitTest

# Assemble debug APK
gradle :app:assembleDebug
```
The resulting debug APK is generated at:
`app/build/outputs/apk/debug/app-debug.apk`

### Automated GitHub Actions CI/CD
The repository includes `.github/workflows/android.yml` which automatically:
1. Sets up JDK 17 with Temurin distribution.
2. Caches Gradle build dependencies.
3. Injects default `.env.example` configurations.
4. Executes unit tests (`gradle :app:testDebugUnitTest`).
5. Assembles `app-debug.apk` and uploads it as a downloadable GitHub Actions artifact.
