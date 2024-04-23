package com.interview.manager.backend.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public final class DataValidation {

    public enum CategoryType {
        VALID,
        INVALID,
        ABSENT,
        PRESENT,
        NOT_FOUND;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }

    @RequiredArgsConstructor
    public enum Status {
        SUCCESSFUL(200, CategoryType.VALID, "Operation performed successful"),
        MISSING_DATA(400, CategoryType.ABSENT, "Data is missing"),
        MALFORMED_DATA(400, CategoryType.INVALID, "Data is malformed"),
        TITLE_TOO_LARGE(400, CategoryType.INVALID, "Title is too long"),
        ILLEGAL_ACCEPT(422, CategoryType.INVALID, "Illegal accept value"),
        NOT_FOUND(404, CategoryType.NOT_FOUND, "Data not found");

        @Getter
        private final int code;

        @Getter
        private final CategoryType category;

        @Getter
        private final String description;

        @Override
        public String toString() {
            return description;
        }
    }
}
