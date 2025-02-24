//	Vehicle Management System (Buy and Sell)

//	Outline :-
/*
		Classes : 
			(1) Vehicle with Subclass Car & Bike
			(2) VehicleInventory (acts as BackBone -> for managing vehicle data)
			(3) User
			(4) Payment
			(5) Admin
			(6) Authantication (validates credentials and manages registration / login)
			(7) Run
*/

/*
	Concepts :-
		(1) OOPs
			-Inheritance (Car and Bike)
			-Polymorphism (overridden displayDetails() in Car and Bike to show subclass-specific details)
			-Encapsulation (Data(e.g., price, isSold) is encapsulated within classes and accessed via methods like disp())
		(2)Arrays
*/

//	Importing unity of classes (Scanner, Array, etc.)
import java.util.*;

//	Base Class Vehicle
class Vehicle {
	
	//	Attributes OR Instance variables forstore vehicle properties
	String brand;
	String model;
	double price;
	String fuelType;
	double mileage;
	int year;
	
	boolean isSold;
	double rating;
	int numberOfRatings;
	boolean isFeatured;
	double discount;
	
	//	Constructor (for initializing Varialbles)
	Vehicle(String brand, String model, double price, String fuelType, double mileage, int year) {
		//	Using "this" keyword to ==> Refer to the Current Instance Variable
		// Differentiating instance variable and parameter
		this.brand = brand;
		this.model = model;
		this.price = price;
		this.fuelType = fuelType;
		this.mileage = mileage;
		this.year = year;
		
		this.isSold = false;
		this.rating = 0;
		this.numberOfRatings = 0;
		this.isFeatured = false;
		this.discount = 0;
	}
	
	//	Method to Display Details
	void displayDetails() {
		System.out.println("\n--> Brand: " + brand);
		System.out.println("--> Mileage: " + mileage);
		System.out.println("--> Price: $" + price);
		System.out.println("--> Discounted price: $" + (price - discount));
		System.out.println("--> Fuel Type: " + fuelType);
		System.out.println("--> Mileage: " + mileage + " km/l");
		System.out.println("--> Year" + year);
		System.out.println("--> Status: " + (isSold ? "Sold" : "Available"));
		System.out.println("--> Rating: " + (numberOfRatings > 0 ? rating / numberOfRatings : "Not Rated"));
		System.out.println("--> Featured" + (isFeatured ? "Yes" : "No"));
	}
	
	// Method rateVehicle and applyDiscount for mogify the state of the Vehicle class
	
	void rateVehicle(double rating) {
		this.rating += rating;
		this.numberOfRatings++;
	}
	
	void applyDiscount(double discount) {
		this.discount = discount;
	}
	 
	void markAsFeatured() {
		this.isFeatured = true;
	}
}


//	Class Car (Subclass of Vehicle)
class Car extends Vehicle {
	int numberOfSeats;
	
	//	Constructor
	Car(String brand, String model, double price, String fuelType, double mileage, int year, int numberOfSeats) {
		super(brand, model, price, fuelType, mileage, year);					//	Using "super" keyword for ==> Access Parent Class Constructor (Vehicle)
		this.numberOfSeats = numberOfSeats;
	}
	
	//	Override
	void displayDetails() {
		super.displayDetails();					//	Using "super" keyword for ==> Access Parent Class Method (displayDetails)
		System.out.println("--> Number of Seats: " + numberOfSeats);
	}
}


// Class Bike (Subclass of Vehicle)
class Bike extends Vehicle {
	String bikeType;	//	e.g., Sports, Cruiser, Off-road
	
	//	Constructor
	Bike(String brand, String model, double price, String fuelType, double mileage, int year, String bikeType) {
		super(brand, model, price, fuelType, mileage, year);
		this.bikeType = bikeType;
	}
	
	//	Override
	void displayDetails() {
		super.displayDetails();
		System.out.println("--> Bike Type: " + bikeType);
	}
}



// Class VehicleInventory --> to manage the inventory of vehicles (including available and sold vehicle)
class VehicleInventory {
	//	Array to store available and sold vehicles 
	// array availableVehicles & soldVehicles can hold 30 objects of type 'Vehicle' 
	
