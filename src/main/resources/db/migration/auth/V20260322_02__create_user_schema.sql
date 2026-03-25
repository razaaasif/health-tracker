CREATE SCHEMA auth;

-- Users table (no role column anymore)
CREATE TABLE auth.users (
    id          BIGSERIAL PRIMARY KEY,
    email       VARCHAR(150) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    enabled     BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Roles table
CREATE TABLE auth.roles (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE
);

-- Many-to-many: one user can have multiple roles
CREATE TABLE auth.user_roles (
     user_id     BIGINT NOT NULL,
     role_id     BIGINT NOT NULL,
     PRIMARY KEY (user_id, role_id),
     CONSTRAINT fk_user_roles_user
         FOREIGN KEY (user_id) REFERENCES auth.users(id) ON DELETE CASCADE,
     CONSTRAINT fk_user_roles_role
         FOREIGN KEY (role_id) REFERENCES auth.roles(id) ON DELETE CASCADE
);

-- Seed default roles
INSERT INTO auth.roles (name) VALUES
  ('ROLE_PATIENT'),
  ('ROLE_DOCTOR'),
  ('ROLE_ADMIN');
