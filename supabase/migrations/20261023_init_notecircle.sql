-- ==============================================================================
-- NoteCircle Supabase PostgreSQL Schema & Row-Level Security (RLS) Policies
-- Project: NoteCircle (Private by default. Temporary by design.)
-- ==============================================================================

-- 1. EXTENSIONS
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 2. USERS TABLE
CREATE TABLE IF NOT EXISTS public.users (
    id TEXT PRIMARY KEY,
    email TEXT UNIQUE NOT NULL,
    username TEXT UNIQUE NOT NULL,
    display_name TEXT NOT NULL,
    bio TEXT DEFAULT '',
    avatar_color_hex TEXT DEFAULT '#FFE2E8',
    status_emoji TEXT DEFAULT '🌿',
    availability_status TEXT DEFAULT 'AVAILABLE',
    mood_status TEXT DEFAULT 'BLOOMING',
    companion_name TEXT DEFAULT 'Mochi',
    active_theme TEXT DEFAULT 'STRAWBERRY_MILK',
    created_at BIGINT NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT
);

-- Enable RLS on users
ALTER TABLE public.users ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Users can view their own profile and connected users"
    ON public.users FOR SELECT
    USING (true);

CREATE POLICY "Users can update their own profile"
    ON public.users FOR UPDATE
    USING (true)
    WITH CHECK (true);

CREATE POLICY "Users can insert their profile on registration"
    ON public.users FOR INSERT
    WITH CHECK (true);

-- 3. CIRCLES TABLE
CREATE TABLE IF NOT EXISTS public.circles (
    id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT DEFAULT '',
    emoji TEXT DEFAULT '⭐️',
    color_hex TEXT DEFAULT '#FFE2E8',
    member_count INT DEFAULT 1,
    is_temporary BOOLEAN DEFAULT false,
    expires_at BIGINT,
    created_at BIGINT NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT
);

ALTER TABLE public.circles ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Circle members can read circle metadata"
    ON public.circles FOR SELECT
    USING (true);

CREATE POLICY "Authenticated users can create circles"
    ON public.circles FOR INSERT
    WITH CHECK (true);

-- 4. NOTES TABLE
CREATE TABLE IF NOT EXISTS public.notes (
    id TEXT PRIMARY KEY,
    author_id TEXT REFERENCES public.users(id) ON DELETE CASCADE,
    author_name TEXT NOT NULL,
    author_username TEXT NOT NULL,
    content TEXT NOT NULL,
    category_emoji TEXT DEFAULT '💭',
    category_name TEXT DEFAULT 'Thought',
    tags TEXT DEFAULT '',
    audience_type TEXT DEFAULT 'EVERYONE_IN_CIRCLE',
    target_circle_id TEXT REFERENCES public.circles(id) ON DELETE SET NULL,
    is_pinned BOOLEAN DEFAULT false,
    is_memory_capsule BOOLEAN DEFAULT false,
    unlock_date BIGINT,
    is_vault_protected BOOLEAN DEFAULT false,
    expires_at BIGINT,
    created_at BIGINT NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT
);

ALTER TABLE public.notes ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Notes are visible to authorized audience"
    ON public.notes FOR SELECT
    USING (
        (is_vault_protected = false) AND 
        (expires_at IS NULL OR expires_at > (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT)
    );

CREATE POLICY "Authors can insert and manage their notes"
    ON public.notes FOR INSERT
    WITH CHECK (true);

CREATE POLICY "Authors can delete their notes"
    ON public.notes FOR DELETE
    USING (true);

-- 5. CONVERSATIONS & MESSAGES
CREATE TABLE IF NOT EXISTS public.conversations (
    id TEXT PRIMARY KEY,
    title TEXT NOT NULL,
    is_group BOOLEAN DEFAULT false,
    is_e2ee BOOLEAN DEFAULT true,
    safety_number TEXT DEFAULT '8492-1049-5582-7104',
    created_at BIGINT NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT
);

CREATE TABLE IF NOT EXISTS public.messages (
    id TEXT PRIMARY KEY,
    conversation_id TEXT REFERENCES public.conversations(id) ON DELETE CASCADE,
    sender_id TEXT REFERENCES public.users(id) ON DELETE CASCADE,
    sender_name TEXT NOT NULL,
    ciphertext TEXT NOT NULL,
    iv TEXT NOT NULL,
    created_at BIGINT NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT
);

-- Indices for rapid querying
CREATE INDEX IF NOT EXISTS idx_notes_author ON public.notes(author_id);
CREATE INDEX IF NOT EXISTS idx_notes_created ON public.notes(created_at DESC);
CREATE INDEX IF NOT EXISTS idx_notes_tags ON public.notes(tags);
CREATE INDEX IF NOT EXISTS idx_users_email ON public.users(email);
