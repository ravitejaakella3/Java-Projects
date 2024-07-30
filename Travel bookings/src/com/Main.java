package com;

import com.service.UserService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import com.model.routelist;

public class Main {
	private static UserService userService = new UserService();
	private static routelist Routelist=new routelist();
	
    

    public static void main(String[] args) throws SQLException {
    	routelist.listOfRoutes();
    	if(displayCompanyLogo()) {
    		showMenuOptions();
    	}else {
    		System.out.println("logo was not found");
    	}
    }
	
	private static boolean displayCompanyLogo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\C Comapny logo.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            return true; // Logo loaded successfully
        } catch(IOException e) {
            System.err.println("Error reading company logo file: " + e.getMessage());
            return false; // Logo loading failed
        }
    }
	
	private static void showMenuOptions() {
		Scanner scanner = new Scanner(System.in);
		int choice;
		boolean running = true;

		while (running) {
			System.out.println("\nMenu Options:");
			System.out.println("1. New Admin User Registration");
			System.out.println("2. Login");

			System.out.print("Enter your choice: ");
			try {
				choice = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a number.");
				continue;
			}

			switch (choice) {
			case 1:
				userService.registerNewAdmin();
				break;
			case 2:
				userService.login();
				running=false;
				break;
			default:
				System.out.println("Invalid choice. Please enter a correct option.");
				break;
			}
		}
		scanner.close();  // Close the scanner when we're done with it
	}
}

