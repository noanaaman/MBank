package cli_screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainMenu {
	
	public MainMenu() {
		showMainMenu();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        try {
            int input = Integer.parseInt(br.readLine());
            
            switch (input) {
            case 1: {
            	System.out.println("All admin actions can be done through the swing GUI. Please use it, it's ugly but better.");
            	showMainMenu();             }
            case 2: new ClientMain();
            case 3: System.exit(1);
            default: System.out.println("You have't entered a correct option. Please choose 1-3");
            }
        } catch (IOException ioe) {
            System.out.println("System Error!");
            System.exit(1);
            
        }
        
        
	}
	
	public void showMainMenu(){
		System.out.println("");
		System.out.println("Welcome to MBank CLI Client!");
        System.out.println("Main Menu");
        System.out.println("---------------------------");
        System.out.println("1. I am an MBank administrator");
        System.out.println("2. I am an MBank client");
        System.out.println("3. Exit the program");
        System.out.println("----------------------------");
        System.out.println("");
        System.out.print("Please select an option from 1-3\r\n");
        System.out.println("");
        System.out.println("");
	}
}
