package com.example.firestoreclassdemo;

public class Patient {

    private String name;
    private int age;
    private String reason;

    public Patient(String name, int age, String reason) {
        this.name = name;
        this.age = age;
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
