package data.people;

import data.objects.Device;
import gui.customer.GuiCustomer;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Customer extends Person{

    private String customerID;
    private ArrayList<Device> shoppincart;
    private HashMap<String,ArrayList<Device>> receipt;
    final private String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    final private Random rng = new SecureRandom();

    public Customer() {
        this.shoppincart = new ArrayList<>();
        this.customerID = generateCustomerId(10);
    }

    public void addItemToShoppinCart(Device device){
        this.shoppincart.add(device);
        System.out.println("ID: " + device.getDeviceID() + " TYPE: " + device.getDeviceType() + " COST: " + device.getCost() + " Has been added to the shopping cart");
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public ArrayList<Device> getShoppincart() {
        return shoppincart;
    }

    public void setShoppincart(ArrayList<Device> shoppincart) {
        this.shoppincart = shoppincart;
    }

    public HashMap<String, ArrayList<Device>> getReceipt() {
        return receipt;
    }

    public void setReceipt(HashMap<String, ArrayList<Device>> receipt) {
        this.receipt = receipt;
    }

    private String generateCustomerId(int length){
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

    public int getAllItemCost(){
        int totalprice = 0;

        for (Device device: shoppincart){
            totalprice += device.getCost();
        }
        return totalprice;
    }
}
