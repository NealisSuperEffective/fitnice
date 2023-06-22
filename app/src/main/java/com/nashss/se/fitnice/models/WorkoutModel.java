package com.nashss.se.fitnice.models;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class WorkoutModel {
    private String date;
    private String name;
    private Set<String> tags;
    private String description;
    private List<String> exercises;

    public WorkoutModel(String date, String name, Set<String> tags, String description, List<String> exercises) {
        this.name = name;
        this.date = date;
        this.tags = tags;
        this.description = description;
        this.exercises = exercises;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Set<String> getTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getExercises() {
        return exercises;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutModel that = (WorkoutModel) o;
        return Objects.equals(name, that.name) && Objects.equals(date, that.date) && Objects.equals(tags, that.tags) && Objects.equals(description, that.description) && Objects.equals(exercises, that.exercises);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, date, tags, description, exercises);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String date;
        private String name;
        private Set<String> tags;
        private String description;
        private List<String> exercises;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDate(String date) {

            this.date = date;
            return this;
        }

        public Builder withTags(Set<String> tags) {
            this.tags = tags;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withExercises(List<String> exercises) {
            this.exercises = exercises;
            return this;
        }

        public WorkoutModel build() {
            return new WorkoutModel(date, name, tags, description, exercises);
        }
    }
}
