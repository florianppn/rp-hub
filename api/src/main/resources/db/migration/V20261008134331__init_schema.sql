CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    public_id UUID NOT NULL UNIQUE DEFAULT gen_random_uuid() UNIQUE,
    username VARCHAR(25) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(25) NOT NULL DEFAULT 'PLAYER' CHECK (role IN ('PLAYER', 'MODERATOR', 'ADMIN')),

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMPTZ
);

CREATE TABLE characters (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE RESTRICT,

    public_id UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender VARCHAR(10) NOT NULL CHECK (gender IN ('MALE', 'FEMALE')),
    -- limite arbitraire de taille pour les personnages, entre 140 et 220 cm
    height INT NOT NULL CHECK (height >= 140 AND height <= 220),
    status VARCHAR(10) NOT NULL DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'DECEASED', 'ARCHIVED')),
    has_staff_tools BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_characters_user ON characters(user_id);

CREATE TABLE bank_accounts (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    character_id BIGINT NOT NULL REFERENCES characters(id) ON DELETE RESTRICT,

    public_id UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    account_number VARCHAR(30) NOT NULL UNIQUE,
    balance NUMERIC(15, 2) NOT NULL DEFAULT 0.00 CHECK (balance >= 0.00),
    status  VARCHAR(10) NOT NULL DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'FROZEN', 'CLOSED')),

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_bank_accounts_character ON bank_accounts(character_id);

CREATE TABLE transactions (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    source_account_id BIGINT REFERENCES bank_accounts(id) ON DELETE RESTRICT,
    target_account_id BIGINT REFERENCES bank_accounts(id) ON DELETE RESTRICT,

    public_id UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    type_transaction VARCHAR(10) NOT NULL CHECK (type_transaction IN ('TRANSFER', 'PAYMENT', 'WITHDRAWAL', 'DEPOSIT')),
    category_transaction VARCHAR(10) NOT NULL CHECK (category_transaction IN ('SALARY', 'HOUSING', 'HEALTH', 'GROCERIES', 'VEHICLE', 'LEGAL', 'BUSINESS', 'TAX')),
    amount NUMERIC(15, 2) NOT NULL CHECK (amount >= 0.00),

    CONSTRAINT chk_transaction_accounts CHECK (
        (type_transaction = 'TRANSFER' AND source_account_id IS NOT NULL AND target_account_id IS NOT NULL) OR
        (type_transaction = 'PAYMENT' AND source_account_id IS NOT NULL AND target_account_id IS NOT NULL) OR
        (type_transaction = 'WITHDRAWAL' AND source_account_id IS NOT NULL AND target_account_id IS NULL) OR
        (type_transaction = 'DEPOSIT' AND source_account_id IS NULL AND target_account_id IS NOT NULL)
    ),

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_transactions_source_bank ON transactions(source_account_id);
CREATE INDEX idx_transactions_target_bank ON transactions(target_account_id);