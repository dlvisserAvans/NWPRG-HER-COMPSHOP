package data.people;

import data.objects.ComputerShop;
import data.objects.DeviceType;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Employee extends Person {

    private String password;
    private String userName;
    final private Random rng = new SecureRandom();
    final private List<PeopleNames> peopleNamesList = Collections.unmodifiableList(Arrays.asList(PeopleNames.values()));

    public Employee() {
        this.userName = generateUsername();
        this.password = String.valueOf(generatePassword());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    private int generatePassword(){
        return(int)(Math.random() * 1000);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String generateUsername(){
        String name = "" + peopleNamesList.get(rng.nextInt(peopleNamesList.size()));

        return name;
    }


}
