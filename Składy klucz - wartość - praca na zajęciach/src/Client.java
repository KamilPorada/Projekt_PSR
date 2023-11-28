import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.example.Car;
import org.example.Owner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Client {
    static int carIdentifier = 0;
    static int ownerIdentifier = 0;

    public static void main(String[] args) throws IOException {
        ClientConfig clientConfiguration = new ClientConfig();
        HazelcastInstance hazelcastClient = HazelcastClient.newHazelcastClient(clientConfiguration);

        IMap<Integer, Car> carsMap = hazelcastClient.getMap("cars");
        IMap<String, Owner> ownersMap = hazelcastClient.getMap("owners");

        while(true){
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("SERWIS SAMOCHODOWY");
            System.out.println("1. Nowy samochód\n" +
                    "2. Lista samochodów\n" +
                    "3. Usuń samochód\n" +
                    "4. Modyfikuj samochód\n" +
                    "5. Dodaj klienta\n" +
                    "6. Lista klientów\n" +
                    "7. Zakończ");
            System.out.print("Opcja: ");
            String choice = reader.readLine();

            if (choice.equals("1")) {
                    if(ownersMap.isEmpty()){
                        System.out.println("Brak klientów w serwisie! Wciśnij 5 aby dodać klienta!");
                    }
                    else{
                        System.out.print("Marka: ");
                        String make = reader.readLine();
                        System.out.print("Kolor: ");
                        String color = reader.readLine();
                        System.out.print("Wiek: ");
                        int age;
                        try {
                            age = Integer.parseInt(reader.readLine());
                        } catch (NumberFormatException nfe) {
                            continue;
                        }
                        System.out.print("ID klienta: ");
                        int ownerId;
                        try {
                            ownerId = Integer.parseInt(reader.readLine());
                        } catch (NumberFormatException nfe) {
                            continue;
                        }
                        Car newCar = new Car(++carIdentifier, make, color, age, ownerId);
                        carsMap.put(carIdentifier, newCar);
                        System.out.println("Pomyślnie dodano samochód.");
                    }
            }
            else if (choice.equals("2")) {
                Set<Map.Entry<Integer, Car>> carList =  carsMap.entrySet();
                if (carList.isEmpty()) {
                    System.out.println("Brak samochodów w bazie danych!");
                    System.out.println(".........................................................................................");
                }
                else{
                    System.out.println("Samochody w serwisie:");
                    for (Map.Entry<Integer, Car> entry : carsMap.entrySet()) {
                        Car currentCar = entry.getValue();
                        System.out.println("ID: " + currentCar.getId() + ", Marka: " + currentCar.getMake() + ", kolor: " + currentCar.getColor() + ", Wiek: " + currentCar.getAge() + ", ID klienta: " + currentCar.getOwnerId());
                    }
                    System.out.println("-----------------------------------------------------");
                }
            }
            else if (choice.equals("3")) {
                if (carsMap.isEmpty()) {
                    System.out.println("Brak samochodów w bazie danych!");
                    System.out.println(".........................................................................................");
                }
                else{
                    System.out.print("Wprowadź id samochodu do usunięcia: ");
                    int idToDelete;
                    try {
                        idToDelete = Integer.parseInt(reader.readLine());
                    } catch (NumberFormatException nfe) {
                        continue;
                    }
                    if (carsMap.containsKey(idToDelete)) {
                        carsMap.remove(idToDelete);
                        System.out.println("Pomyślnie usunięto samochód o ID: " + idToDelete);
                    } else {
                        System.out.println("Samochód o podanym ID nie istnieje!");
                    }
                }
            }
            else if (choice.equals("4")) {
                if (carsMap.isEmpty()) {
                    System.out.println("Brak samochodów w bazie danych!");
                    System.out.println(".........................................................................................");
                }
                else{
                    System.out.print("Podaj ID samochodu do modyfikacji: ");
                    int carIdToModify;
                    try {
                        carIdToModify = Integer.parseInt(reader.readLine());
                    } catch (NumberFormatException nfe) {
                        continue;
                    }
                    if (carsMap.containsKey(carIdToModify)) {
                        System.out.print("Nowa marka: ");
                        String newMake = reader.readLine();
                        System.out.print("Nowy kolor: ");
                        String newColor = reader.readLine();
                        System.out.print("Nowy wiek: ");
                        int newAge;
                        try {
                            newAge = Integer.parseInt(reader.readLine());
                        } catch (NumberFormatException nfe) {
                            System.out.println("Błędny format wieku!");
                            continue;
                        }
                        System.out.print("ID klienta: ");
                        int ownerID;
                        try {
                            ownerID = Integer.parseInt(reader.readLine());
                        } catch (NumberFormatException nfe) {
                            System.out.println("Błędny format id!");
                            continue;
                        }
                        Car modifiedCar = new Car(carIdToModify, newMake,newColor,newAge,ownerID);

                        carsMap.put(carIdToModify, modifiedCar);
                        System.out.println("Pomyślnie zmodyfikowano samochód o ID: " + carIdToModify);
                    } else {
                        System.out.println("Samochód o podanym ID nie istnieje!");
                    }
                }
            }
            else if(choice.equals("5")){
                System.out.print("Podaj imię: ");
                String name = reader.readLine();
                System.out.print("Podaj nazwisko: ");
                String surname = reader.readLine();
                System.out.print("Podaj numer telefonu: ");
                int number;
                try {
                    number = Integer.parseInt(reader.readLine());
                } catch (NumberFormatException nfe) {
                    continue;
                }
                Owner newOwner = new Owner(++ownerIdentifier, name, surname, number);
                ownersMap.put("owner" + (ownersMap.size()+1), newOwner);
                System.out.println("Pomyślnie dodano nowego klienta.");
            }
            else if (choice.equals("6")) {
                Set<Map.Entry<String, Owner>> ownerList =  ownersMap.entrySet();
                if (ownerList.isEmpty()) {
                    System.out.println("Brak klientów w bazie danych!");
                    System.out.println(".........................................................................................");
                }
                else{
                    System.out.println("Klienci serwisu:");
                    for (Map.Entry<String, Owner> entry : ownersMap.entrySet()) {
                        Owner currentOwner = entry.getValue();
                        System.out.println("ID klienta: " + currentOwner.getOwnerId() + ", Imię: " + currentOwner.getName() + ", Nazwisko: " + currentOwner.getSurname() + ", Numer telefonu: " + currentOwner.getNumber());
                        List<Car> OwnerCars = currentOwner.getOwnerCars();
                        for (Car car : OwnerCars) {
                            System.out.println("  - ID: " + car.getId() + ", Imię: " + car.getMake() + ", Kolor: " + car.getColor() + ", Wiek: " + car.getAge());
                        }
                    }
                    System.out.println("-----------------------------------------------------");
                }
            }
            else if(choice.equals("7")){
                System.out.println("Wyjście");
                HazelcastClient.shutdownAll();
                break;
            }
            else {
                System.out.println("Zły wybór! Wybierz poprawną opcję!");
                System.out.println(".........................................................................................");
            }

        }

    }

}