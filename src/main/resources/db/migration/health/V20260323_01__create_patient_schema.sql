-- =====================================================
-- SCHEMA
-- =====================================================
CREATE SCHEMA IF NOT EXISTS health_tracker;

-- =====================================================
-- PATIENTS TABLE
-- =====================================================
CREATE TABLE IF NOT EXISTS health_tracker.patients (
                                                       id              BIGSERIAL PRIMARY KEY,
                                                       user_id         BIGINT UNIQUE,

                                                       first_name      VARCHAR(50)  NOT NULL,
    last_name       VARCHAR(50)  NOT NULL,
    date_of_birth   DATE,

    gender          VARCHAR(10)
    CHECK (gender IN ('MALE', 'FEMALE', 'OTHER')),

    blood_group     VARCHAR(5)
    CHECK (blood_group IN ('A+','A-','B+','B-','AB+','AB-','O+','O-')),

    height_cm       NUMERIC(5,2)
    CHECK (height_cm BETWEEN 50 AND 250),

    phone           VARCHAR(20),
    address         VARCHAR(300),

    emergency_contact_name  VARCHAR(100),
    emergency_contact_phone VARCHAR(20),

    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_patient_user
    FOREIGN KEY (user_id)
    REFERENCES auth.users(id)
    ON DELETE SET NULL
    );

-- =====================================================
-- USER ↔ PATIENT ACCESS (Doctor / Caregiver / Self)
-- =====================================================
CREATE TABLE IF NOT EXISTS health_tracker.user_patient_access (
                                                                  id          BIGSERIAL PRIMARY KEY,
                                                                  user_id     BIGINT NOT NULL,
                                                                  patient_id  BIGINT NOT NULL,

                                                                  access_type VARCHAR(20) NOT NULL
    CHECK (access_type IN ('SELF','DOCTOR','CAREGIVER','FAMILY')),

    granted_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_user_patient UNIQUE (user_id, patient_id),

    CONSTRAINT fk_access_user
    FOREIGN KEY (user_id)
    REFERENCES auth.users(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_access_patient
    FOREIGN KEY (patient_id)
    REFERENCES health_tracker.patients(id)
    ON DELETE CASCADE
    );

-- =====================================================
-- DAILY HEALTH LOG
-- =====================================================
CREATE TABLE IF NOT EXISTS health_tracker.daily_health_log (
                                                               id              BIGSERIAL PRIMARY KEY,
                                                               patient_id      BIGINT NOT NULL,
                                                               log_date        DATE NOT NULL,

                                                               steps           INT CHECK (steps >= 0),
    calories        INT CHECK (calories >= 0),
    protein_grams   INT CHECK (protein_grams >= 0),
    water_ml        INT CHECK (water_ml >= 0),

    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_log_patient
    FOREIGN KEY (patient_id)
    REFERENCES health_tracker.patients(id)
    ON DELETE CASCADE,

    CONSTRAINT uq_patient_date UNIQUE (patient_id, log_date)
    );

-- =====================================================
-- DIET ENTRY
-- =====================================================
CREATE TABLE IF NOT EXISTS health_tracker.diet_entry (
                                                         id          BIGSERIAL PRIMARY KEY,
                                                         log_id      BIGINT NOT NULL,

                                                         meal_type   VARCHAR(20)
    CHECK (meal_type IN ('BREAKFAST','LUNCH','DINNER','SNACK')),

    food_name   VARCHAR(100) NOT NULL,
    calories    INT CHECK (calories >= 0),
    protein     INT CHECK (protein >= 0),

    CONSTRAINT fk_diet_log
    FOREIGN KEY (log_id)
    REFERENCES health_tracker.daily_health_log(id)
    ON DELETE CASCADE
    );

-- =====================================================
-- EXERCISE ENTRY
-- =====================================================
CREATE TABLE IF NOT EXISTS health_tracker.exercise_entry (
                                                             id              BIGSERIAL PRIMARY KEY,
                                                             log_id          BIGINT NOT NULL,

                                                             exercise_name   VARCHAR(100) NOT NULL,
    duration_min    INT CHECK (duration_min >= 0),
    calories_burned INT CHECK (calories_burned >= 0),

    CONSTRAINT fk_exercise_log
    FOREIGN KEY (log_id)
    REFERENCES health_tracker.daily_health_log(id)
    ON DELETE CASCADE
    );

-- =====================================================
-- MEDICINE ENTRY
-- =====================================================
CREATE TABLE IF NOT EXISTS health_tracker.medicine_entry (
                                                             id          BIGSERIAL PRIMARY KEY,
                                                             log_id      BIGINT NOT NULL,

                                                             medicine_name   VARCHAR(100) NOT NULL,
    dosage          VARCHAR(50),
    taken           BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_medicine_log
    FOREIGN KEY (log_id)
    REFERENCES health_tracker.daily_health_log(id)
    ON DELETE CASCADE
    );

-- =====================================================
-- INDEXES
-- =====================================================
CREATE INDEX idx_patient_user_id
    ON health_tracker.patients(user_id);

CREATE INDEX idx_patient_name
    ON health_tracker.patients(last_name, first_name);

CREATE INDEX idx_access_user_patient
    ON health_tracker.user_patient_access(user_id, patient_id);

CREATE INDEX idx_log_patient_date
    ON health_tracker.daily_health_log(patient_id, log_date);

-- =====================================================
-- AUTO UPDATE updated_at TRIGGER
-- =====================================================
CREATE OR REPLACE FUNCTION health_tracker.update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
   NEW.updated_at = CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_patient_timestamp
    BEFORE UPDATE ON health_tracker.patients
    FOR EACH ROW
    EXECUTE FUNCTION health_tracker.update_timestamp();

-- =====================================================
-- SAMPLE ANALYTICS QUERY (TODAY HEALTH STATUS)
-- =====================================================
-- Replace :patientId with actual value

SELECT
    steps,
    protein_grams,
    calories,
    water_ml,

    CASE
        WHEN steps >= 8000 THEN 'GOOD'
        ELSE 'LOW_ACTIVITY'
    END AS step_status,

    CASE
        WHEN protein_grams >= 80 THEN 'GOOD'
        ELSE 'LOW_PROTEIN'
    END AS protein_status,

    CASE
        WHEN calories BETWEEN 1800 AND 2500 THEN 'BALANCED'
        ELSE 'IMBALANCED'
    END AS calorie_status

FROM health_tracker.daily_health_log
WHERE patient_id = :patientId
AND log_date = CURRENT_DATE;