	Vehicle[] availableVehicles = new Vehicle[30];
	Vehicle[] soldVehicles = new Vehicle[30];
	
	// Instance variable to track count of available and sold vehicles
	int availableCount = 0;
	int soldCount = 0;
	
	// Method for adding vehicle
	void addVehicle(Vehicle vehicle) {
		if(availableCount < availableVehicles.length) {
			availableVehicles[availableCount++] = vehicle;
		}
		else {
			System.out.println("Inventory Full!!");
		}
	}
	
	// Method for remove vehicle (removes sold vehicle and make one space null)
	void removeVehicle(Vehicle vehicle) {
		for(int i = 0; i < availableCount; i++) {
			if(availableVehicles[i] == vehicle) {
				soldVehicles[soldCount++] = vehicle;
				vehicle.isSold = true;
				
				for(int j = i; j < availableCount - 1; j++) {
					availableVehicles[j] = availableVehicles[j + 1];
				}
				
				availableVehicles[--availableCount] = null;
				break;
			}
		}
	}
	
	// Method to display available vehicles 
	void displayAvailableVehicles() {
		System.out.println("\nAvailable Vehicles:");
		
		for(int i = 0; i < availableCount; i++) {
			System.out.println("--------------------------------------------------");
			System.out.println("Vehicle ID: " + (i + 1));
			availableVehicles[i].displayDetails();
			System.out.println("--------------------------------------------------");
		}
	}
	
	// Method to display sold vehicles
	void displaySoldVehicles() {
		System.out.println("\nSold Vehicles:");
		
		for(int i = 0; i < soldCount; i++) {
			System.out.println("--------------------------------------------------");
			soldVehicles[i].displayDetails();
			System.out.println("--------------------------------------------------");
		}
	}
	
	//	search vehicles by specific brand
	Vehicle[] searchVehiclesByBrand(String brand) {
		Vehicle[] result = new Vehicle[availableCount];
		
		int count = 0;
		
		for(int i = 0; i < availableCount; i++) {
			if(availableVehicles[i].brand.equalsIgnoreCase(brand)) {
				result[count++] = availableVehicles[i];
			}
		}
		
		Vehicle[] finalResult = new Vehicle[count];
		
		for(int i = 0; i < count; i++) {
			finalResult[i] = result[i];
		}
		
		return finalResult;
	}
	
	Vehicle[] filterVehiclesByPrice(double minPrice, double maxPrice) {
		Vehicle[] result = new Vehicle[availableCount];
		
		int count = 0;
		
		for(int i = 0; i < availableCount; i++) {
			if(availableVehicles[i].price >= minPrice && availableVehicles[i].price <= maxPrice) {
				result[count++] = availableVehicles[i];
			}
		}
		
		Vehicle[] finalResult = new Vehicle[count];
		
		for(int i = 0; i < count; i++) {
			finalResult[i] = result[i];
		}
		
		return finalResult;
	}
	
	Vehicle[] filterVehiclesByYear(int minYear, int maxYear) {
		Vehicle[] result = new Vehicle[availableCount];
		
		int count = 0;
		
		for(int i = 0; i < availableCount; i++) {
			if(availableVehicles[i].year >= minYear && availableVehicles[i].year <= maxYear) {
				result[count++] = availableVehicles[i];
			}
		}
		
		Vehicle[] finalResult = new Vehicle[count];
		
		for(int i = 0; i < count; i++) {
			finalResult[i] = result[i];
		}
		
		return finalResult;
	}
	
	Vehicle[] filterVehiclesByMileage(double minMileage, double maxMileage) {
		Vehicle[] result = new Vehicle[availableCount];
		
		int count = 0;
		
		for(int i = 0; i < availableCount; i++) {
			if(availableVehicles[i].mileage >= minMileage && availableVehicles[i].mileage <= maxMileage) {
				result[count++] = availableVehicles[i];
			}
		}
		
		Vehicle[] finalResult = new Vehicle[count];
		
		for(int i = 0; i < count; i++) {
			finalResult[i] = result[i];
		}
		
		return finalResult;
	}
	
