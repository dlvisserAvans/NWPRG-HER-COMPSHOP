package data;

import data.user.User;

import java.util.ArrayList;

public class DataBase {

    ArrayList<User> users;

    public DataBase() {
        this.buildings = setupBuildingList();
        this.users = setupUserList();
    }

    public ArrayList<Building> setupBuildingList(){

        return buildings;
    }

    public ArrayList<User> setupUserList(){
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Dave","1234"));
        users.add(new User("JanKees","1234"));
        users.add(new User("Hans", "1234"));

        return users;
    }

    public boolean checkUserRegisterd(String userName, String password){
        for (User user : users){
            if (user.getLoginname().equals(userName)){
                if (user.getPassword().equals(password)){
                    return true;
                }
            }
        }
        return false;
    }

    public void registerNewUser(String userName, String password){
        this.users.add(new User(userName,password));
    }

    public void addNewBuilding(String zipcode, String address, String City, BuildingType buildingType, int price){
        if (buildingType.equals(BuildingType.HOUSE)){
            this.buildings.add(new House(zipcode,address,City, price));
        }
        if (buildingType.equals(BuildingType.APARTMENT)){
            this.buildings.add(new Apartment(zipcode,address,City, price));
        }
        if (buildingType.equals(BuildingType.CONDO)){
            this.buildings.add(new Condo(zipcode,address,City, price));
        }
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
