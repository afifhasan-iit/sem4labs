interface Ride{
    void book();
}

class CarRide implements Ride{
    @Override
    public void book() {
        System.out.println("Calling Car");
    }
}
class BikeRide implements Ride{
    @Override
    public void book() {
        System.out.println("Calling Bike");
    }
}
class AutoRide implements Ride{
    @Override
    public void book() {
        System.out.println("Calling Auto");
    }
}

abstract class RideFactory {



    abstract Ride getRide();

    public void book() {
        Ride ride = getRide();
        ride.book();
    }
}

class BikeService extends RideFactory{
    @Override
    public Ride getRide() {
        return new BikeRide();
    }
}

class AutoService extends RideFactory{
    @Override
    public Ride getRide() {
        return new AutoRide();
    }
}
class CarService extends RideFactory{
    @Override
    public Ride getRide() {
        return new CarRide();
    }
}

public class Main {
    public static void main(String[] args) {
        BikeService bike = new BikeService();
        bike.book();
    }
}