	Vehicle[] filterVehiclesByFuelType(String fuelType) {
		Vehicle[] result = new Vehicle[availableCount];
		
		int count = 0;
		
		for(int i = 0; i < availableCount; i++) {
			if(availableVehicles[i].fuelType.equalsIgnoreCase(fuelType)) {
				result[count++] = availableVehicles[i];
			}
		}
		
		Vehicle[] finalResult = new Vehicle[count];
		
		for(int i = 0; i < count; i++) {
			finalResult[i] = result[i];
		}
		
		return finalResult;
	}
	
	Vehicle[] getTopRatedVehicles(int limit) {
		Vehicle[] sortedVehicles = new Vehicle[availableCount];
		
		for(int i = 0; i < availableCount; i++) {
			sortedVehicles[i] = availableVehicles[i];
		}
		
		// Sort by rating (in discending order)
		for(int i = 0; i < availableCount - 1; i++) {
			for(int j = 0; j < availableCount; j++) {
				double rating1 = sortedVehicles[i].numberOfRatings > 0 ? sortedVehicles[i].rating / sortedVehicles[i].numberOfRatings : 0 ;
				double rating2 = sortedVehicles[j].numberOfRatings > 0 ? sortedVehicles[j].rating / sortedVehicles[j].numberOfRatings : 0 ;
				
				if(rating1 < rating2) {
					Vehicle temp = sortedVehicles[i];
					sortedVehicles[i] = sortedVehicles[j];
					sortedVehicles[j] = temp;
				}
			}
		}
		
		Vehicle[] topRatedVehicles = new Vehicle[Math.min(limit, availableCount)];
		
		for(int i = 0; i < topRatedVehicles.length; i++) {
			topRatedVehicles[i] = sortedVehicles[i];
		}
		
		return topRatedVehicles;
	}
	
	Vehicle[] getFeaturedVehicles() {
		Vehicle[] featuredVehicles = new Vehicle[availableCount];
		
		int count = 0;
		
		for(int i = 0; i < availableCount; i++) {
			if(availableVehicles[i].isFeatured) {
				featuredVehicles[count++] = availableVehicles[i];
			}
		}
		
		Vehicle[] finalResult = new Vehicle[count];
		
		for(int i = 0; i < count; i++) {
			finalResult[i] = featuredVehicles[i];
		}
		
		return finalResult;
	}
}



//	Class User
class User {
	String name;
	String contactNo;
	String username;
	String password;
	
	Vehicle[] wishlist = new Vehicle[10];
	Vehicle[] purchaseHistory = new Vehicle[50];
	
	int wishlistCount;
	int purchaseCount;
	
	// Constructor
	User(String name, String contactNo, String username, String password) {
		this.name = name;
		this.contactNo = contactNo;
		this.username = username;
		this.password = password;
		
		this.wishlistCount = 0;
		this.purchaseCount = 0;
	}
	
	void buyVehicle(VehicleInventory inventory, int vehicleIndex) {
		if(vehicleIndex < 1 || vehicleIndex > inventory.availableCount) {
			System.out.println("Invalid Vehicle Selection!!");
			return;
		}
		
		Vehicle selectedVehicle = inventory.availableVehicles[vehicleIndex - 1];
		
		Payment payment = new Payment();
		
		if(payment.verifyPayment(selectedVehicle.price - selectedVehicle.discount)) {
			inventory.removeVehicle(selectedVehicle);
			
			purchaseHistory[purchaseCount++] = selectedVehicle;
			
			System.out.println("Congratulations! You bought " + selectedVehicle.brand + " " + selectedVehicle.model + ".");
		}
		else {
			System.out.println("Payment Failed!!");
		}
	}
	
	void sellVehicle(VehicleInventory inventory, Vehicle vehicle) {
		inventory.addVehicle(vehicle);
		System.out.println("Vehicle Listed for Sale: " + vehicle.brand + " " + vehicle.model);
	}
	
