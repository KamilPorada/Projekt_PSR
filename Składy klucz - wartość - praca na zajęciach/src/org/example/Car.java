package org.example;

import java.io.Serializable;

public class Car implements Serializable {
    private int id;
    private String make;
    private String color;
    private int age;
    private int ownerId;
    private static int nextId = 1;

    public Car(String make, String color, int mileage, int ownerId) {
        this.id = nextId++;
        this.make = make;
        this.color = color;
        this.age = mileage;
        this.ownerId = ownerId;
    }

    public Car(int id, String make, String color, int mileage, int ownerId) {
        this.id = id;
        this.make = make;
        this.color = color;
        this.age = mileage;
        this.ownerId = ownerId;
    }

    public int getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getColor() {
        return color;
    }

    public int getAge() {
        return age;
    }

    public int getOwnerId() {
        return ownerId;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Marka: " + make + ", Kolor: " + color + ", Wiek: " + age + ", ID właściciela: " + ownerId;
    }
}
