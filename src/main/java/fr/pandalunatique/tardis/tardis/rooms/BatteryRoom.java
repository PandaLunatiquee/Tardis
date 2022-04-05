package fr.pandalunatique.tardis.tardis.rooms;

public class BatteryRoom extends Room {

    private int battery;

    public BatteryRoom(String name, int initCost, int timeCost, int batteryStorage) {
        super(name, initCost, timeCost);
        this.battery = batteryStorage;
    }

    @Override
    public void printName() {

    }

}
