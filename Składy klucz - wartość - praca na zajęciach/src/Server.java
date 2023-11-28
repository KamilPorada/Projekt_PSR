import com.hazelcast.config.Config;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import org.example.Car;
import org.example.Owner;

import java.io.IOException;

public class Server {
    static int carIdentifier = 0;
    static int ownerIdentifier = 0;

    public static void main(String[] args) {
        Config configuration = new Config();


        SerializerConfig carSerializerConfiguration = new SerializerConfig()
                .setTypeClass(Car.class)
                .setImplementation(new CarHelper());
        configuration.getSerializationConfig().addSerializerConfig(carSerializerConfiguration);

        SerializerConfig ownerSerializerConfiguration = new SerializerConfig()
                .setTypeClass(Owner.class)
                .setImplementation(new OwnerHelper());
        configuration.getSerializationConfig().addSerializerConfig(ownerSerializerConfiguration);

        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(configuration);

        IMap<String, Car> carsMap = hazelcastInstance.getMap("cars");
        IMap<String, Owner> ownerMap = hazelcastInstance.getMap("owners");

//        // MultiMap do przechowywania przypisania książek do czytelników
//        MultiMap<Object, Object> borrowedBooksMap = hazelcastInstance.getMultiMap("borrowedBooks");

        System.out.println("Serwer został uruchomiony!");
    }

    static class OwnerHelper implements StreamSerializer<Owner> {

        @Override
        public void write(ObjectDataOutput out, Owner owner) throws IOException {
            out.writeUTF(owner.getName());
            out.writeUTF(owner.getSurname());
            out.writeUTF(String.valueOf(owner.getNumber()));

        }

        @Override
        public Owner read(ObjectDataInput in) throws IOException {
            String name = in.readUTF();
            String surname = in.readUTF();
            int number = in.readInt();
            return new Owner(ownerIdentifier,name, surname, number);
        }

        @Override
        public int getTypeId() {
            return 1;
        }

        @Override
        public void destroy() {
        }
    }

    static class CarHelper implements StreamSerializer<Car> {

        @Override
        public void write(ObjectDataOutput out, Car car) throws IOException {
            out.writeUTF(car.getMake());
            out.writeUTF(car.getColor());
            out.writeUTF(String.valueOf(car.getAge()));
        }

        @Override
        public Car read(ObjectDataInput in) throws IOException {
            String make = in.readUTF();
            String color = in.readUTF();
            int number = in.readInt();
            int ownerId = in.readInt();
            return new Car(carIdentifier, make, color, number, ownerId);
        }

        @Override
        public int getTypeId() {
            return 2;
        }

        @Override
        public void destroy() {
        }
    }
}
