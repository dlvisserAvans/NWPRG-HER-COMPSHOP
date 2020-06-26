package data.objects;

import data.people.Customer;
import data.people.Employee;

import java.util.ArrayList;

public class ComputerShop {

    private String name;
    private ArrayList<Employee> workers;
    private ArrayList<Customer> customers;
    private static ArrayList<Device> devicesInStock;

    public ComputerShop(String name) {
        this.name = name;
        this.workers = generateWorkers(10);
        this.customers = new ArrayList();
        devicesInStock = generateDevices(50);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Employee> getWorkers() {
        return workers;
    }

    public void setWorkers(ArrayList<Employee> workers) {
        this.workers = workers;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    private ArrayList<Device> generateDevices(int amount){
        ArrayList<Device> devices = new ArrayList<>();
        for (int i = 0; i < amount; i++){
            devices.add(new Device());
        }
        return devices;
    }

    private ArrayList<Employee> generateWorkers(int amount){
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee());
        for (int i = 0; i < amount; i++){
            employees.add(new Employee());
        }
        return employees;
    }

    public void printDevicesInStock(){
        for (Device device: devicesInStock){
            device.printDevice();
        }
    }

    public ArrayList<Device> getDevicesInStock() {
        return devicesInStock;
    }

    public void fireEmployee(Employee employee){
        workers.remove(employee);
    }

    public void hireEmployee(){
        Employee employee = new Employee();
        workers.add(employee);
        System.out.println("Employee: " + employee.getUserName() + " is hired!");
    }

    public void setDevicesInStock(ArrayList<Device> devicesInStock) {
        ComputerShop.devicesInStock = devicesInStock;
    }

    public boolean checkUserRegisterd(String userName, String password){
        for (Employee user : workers){
            if (user.getUserName().equals(userName)){
                if (user.getPassword().equals(password)){
                    return true;
                }
            }
        }
        return false;
    }
}
