package com.fitapp.logic.model.entity;

public enum Course {
    KICKBOXING(0, "kickboxing"),
    PUGILATO(1, "pugilato"),
    ZUMBA(2, "zumba"),
    SALSA(3, "salsa"),
    FUNCTIONAL(4, "funzionale"),
    WALKING(5, "walking"),
    PUMP(6, "pump");


    private final int courseNumber;
    private final String courseName;

    private Course(int courseNumber, String courseName) {
        this.courseName = courseName;
        this.courseNumber = courseNumber;
    }

    public static Course getCourse(int number) {
        for (Course c : Course.values()) if (c.courseNumber == number) return c;
        throw new IllegalArgumentException("Unable to find the requested course(" + number + ")");
    }

    public static Course getCourse(String name) {
        for (Course c : Course.values()) if (c.courseName.contentEquals(name)) return c;
        throw new IllegalArgumentException("Unable to find the requested view(" + name + ")");
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

}