	void rateVehicle(Vehicle vehicle, double rating) {
		if(rating >= 1 && rating <= 5) {
			vehicle.rateVehicle(rating);
			System.out.println("Thank you for rating the vehicle!!");
		}
		else {
			System.out.println("Invalid rating! Please rate between 1 to 5.");
		}
	}
	
	void addToWishlist(Vehicle vehicle) {
		if(wishlistCount < wishlist.length) {
			wishlist[wishlistCount++] = vehicle;
			System.out.println("Vehicle added to wishlist: " + vehicle.brand + " " + vehicle.model);
		}
		else {
			System.out.println("Wishlist is full!");
		}
	}
	
	void removeFromWishlist(int vehicleIndex) {
		if(vehicleIndex < 1 || vehicleIndex > wishlistCount) {
			System.out.println("Invalid Vehicle Selection!");
			return;
		}
		
		for(int i = vehicleIndex - 1; i < wishlistCount; i++) {
			wishlist[i] = wishlist[i + 1];
		}
		
		wishlist[--wishlistCount] = null;
		System.out.println("Vehicle removed from wishlist.");
	}
	
	void viewWishlist() {
		if(wishlistCount < 1 || wishlistCount > 10) {
			System.out.println("Your wishlist is empty!");
		}
		
		System.out.println("\nYour Wishlist:");
		for(int i = 0; i < wishlistCount; i++) {
			wishlist[i].displayDetails();
		}
	}
	
	void viewPurchaseHistory() {
		if(purchaseCount < 1 || purchaseCount > 50) {
			System.out.println("Your purchase history is empty!");
		}
		
		System.out.println("\nPurchase History:");
		for(int i = 0; i < purchaseCount; i++) {
			purchaseHistory[i].displayDetails();
		}
	}
	
	void updateProfile(String name, String contactNo, String password) {
		// Validate contact number
        if(!Authentication.isValidContact(contactNo)) {
            System.out.println("Invalid contact number! It must be 10 digits long and contain only numbers.");
            return;
        }

        // Validate password
        if(!Authentication.isValidPassword(password)) {
            System.out.println("Invalid password! It must be at least 8 characters long.");
            return;
        }
		
		this.name = name;
		this.contactNo = contactNo;
		this.password = password;
		
		System.out.println("Profile updated successfully.");
	}
}



//	Class Payment
class Payment {
	boolean verifyPayment(double amount) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Total Amount: $" + amount);
		System.out.print("Enter 1 to Confirm Payment: ");
		int confirm = sc.nextInt();
		
		return confirm == 1;
	}
	
	//	???
	void processPayment(double amount) {
		System.out.println("Payment of $" + amount + " Processed Successfully!");
	}
}



//	Class Admin
class Admin {
	void viewStatistics(VehicleInventory inventory) {
		System.out.println("\nInventory Statistics:");
		System.out.println("Total available vehicles: " + inventory.availableCount);
		System.out.println("Total sold vehicles: " + inventory.soldCount);
	}
	
	void addVehicleToInventory(VehicleInventory inventory, Vehicle vehicle) {
		inventory.addVehicle(vehicle);
		System.out.println("Vehicle added to inventory by admin: " + vehicle.brand + " " +vehicle.model);
	}
	
	void markVehicleAsFeatured(VehicleInventory inventory, int vehicleIndex) {
		if(vehicleIndex < 1 || vehicleIndex > inventory.availableCount) {
			System.out.println("Invalid vehicle selection!");
			return;
		}
		
		inventory.availableVehicles[vehicleIndex - 1].markAsFeatured();
		System.out.println("Vehicle marked as featured.");
	}
	
	void applyDiscountToVehicle(VehicleInventory inventory, int vehicleIndex, double discount) {
		if(vehicleIndex < 1 || vehicleIndex > inventory.availableCount) {
			System.out.println("Invalid vehicle selection!");
			return;
		}
		
		inventory.availableVehicles[vehicleIndex - 1].applyDiscount(discount);
		System.out.println("Discount applied to vehicle.");
	}
}



// Class Authentication
class Authentication {
	//	users[] can store upto 50 objects of type User
	User[] users = new User[50];	// Array to store users
	
	//	Instance to track the number of users
	int userCount = 0;
	
