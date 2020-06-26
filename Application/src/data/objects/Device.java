package data.objects;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Device implements Serializable {

    private String deviceID;
    private DeviceType deviceType;
    private int cost;
    final private String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    final private Random rng = new SecureRandom();
    final private List<DeviceType> deviceTypeList = Collections.unmodifiableList(Arrays.asList(DeviceType.values()));
    

    public Device() {
        this.deviceID = generateDeviceID(8);
        this.deviceType = generateDeviceType();
        this.cost = generateCost();
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public int getCost() {
        return cost;
    }

    public String printDevicePrize(){
        return "$ " + getCost();
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    private int generateCost(){
        return(int)(Math.random() * 5000);
    }

    private String generateDeviceID(int length){
        StringBuilder sb = new StringBuilder();
        while(length > 0){
            length--;
            sb.append(randomChar());
        }
        return sb.toString();
    }

    private char randomChar(){
        return ALPHABET.charAt(rng.nextInt(ALPHABET.length()));
    }

    private DeviceType generateDeviceType(){
        return deviceTypeList.get(rng.nextInt(deviceTypeList.size()));
    }

    public String printDevice(){
        return  "TYPE: " + this.getDeviceType() + " ID: " + this.getDeviceID() + " COST: " + this.printDevicePrize();
    }
}
