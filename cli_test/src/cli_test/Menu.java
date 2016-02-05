package cli_test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
 
public class Menu {
    public Menu() {
    	showMainMenu();
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        try {
            int input = Integer.parseInt(br.readLine());
            
            switch (input) {
            case 1: calcScreen(); break;
            case 2: System.out.println("HELLO"); break;
            case 3: System.exit(1);
            default: System.out.println("Please choose 1-3");
            }
        } catch (IOException ioe) {
            System.out.println("IO error trying to read your input!\r\n");
            System.exit(1);
        }
        
    }
    
    
    public void showMainMenu(){
    	 System.out.println("Example Program");
         System.out.println("");
         System.out.println("Menu Options:");
         System.out.println("1. Calc");
         System.out.println("2. Say hello to me");
         System.out.println("3. Exit the program");
         System.out.println("");
         System.out.print("Please select an option");
    }
    
    public void calcScreen(){
    	System.out.println("Time To Add!");
    	System.out.println("please enter the first number");
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	int num1 = 0;
    	int num2 = 0;
    	try {
    		num1 = Integer.parseInt(br.readLine());
    		System.out.println(num1);
    		System.out.println("please enter the second number");
    		try {
    			num2 = Integer.parseInt(br.readLine());
    			System.out.println(num2);
    			System.out.println("this is the result");
    			CalcNoa calc = new CalcNoa(num1, num2);
    			System.out.println(calc.getResult());
    			
    		} catch (IOException ioe) {
                System.out.println("IO error trying to read your input!\r\n");
    		}
    		
    	} catch (IOException ioe) {
            System.out.println("IO error trying to read your input!\r\n");
    	}    
    }
    
    public static void main(String[] args) {
		new Menu();
	}
}
