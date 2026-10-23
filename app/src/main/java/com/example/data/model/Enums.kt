package com.example.data.model

enum class AvailabilityStatus(
    val title: String,
    val emoji: String,
    val description: String
) {
    AVAILABLE("Available", "🌿", "Open for a chat or coffee"),
    BUSY("Busy", "☕", "In the zone, replies delayed"),
    STUDYING("Studying", "📚", "Focusing on books & notes"),
    SLEEPING("Sleeping", "💤", "Quiet time, do not disturb"),
    TRAVELLING("Travelling", "✈️", "On the move, low battery"),
    DND("Do Not Disturb", "🔕", "Silenced notifications"),
    FAMILY_TIME("Family Time", "🏡", "Cherishing moments with home")
}

enum class MoodGarden(
    val title: String,
    val emoji: String,
    val colorHex: String,
    val vibe: String
) {
    BLOOMING("Blooming", "🌸", "#FFD1DC", "Happy, radiant & energetic"),
    COZY("Cozy", "☕", "#FFE07A", "Warm drink, comfortable silence"),
    GROUNDED("Grounded", "🌿", "#B5EAD7", "Calm, balanced & mindful"),
    DREAMY("Dreamy", "🌙", "#D8B4F8", "Night thoughts & stargazing"),
    RAINY("Rainy", "🌧️", "#A0C4FF", "Gentle slow pace, warm socks"),
    CREATIVE("Creative", "🎨", "#FFB4A2", "In the zone, making things"),
    FOCUSING("Focusing", "📚", "#E2D4F0", "Deep work session"),
    HIBERNATING("Resting", "💤", "#FFF2B2", "Low battery, recharging")
}

enum class LivingTheme(
    val title: String,
    val emoji: String,
    val primaryColorHex: String,
    val secondaryColorHex: String,
    val isDark: Boolean = false
) {
    STRAWBERRY_MILK("Strawberry Milk", "🍓", "#FFE2E8", "#FF85A1"),
    LAVENDER_DREAMS("Lavender Dreams", "🪻", "#E9E2FF", "#B388EB"),
    MATCHA_GARDEN("Matcha Garden", "🍵", "#DDF7E8", "#88D49E"),
    PEACH_SUNSET("Peach Sunset", "🍑", "#FFE8D6", "#FFB4A2"),
    CLOUD_NINE("Cloud Nine", "☁️", "#DDF1FF", "#A0C4FF"),
    MIDNIGHT_GALAXY("Midnight Galaxy", "🌌", "#15131D", "#A594F9", true)
}

enum class DigitalCompanion(
    val petName: String,
    val species: String,
    val emoji: String,
    val trait: String,
    val greeting: String
) {
    MOCHI("Mochi", "Cat", "🐱", "Warm, curious & gentle", "Purr~ Take your sweet time today! 🐾"),
    BOBA("Boba", "Bear", "🐻", "Grounding, calm & loves tea", "A quiet breath is a little victory. 🍯"),
    PIPPIN("Pippin", "Bunny", "🐰", "Playful & full of wonder", "Hop gently through your lovely notes! 🌸"),
    MATCHA("Matcha", "Frog", "🐸", "Mindful & peaceful listener", "Ribbit~ Listen to your inner calm. 🍃"),
    NIMBUS("Nimbus", "Cloud", "☁️", "Dreamy, soft & soothing", "Float freely without any pressure. ✨"),
    BISCUIT("Biscuit", "Puppy", "🐶", "Loyal, cheerful & supportive", "You're doing wonderfully today! 🦴")
}

enum class AudienceType(
    val label: String,
    val icon: String,
    val badgeText: String
) {
    EVERYONE_IN_CIRCLE("All Connections", "👥", "TRUSTED CIRCLE"),
    CLOSE_FRIENDS("Close Friends Only", "⭐️", "CLOSE FRIENDS"),
    SPECIFIC_CIRCLE("Specific Circle", "🎯", "CIRCLE ONLY"),
    ONLY_ME("Only Me (Private Log)", "🔒", "PRIVATE LOG")
}

enum class NoteExpiry(
    val label: String,
    val durationMillis: Long?
) {
    ONE_HOUR("1 hour", 3600000L),
    THREE_HOURS("3 hours", 10800000L),
    SIX_HOURS("6 hours", 21600000L),
    TWELVE_HOURS("12 hours", 43200000L),
    ONE_DAY("24 hours", 86400000L),
    THREE_DAYS("3 days", 259200000L),
    ONE_WEEK("1 week", 604800000L),
    NEVER("Never expires", null);

    companion object {
        fun calculateExpiresAt(expiry: NoteExpiry, fromMillis: Long = System.currentTimeMillis()): Long? {
            val dur = expiry.durationMillis ?: return null
            return fromMillis + dur
        }
    }
}

enum class ConnectionStatus {
    NONE,
    REQUEST_SENT,
    REQUEST_RECEIVED,
    CONNECTED,
    BLOCKED
}

enum class NoteType {
    STANDARD,
    WHISPER,
    MEMORY_CAPSULE,
    FUTURE_SELF,
    GRATITUDE,
    JOURNAL,
    COLLABORATIVE,
    VOICE_POSTCARD
}
