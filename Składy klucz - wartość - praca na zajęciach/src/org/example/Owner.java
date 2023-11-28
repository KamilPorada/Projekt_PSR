package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Owner implements Serializable {
    private int ownerId;
    private String name;
    private String surname;
    private int number;
    private List<Car> ownerCars;

    public Owner(int ownerId, String name, String surname, int number) {
        this.ownerId = ownerId;
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.ownerCars = new ArrayList<>();
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public int getNumber() {
        return number;
    }

    public List<Car> getOwnerCars() {
        return new ArrayList<>(ownerCars);
    }

    public void addCarToOwner(Car car) {
        ownerCars.add(car);
    }

    public void removeCarFromOwner(int carId) {
        ownerCars.removeIf(car -> car.getId() == carId);
    }

    public synchronized void modifyOwnerCar(int carId, Car modifiedCar) {
        for (int i = 0; i < ownerCars.size(); i++) {
            Car car = ownerCars.get(i);
            if (car.getId() == carId) {
                ownerCars.set(i, modifiedCar);
                return;
            }
        }
    }

}
