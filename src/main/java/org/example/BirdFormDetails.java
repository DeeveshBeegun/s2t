package org.example;

import lombok.Data;

import java.time.LocalTime;

@Data
public class BirdFormDetails {
    private String species;
    private int count;
    private String sex;
    private int age;
    private String behavior;
    private String habitat;
    private String notes;
    private LocalTime timeObserved;

    @Override
    public String toString() {
        return "{" +
                "\"species\": " + (species == null ? null : "\"" + species + "\"") + ", " +
                "\"count\": " + count + ", " +
                "\"sex\": " + (sex == null ? null : "\"" + sex + "\"") + ", " +
                "\"age\": " + age + ", " +
                "\"behavior\": " + (behavior == null ? null : "\"" + behavior + "\"") + ", " +
                "\"habitat\": " + (habitat == null ? null : "\"" + habitat + "\"") + ", " +
                "\"notes\": " + (notes == null ? null : "\"" + notes + "\"") + ", " +
                "\"timeObserved\": " + (timeObserved == null ? null : "\"" + timeObserved + "\"") +
                "}";
    }

}
