-- liquibase formatted sql
-- changeset kasparas.putrius:create_interview_questions_table

CREATE TABLE interview_questions
(
    id           BIGINT PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    interview_id BIGINT       NOT NULL
        CONSTRAINT fk_interview_question REFERENCES interviews
);