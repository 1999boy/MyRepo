import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, double basePricePerDay, String model) {
        this.carId = carId;
        this.brand = brand;
        this.basePricePerDay = basePricePerDay;
        this.model = model;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatorPrice(int rentalDay) {
        return basePricePerDay * rentalDay;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent(){
        isAvailable=false;
    }
    public void returnCar(){
        isAvailable=true;
    }
}

class Customer{

    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId(){
        return customerId;
    }
    public String getName(){
        return name;
    }
}

class Rental{

    private Car car;

    private Customer customer;

    private int day;

    public Rental(Car car, Customer customer, int day) {
        this.car = car;
        this.customer = customer;
        this.day = day;
    }

    public Car getCar(){
        return car;
    }
    public Customer getCustomer(){
        return customer;
    }

}

public class CarRentalSystem {
    private List<Car> cars;

    private List<Customer> customers;

    private  List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers =new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void  addCar(Car car){
        cars.add(car);
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public void rentCar(Car car,Customer customer,int day){
        if (car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car,customer,day));
        }else {
            System.out.println("car is not available for rent");
        }
    }

    public void returnCar(Car car){
        car.returnCar();
        Rental rentalToRemove=null;
        for (Rental rental: rentals){
            if (rental.getCar()== car){
                rentalToRemove=rental;
                break;
            }
        }
        if (rentalToRemove != null){
            rentals.remove(rentalToRemove);
            System.out.println("car return successfully");
        }
        else {
            System.out.println("car was not rented");
        }
    }
    public void menu(){
        Scanner scanner=new Scanner(System.in);

        while (true){
            System.out.println("*==== car rental System ====*");
            System.out.println("1.    Rent a car");
            System.out.println("2.    Return a Car");
            System.out.println("3.    Exit");

            int choice=scanner.nextInt();
            scanner.nextLine();

            if (choice==1){
                System.out.println("\n==  Rent a car  ==\n");
                System.out.println("enter your name");
                String customerName=scanner.nextLine();

                System.out.println("\nAvailable cars:");
                for (Car car : cars){
                    if (car.isAvailable()){
                        System.out.println(car.getCarId()+"  "+car.getBrand()+"  "+car.getModel());
                    }
                }
                System.out.println("\nenter the car id you want to rent");
                String carId = scanner.nextLine();

                System.out.println("enter the number of day for rental:  ");
                int rentalDay=scanner.nextInt();
                scanner.nextLine();// customer new line

                Customer newCustomer = new Customer("cus" + (customers.size() + 1),customerName);
                addCustomer(newCustomer);

                Car selectedCar=null ;
                for (Car car : cars){
                    if (car.getCarId().equals(carId)&& car.isAvailable()){
                        selectedCar=car;
                        break;
                    }
                }
                if (selectedCar != null){
                    double totalPrice = selectedCar.calculatorPrice(rentalDay);
                    System.out.println("\n==  Rental Information  ==\n");
                    System.out.println("customer id: "+ newCustomer.getCustomerId());
                    System.out.println("customerName : "+newCustomer.getName());
                    System.out.println("car : "+selectedCar.getBrand()+"   "+selectedCar.getModel());
                    System.out.println("Rentsl Day : "+rentalDay);
                    System.out.println("total price: "+ totalPrice);
                    System.out.println("\nconfirm rental (Y/S): ");

                    String confirm=scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")){
                        rentCar(selectedCar, newCustomer,rentalDay);
                        System.out.println("\n car rented successfully");
                    }else {
                        System.out.println("\n rental canceled");
                    }

                }else {
                    System.out.println("\n invalid Car Selection or cat not available for rent");
                }
            } else if (choice==2) {
                System.out.println("\n== return a car ==");
                System.out.println("enter the car id you want to return: ");
                String carId=scanner.nextLine();

                Car carToReturn=null;
                for (Car car : cars){
                    if (car.getCarId().equals(carId) && !car.isAvailable()){
                        carToReturn=car;
                        break;
                    }
                }

                if (carToReturn !=null)
                {
                    Customer customer=null;
                    for (Rental rental : rentals){
                        if (rental.getCar()==carToReturn){
                            customer =rental.getCustomer();
                            break;
                        }
                    }
                    if (customer != null){
                        returnCar(carToReturn);
                        System.out.println("car return successfully by  "+ customer.getName());

                    }else {
                        System.out.println("car was not return or rental  information is missing  ");
                    }
                }else {
                    System.out.println("invalid car id or car is not return");
                }

            }else if (choice==3){
                break;
            }else {
                System.out.println("invalid choice please enter the valid option");
            }
        }
        System.out.println("\n thank you using the car rental system");

    }



    public static void main(String[] args) {

        CarRentalSystem rentalSystem=new CarRentalSystem();

        Car car1=new Car("C001","Toyota",60.0,"Camry");
        Car car2=new Car("C002","Honda",85.0,"Accord");
        Car car3 =new Car("C003","Mahindra",99.0,"Thar");

        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        rentalSystem.menu();

    }
}