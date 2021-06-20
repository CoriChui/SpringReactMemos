CREATE TABLE app_user (
    id UUID NOT NULL PRIMARY KEY,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    ENUM ('ADMIN', 'USER'),
    isAccountNonExpired BOOLEAN NOT NULL,
    isAccountNonLocked BOOLEAN NOT NULL,
    isCredentialsNonExpired BOOLEAN NOT NULL,
    isEnabled BOOLEAN NOT NULL
);

CREATE TABLE email_verification_token (
    id UUID NOT NULL PRIMARY KEY,
    token VARCHAR(50) NOT NULL,
    app_user_id UUID NOT NULL,
    createdAt VARCHAR(50) NOT NULL,
    expiresAt VARCHAR(50) NOT NULL,
    confirmedAt VARCHAR(50) NOT NULL,
    FOREIGN KEY(app_user_id) REFERENCES app_user(id) ON DELETE SET NULL
);

CREATE TABLE memos (
    id UUID NOT NULL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(50) NOT NULL,
    createdAt VARCHAR(50) NOT NULL,
    userId UUID NOT NULL
);