	// Method to validate contact number
    static boolean isValidContact(String contactNo) {
        if(contactNo.length() != 10) {
            return false;
        }
		
        for(char c : contactNo.toCharArray()) {
            if(!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    // Method to validate password
    static boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    void registerUser(String name, String contactNo, String username, String password) {
        // Validate contact number
        if(!isValidContact(contactNo)) {
            System.out.println("Invalid contact number! It must be 10 digits long and contain only numbers.");
            return;
        }

        // Validate password
        if(!isValidPassword(password)) {
            System.out.println("Invalid password! It must be at least 8 characters long.");
            return;
        }

		// Check if username already exists
        if(findUser(username) != null) {
            System.out.println("Username already exists. Please choose a different username.");
            return;
        }

        // Add user if validations pass
        if(userCount < users.length) {
            User newUser = new User(name, contactNo, username, password);
            users[userCount++] = newUser;
            System.out.println("User registered successfully!");
        }
		else {
            System.out.println("User limit reached. Cannot register more users.");
        }
    }

	User loginUser(String username, String password) {
		User user = findUser(username);
		
		if(user != null && user.password.equals(password)) {
			System.out.println("User logged in successfully!");
			return user;
		}
		else {
			System.out.println("Invalid username or password.");
			return null;
		}
	}
	
	User findUser(String username) {
		for(int i = 0; i < userCount; i++) {
			if (users[i].username.equals(username)) {
				return users[i];
			}
		}
		return null;
	}
}



// Class Run
class Run {
	public static void main(String args[]) {
		VehicleInventory inventory = new VehicleInventory();
		
		Authentication auth = new Authentication();
		
		Scanner sc = new Scanner(System.in);
		
		// Adding sample cars and bikes
		inventory.addVehicle(new Car("Toyota", "Corolla", 15000, "Petrol", 15.0, 2019, 5));
		inventory.addVehicle(new Car("Honda", "Civic", 18000, "Diesel", 18.0, 2020, 5));
		inventory.addVehicle(new Bike("Yamaha", "MT-15", 5000, "Petrol", 45.0, 2021, "Sports"));
		inventory.addVehicle(new Bike("Royal Enfield", "Classic 350", 7000, "Petrol", 35.0, 2022, "Cruiser"));
		
		User currentUser = null;
		
		Admin admin = new Admin();
		
		while(true) {
			if(currentUser == null) {
				System.out.println("\n1. Register");
				System.out.println("2. Login");
				System.out.println("3. Admin Login");
				System.out.println("4. Exit");
				System.out.print("Choose an option: ");
				int ch = sc.nextInt();
				sc.nextLine(); // Consume newline
				
				switch(ch) {
					case 1:
						System.out.print("Enter Name: ");
						String name = sc.nextLine();
						System.out.print("Enter Contact: ");
						String contact = sc.nextLine();
						System.out.print("Enter Username: ");
						String username = sc.nextLine();
						System.out.print("Enter Password: ");
						String password = sc.nextLine();
						auth.registerUser(name, contact, username, password);
						break;
						
					case 2:
						System.out.print("Enter Username: ");
						String loginUsername = sc.nextLine();
						System.out.print("Enter Password: ");
						String loginPassword = sc.nextLine();
						currentUser = auth.loginUser(loginUsername, loginPassword);
						break;
						
					case 3:
						System.out.print("Enter Admin Username: ");
						String adminUsername = sc.nextLine();
						System.out.print("Enter Admin Password: ");
						String adminPassword = sc.nextLine();
						
						if (adminUsername.equals("admin") && adminPassword.equals("admin123")) {
							currentUser = new User("Admin", "0000000000", "admin", "admin123");
							System.out.println("Admin logged in successfully!");
                        }
						else {
							System.out.println("Invalid admin credentials!");
						}
						break;
										
					case 4:
						System.out.println("\u001B[31;2mExiting the System...\u001B[0m");
						return;
						
					default:
						System.out.println("Invalid Option Selected!\nPlease enter valid choice...");
				}
			}
			else {
				if(currentUser.username.equals("admin")) {
					int ch = 0;
					do {
					System.out.println("\n1. View Statistics");
					System.out.println("2. Add Vehicle to Inventory");
					System.out.println("3. Mark Vehicle as Featured");
					System.out.println("4. Apply Discount to Vehicle");
					System.out.println("5. Logout");
					
					System.out.print("Enter your choice: ");
					ch = sc.nextInt();
					
					
					
					switch(ch) {
						case 1:
							admin.viewStatistics(inventory);
							
							int c2 = 0;
							
							do {
								System.out.println("\n1. View Available Vehicles");
								System.out.println("2. View Sold Vehicles");
								System.out.println("3. Go back");
								
								System.out.print("Enter your choice: ");
								c2 = sc.nextInt();
								
								switch(c2) {
									case 1:
										inventory.displayAvailableVehicles();
										break;
										
									case 2:
										inventory.displaySoldVehicles();
										break;
									
									case 3:
										System.out.println("Going back..");
										break;
										
									default:
										System.out.println("Invalid choice!");
								}
							} while(c2 != 3);
							break;
							
						case 2:
							System.out.print("Enter Vehicle Brand: ");
							String adminBrand = sc.nextLine();
							sc.nextLine();	//	consume line
							System.out.print("Enter Vehicle Model: ");
							String adminModel = sc.nextLine();
							System.out.print("Enter Price: $");
							double adminPrice = sc.nextDouble();
							sc.nextLine(); // Consume newline
							System.out.print("Enter Fuel Type: ");
							String adminFuelType = sc.nextLine();
							System.out.print("Enter Mileage: ");
							double adminMileage = sc.nextDouble();
							System.out.print("Enter Year: ");
							int adminYear = sc.nextInt();
							sc.nextLine(); // Consume newline
							System.out.print("Is this a Car or Bike? (Enter 'Car' or 'Bike'): ");
							String adminVehicleType = sc.nextLine();
                           
							if(adminVehicleType.equalsIgnoreCase("Car")) {
								System.out.print("Enter Number of Seats: ");
								int numberOfSeats = sc.nextInt();
								sc.nextLine(); // Consume newline
								
								Car adminCar = new Car(adminBrand, adminModel, adminPrice, adminFuelType, adminMileage, adminYear, numberOfSeats);
								
								admin.addVehicleToInventory(inventory, adminCar);
							}
							else if(adminVehicleType.equalsIgnoreCase("Bike")) {
								System.out.print("Enter Bike Type: ");
								String bikeType = sc.nextLine();
								
								Bike adminBike = new Bike(adminBrand, adminModel, adminPrice, adminFuelType, adminMileage, adminYear, bikeType);
							
								admin.addVehicleToInventory(inventory, adminBike);
							}
							else {
								System.out.println("Invalid Vehicle Type!");
							}
							break;
						
						case 3:
							inventory.displayAvailableVehicles();
							
							System.out.print("Enter Vehicle ID to Mark as Featured: ");
							int featuredVehicleId = sc.nextInt();
							
							admin.markVehicleAsFeatured(inventory, featuredVehicleId);
							break;
						
						case 4:
							inventory.displayAvailableVehicles();
							
							System.out.print("Enter Vehicle ID to Apply Discount: ");
							int discountVehicleId = sc.nextInt();
							System.out.print("Enter Discount Amount: $");
							double discount = sc.nextDouble();
						
							admin.applyDiscountToVehicle(inventory, discountVehicleId, discount);
							break;
							
						case 5:
							currentUser = null;
							System.out.println("\u001B[31;2mLogged out successfully!\u001B[0m");
							break;
							
						default:
							System.out.println("Invalid Option Selected!\nPlease enter valid choice...");
					}
					} while(ch != 5);
				}
				else {
					System.out.println("\n1. Buy a Vehicle");
					System.out.println("2. Sell a Vehicle");
					System.out.println("3. Search Vehicles by Brand");
					System.out.println("4. Filter Vehicles");
					System.out.println("5. View Top Rated Vehicles");
					System.out.println("6. Add Vehicle to Wishlist");
					System.out.println("7. View Wishlist");
					System.out.println("8. Remove Vehicle from Wishlist");
					System.out.println("9. View Purchase History");
					System.out.println("10. Update Profile");
					System.out.println("11. Logout");
				
					System.out.print("Choose an option: ");
					int ch = sc.nextInt();
					sc.nextLine(); // Consume newline
				
					switch(ch) {
						case 1:
							inventory.displayAvailableVehicles();
						
							System.out.print("Enter Vehicle ID to Buy: ");
							int vehicleId = sc.nextInt();
						
							currentUser.buyVehicle(inventory, vehicleId);
							
							break;
						
						case 2:
							System.out.print("Enter Vehicle Brand: ");
							String brand = sc.nextLine();
							System.out.print("Enter Vehicle Model: ");
							String model = sc.nextLine();
							System.out.print("Enter Price: $");
							double price = sc.nextDouble();
							sc.nextLine(); // Consume newline
							System.out.print("Enter Fuel Type: ");
							String fuelType = sc.nextLine();
							System.out.print("Enter Mileage: ");
							double mileage = sc.nextDouble();
							System.out.print("Enter Year: ");
							int year = sc.nextInt();
							sc.nextLine(); // Consume newline
						
							System.out.print("Is this a Car or Bike? (Enter 'Car' or 'Bike'): ");
							String vehicleType = sc.nextLine();
						
							if(vehicleType.equalsIgnoreCase("Car")) {
								System.out.print("Enter Number of Seats: ");
								int numberOfSeats = sc.nextInt();
								sc.nextLine(); // Consume newline
							
								Car newCar = new Car(brand, model, price, fuelType, mileage, year, numberOfSeats);
							
								currentUser.sellVehicle(inventory, newCar);
							}
							else if(vehicleType.equalsIgnoreCase("Bike")) {
								System.out.print("Enter Bike Type: ");
								String bikeType = sc.nextLine();
							
								Bike newBike = new Bike(brand, model, price, fuelType, mileage, year, bikeType);
							
								currentUser.sellVehicle(inventory, newBike);
							}
							else {
								System.out.println("Invalid Vehicle Type!");
							}
							break;
						
						case 3:
							System.out.print("Enter Brand to Search: ");
							String searchBrand = sc.nextLine();
						
							Vehicle[] searchResults = inventory.searchVehiclesByBrand(searchBrand);
						
							if(searchResults.length > 0) {
								System.out.println("\nSearch Results:");
							
								for(Vehicle vehicle : searchResults) {
									System.out.println("--------------------------------------------------");
									vehicle.displayDetails();
									System.out.println("--------------------------------------------------");
								}
							}
							else {
								System.out.println("No vehicles found for the given brand.");
							}
							break;
						
						case 4:
							int c = 0;
						
							do {
								System.out.println("1. Filter Vehicles by Price");
								System.out.println("2. Filter Vehicles by Year");
								System.out.println("3. Filter Vehicles by Mileage");
								System.out.println("4. Filter Vehicles by Fuel Type");
								System.out.println("5. Enter for go back...");
						
								System.out.print("Enter your choice: ");
								c = sc.nextInt();
						
								switch(c) {
									case 1:
										System.out.print("Enter Minimum Price: $");
										double minPrice = sc.nextDouble();
										System.out.print("Enter Maximum Price: $");
										double maxPrice = sc.nextDouble();
						
										Vehicle[] priceFilteredVehicles = inventory.filterVehiclesByPrice(minPrice, maxPrice);
						
										if(priceFilteredVehicles.length > 0) {
											System.out.println("\nFiltered Vehicles by Price:");
							
											for(Vehicle vehicle : priceFilteredVehicles) {
												System.out.println("--------------------------------------------------");
												vehicle.displayDetails();
												System.out.println("--------------------------------------------------");
											}
										}
										else {
											System.out.println("No vehicles found within the given price range.");
										}
										break;
								
									case 2:
										System.out.print("Enter Minimum Year: ");
										int minYear = sc.nextInt();
										System.out.print("Enter Maximum Year: ");
										int maxYear = sc.nextInt();
						
										Vehicle[] yearFilteredVehicles = inventory.filterVehiclesByYear(minYear, maxYear);
						
										if(yearFilteredVehicles.length > 0) {
											System.out.println("\nFiltered Vehicles by Year:");
							
											for(Vehicle vehicle : yearFilteredVehicles) {
												System.out.println("--------------------------------------------------");
												vehicle.displayDetails();
												System.out.println("--------------------------------------------------");
											}
										}
										else {
											System.out.println("No vehicles found within the given year range.");
										}
										break;
								
									case 3:
										System.out.print("Enter Minimum Mileage: ");
										double minMileage = sc.nextDouble();
										System.out.print("Enter Maximum Mileage: ");
										double maxMileage = sc.nextDouble();
							
										Vehicle[] mileageFilteredVehicles = inventory.filterVehiclesByMileage(minMileage, maxMileage);
							
										if(mileageFilteredVehicles.length > 0) {
											System.out.println("\nFiltered Vehicles by Mileage:");
							
											for(Vehicle vehicle : mileageFilteredVehicles) {
												System.out.println("--------------------------------------------------");
												vehicle.displayDetails();
												System.out.println("--------------------------------------------------");
											}
										}
										else {
											System.out.println("No vehicles found within the given mileage range.");
										}
										break;
						
									case 4:
										System.out.print("Enter Fuel Type: ");
										String filterFuelType = sc.nextLine();
										sc.nextLine();
							
										Vehicle[] fuelTypeFilteredVehicles = inventory.filterVehiclesByFuelType(filterFuelType);
							
										if(fuelTypeFilteredVehicles.length > 0) {
											System.out.println("\nFiltered Vehicles by Fuel Type:");
								
											for(Vehicle vehicle : fuelTypeFilteredVehicles) {
												System.out.println("--------------------------------------------------");
												vehicle.displayDetails();
												System.out.println("--------------------------------------------------");
											}
										}
										else {
											System.out.println("No vehicles found with the given fuel type.");
										}
										break;
									
									case 5:
										System.out.println("Going Back...");
										break;
								}
							} while(c != 5);
							break;
							
						case 5:
							System.out.print("Enter Limit for Top Rated Vehicles: ");
							int limit = sc.nextInt();
						
							Vehicle[] topRatedVehicles = inventory.getTopRatedVehicles(limit);
						
							if(topRatedVehicles.length > 0) {
								System.out.println("\nTop Rated Vehicles:");
							
								for(Vehicle vehicle : topRatedVehicles) {
									System.out.println("--------------------------------------------------");
									vehicle.displayDetails();
									System.out.println("--------------------------------------------------");
								}
							}
							else {
								System.out.println("No top-rated vehicles found.");
							}
							break;
						
						case 6:
							inventory.displayAvailableVehicles();
                        
							System.out.print("Enter Vehicle ID to Add to Wishlist: ");
							int vehicleIdToWishlist = sc.nextInt();
						
							if(vehicleIdToWishlist < 1 || vehicleIdToWishlist > inventory.availableCount) {
								System.out.println("Invalid Vehicle Selection!");
								break;
							}
						
							currentUser.addToWishlist(inventory.availableVehicles[vehicleIdToWishlist - 1]);
							break;
						
						case 7:
							currentUser.viewWishlist();
							break;
	
						case 8:
							currentUser.viewWishlist();
						
							System.out.print("Enter Wishlist ID to Remove: ");
							int wishlistId = sc.nextInt();
						
							currentUser.removeFromWishlist(wishlistId);
							break;
						
						case 9:
							currentUser.viewPurchaseHistory();
							break;
						
						case 10:
							System.out.print("Enter New Name: ");
							String newName = sc.nextLine();
							System.out.print("Enter New Contact: ");
							String newContact = sc.nextLine();
							System.out.print("Enter New Password: ");
							String newPassword = sc.nextLine();
						
							currentUser.updateProfile(newName, newContact, newPassword);
							break;
							
						case 11:
							currentUser = null;
							
							System.out.println("\u001B[31;2mLogged out successfully!\u001B[0m");
							break;
						
						default:
							System.out.println("Invalid Option Selected!\nPlease enter valid choice...");
					}
				}
			}
		}
	}
}
