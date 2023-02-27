package cruiseShipManager;

import java.util.*;
import java.io.*;

/** 
 * Program for tracking customers to cabins for a Cruise Ship
 * @author Matt Bradstreet
 * @version 1.0
*/

public class cruiseShipManager {

    static final int FIRST_CABIN = 1, MAX_CABINS = 12, PASSENGERS_PER_CABIN = 3;
    static final int LENGTH_OF_QUEUE = 5;
    static int frontOfQueue = 0, endOfQueue = 0;
    static Cabin[] cruiseShip = new Cabin[MAX_CABINS + 1];
    static Passenger[] waitingList = new Passenger[LENGTH_OF_QUEUE];

    public static void main(String[] args) 
    {
        // calls method for initialising cabins
        populateCabins();

        // loops until Q entered for Quit
        boolean quitKey = false;

        while (quitKey == false)
        {
            Scanner in = new Scanner(System.in);

            // calls menu method to display menu
            String input = menu(in);

            // calls correct method relating to input from user
            if (input.equalsIgnoreCase("A"))
            {
                addCabin(in);
            }
            else if (input.equalsIgnoreCase("V"))
            {
                viewAll();
            }
            else if (input.equalsIgnoreCase("E"))
            {
                displayEmpty();
            }   
            else if (input.equalsIgnoreCase("D"))
            {
                deleteCabin(in);
            } 
            else if (input.equalsIgnoreCase("F"))
            {
                findCabin(in);
            }   
            else if (input.equalsIgnoreCase("S"))
            {
                storeData();
            }
            else if (input.equalsIgnoreCase("L"))
            {
                loadData();
            }
            else if (input.equalsIgnoreCase("O"))
            {
                viewAlpha();
            }
            else if (input.equalsIgnoreCase("T"))
            {
                printExpenses(in);
            }
            else if (input.equalsIgnoreCase("Q"))
            {
                quitKey = true;
            }
            else if (input.equalsIgnoreCase("W"))                   
            {
                //for testing queue/waiting list by filling all cabins with test passengers
                testQueue();
            }
            else 
            {
                System.out.println("Key not recognised, please select an appropriate key from the menu");
            }
        }
    }  


    private static void populateCabins()
    {
        /**
        * Method for initialising cabins in Cruise Ship
        */

        Passenger emptyPassenger = new Passenger();

        for (int i = FIRST_CABIN; i <= MAX_CABINS; i++)
        {
            Cabin cabin = new Cabin(emptyPassenger);
            cabin.setCabinNumber(i);
            cruiseShip[i] = cabin;
        }
        System.out.println("Cabins initialised");
    }


    private static String passengerName(int passengerNumber, Cabin cruiseShip[], int i)
    {
        /** 
        * Method for providing the passenger names from cabins
        * @param passengerNumber for selecting which passenger (1,2,3) to print
        * @param cruiseShip[] array for cabins
        * @param i the index for the cruiseShip[] array
        * @return passengers name
        */        

        String passengerName = "";

        if (passengerNumber == 1)
        {
            Passenger passenger = cruiseShip[i].getPassenger1();
            passengerName = passenger.getName();
        }
        else if (passengerNumber == 2)
        {
            Passenger passenger = cruiseShip[i].getPassenger2();
            passengerName = passenger.getName();
        }
        else if (passengerNumber == 3)
        {        
            Passenger passenger = cruiseShip[i].getPassenger3();
            passengerName = passenger.getName();
        }
        
        if (passengerName.contains("empty"))
        {
            passengerName = "empty";
        }
         
        return passengerName;
    }


    private static String menu(Scanner in)
    {
        /** 
        * Method for printing menu to user and taking user input
        * @param in Scanner object for taking user input
        * @return user input
        */

        System.out.println();
        System.out.println("Menu ");
        System.out.println("A: Add Customer to Cabin");
        System.out.println("V: View All Cabins");
        System.out.println("E: Display Empty Cabins");
        System.out.println("D: Delete Customer from Cabin");
        System.out.println("F: Find cabin from customer name");
        System.out.println("S: Store program data into file");
        System.out.println("L: Load program data from file");
        System.out.println("O: View passengers Ordered alphabetically by name");
        System.out.println("T: Print expenses");
        System.out.println("Q: Quit Program");
        System.out.println("W: For testing queue/waiting list - fills all Cruise Ship Cabins with Passengers");

        System.out.print("Please enter key from above menu: ");
        String input = in.next();

        return input;
    }


    private static void addCabin(Scanner in)
    {
        /** 
        * Method for adding customer name to cabin
        * @param in Scanner object for taking user input
        */
      
        System.out.println("Add Customer to Cabin");

        Boolean full = cruiseShipFull();

        if (full == true)
        {
            System.out.println("Cruise Ship Full, adding Passenger to Waiting List");
            Passenger passenger = getDetails();
            addQueue(passenger);
        }
        else
        {
            System.out.print("Enter Cabin Number (" + FIRST_CABIN + "-" + MAX_CABINS + "): ");
        
            int inputCabin = -1;

            try
            {
                inputCabin = in.nextInt();
            }
            catch (InputMismatchException exception)
            {
                System.out.println("Please enter a valid cabin number");
            }     
            
            for (int i = FIRST_CABIN; i <= MAX_CABINS; i++)
            {
                if (inputCabin == cruiseShip[i].getCabinNumber())
                {
                    Passenger passenger = getDetails();

                    String passenger1Name = passengerName(1, cruiseShip, i);
                    String passenger2Name = passengerName(2, cruiseShip, i);
                    String passenger3Name = passengerName(3, cruiseShip, i);

                    if (passenger1Name == "empty")
                    {
                        cruiseShip[i].setPassenger(1, passenger);
                    }
                    else if (passenger2Name == "empty")
                    {
                        cruiseShip[i].setPassenger(2, passenger);
                    }
                    else if (passenger3Name == "empty")
                    {
                        cruiseShip[i].setPassenger(3, passenger);
                    }
                    else
                    {
                        System.out.println("Selected Cabin is Full");
                    }
                }
            }
        }
    }

    
    private static void viewAll()
    {
        /** 
        * Method for viewing all cabins
        */

        System.out.println("View All Cabins");

        for (int i = FIRST_CABIN; i <= MAX_CABINS; i++)
        {
            int cabin = cruiseShip[i].getCabinNumber();

            String passenger1Name = passengerName(1, cruiseShip, i);
            String passenger2Name = passengerName(2, cruiseShip, i);
            String passenger3Name = passengerName(3, cruiseShip, i);                   

            System.out.println("Cabin " + cabin + " - " + passenger1Name + ", " + passenger2Name + ", " + passenger3Name);
        }
    } 
    

    private static void displayEmpty()
    {
        /** 
        * Method for viewing all empty cabins
        */

        System.out.println("Display Empty Cabins");

        for (int i = FIRST_CABIN; i <= MAX_CABINS; i++)
        {
            String passenger1Name = passengerName(1, cruiseShip, i);
            String passenger2Name = passengerName(2, cruiseShip, i);
            String passenger3Name = passengerName(3, cruiseShip, i);                 

            if (passenger1Name == "empty" && passenger2Name == "empty" && passenger3Name == "empty")
            {
                System.out.println("Cabin " + i + " - " + passenger1Name + ", " + passenger2Name + ", " + passenger3Name);
            }
        }
    }    


    private static void deleteCabin(Scanner in)
    {
        /** 
        * Method for deleting customer from cabin
        * @param in Scanner object for taking user input
        */

        System.out.println("Delete Customer from Cabin");

        System.out.print("Enter Cabin Number (" + FIRST_CABIN + "-" + MAX_CABINS + "): ");
      
        //initialises input in case of incorrect user input
        int inputCabin = -1;

        try
        {
            inputCabin = in.nextInt();
        }
        catch (InputMismatchException exception)
        {
            System.out.println("Please enter a valid cabin number");
        }     

        for (int i = FIRST_CABIN; i <= MAX_CABINS; i++)
        {
            if (inputCabin == cruiseShip[i].getCabinNumber())
            {
                String passenger1Name = passengerName(1, cruiseShip, i);
                String passenger2Name = passengerName(2, cruiseShip, i);
                String passenger3Name = passengerName(3, cruiseShip, i);  

                System.out.println("Cabin: " + inputCabin);
                System.out.println("1. " + passenger1Name);
                System.out.println("2. " + passenger2Name);
                System.out.println("3. " + passenger3Name);
                System.out.print("Please select 1, 2 or 3 to delete passenger from cabin: ");
                int deletedPassenger = in.nextInt();

                if (deletedPassenger != 1 && deletedPassenger != 2 && deletedPassenger != 3)
                {
                    System.out.println("Input not recognised");
                }
                else 
                {
                    System.out.println("Passenger deleted from Cabin " + i);
                    System.out.println("Checking Waiting List...");

                    Passenger passenger = takeQueue();

                    cruiseShip[i].setPassenger(deletedPassenger, passenger);
                }
            }
        }        

    }  
    

    private static void findCabin(Scanner in)
    {
        /** 
        * Method for finding cabin with customer name
        * @param in Scanner object for taking user input
        */

        System.out.println("Find cabin from Customers name");

        System.out.print("Enter Customers Name: ");
        in.nextLine();
        String inputName = in.nextLine();

        // initialises pass in case of customer name not found
        boolean pass = false;

        for (int i = FIRST_CABIN; i <= MAX_CABINS; i++)
        {
            String passenger1Name = passengerName(1, cruiseShip, i);
            String passenger2Name = passengerName(2, cruiseShip, i);
            String passenger3Name = passengerName(3, cruiseShip, i); 

            if (passenger1Name.contains(inputName) || passenger2Name.contains(inputName) || passenger3Name.contains(inputName))
            {
                System.out.println("Cabin " + i + " - " + passenger1Name + ", " + passenger2Name + ", " + passenger3Name);
                pass = true;
            }
        } 

        if (pass == false)
        {
           System.out.println("Customer Name not found");
        }
    }  
    

    private static void storeData()
    {
        /** 
        * Method for creating text file with data from program
        */

        System.out.println("Store Data in File");

        try
        {
            PrintWriter dataFile = new PrintWriter("file.txt");

            for (int i = FIRST_CABIN; i < cruiseShip.length; i++)
            {
                String passenger1Name = passengerName(1, cruiseShip, i);
                String passenger2Name = passengerName(2, cruiseShip, i);
                String passenger3Name = passengerName(3, cruiseShip, i);

                dataFile.println("Cabin " + i + " - " + passenger1Name + ", " + passenger2Name + ", " + passenger3Name);
            }
            dataFile.close();
        }
        catch (FileNotFoundException exception)
        {
            System.out.println("Error when writing file, file not created");
        }
    }  
    

    private static void loadData()
    {
        /** 
        * Method for reading text file
        */

        System.out.println("Load data from File");    

        try
        {
            File dataFile = new File("file.txt");
            Scanner file = new Scanner(dataFile);

            for (int i = FIRST_CABIN; i < cruiseShip.length; i++)
            {
                System.out.println(file.nextLine());
            }
            file.close();
        }
        catch (FileNotFoundException exception)
        {
            System.out.println("Error when reading file, file does not exist");
        }
    }  
    

    private static void viewAlpha()
    {
        /** 
        * Method for viewing customer names alphabetically
        */

        System.out.println("View all alphabetically");

        int numberOfPassengers = MAX_CABINS * PASSENGERS_PER_CABIN;
        String[] sortedArray = new String[numberOfPassengers + 1];
        int arrayPosition = 1;

        for (int i = FIRST_CABIN; i <= MAX_CABINS; i++)
        {  
            for (int cabinPosition = 1; cabinPosition <= 3; cabinPosition++)
            {
                String passengerName = passengerName(cabinPosition, cruiseShip, i);
                sortedArray[arrayPosition] = passengerName;
                arrayPosition++;
            }
        }

        for (int j = FIRST_CABIN; j < numberOfPassengers; j++)
        {
           for (int i = FIRST_CABIN; i < numberOfPassengers; i++)
           {
              if (sortedArray[i].compareToIgnoreCase(sortedArray[i + 1]) > 0)
              {
                 String temp = sortedArray[i + 1];
                 sortedArray[i + 1] = sortedArray[i];
                 sortedArray[i] = temp;
              }
           }
        }
      
        for (int i = FIRST_CABIN; i <= numberOfPassengers; i++)
        {
            if (sortedArray[i] != "empty")
            {
                System.out.println(sortedArray[i]);
            }
        }
    }   
    

    private static void printExpenses(Scanner in)
    {
        /** 
        * Method for viewing customers expenses, individual and total
        */        

        System.out.print("Print expenses for customer (1) or print total expenses (2): ");
        int input = in.nextInt();
        in.nextLine();

        double passengerExpenses = 0;

        //prints expenses for individual customer
        if (input == 1)
        {
            System.out.println("Please enter Passenger's name: ");
            String inputName = in.nextLine();

            for (int i = FIRST_CABIN; i <= MAX_CABINS; i++)
            {  
                String passenger1Name = passengerName(1, cruiseShip, i);
                String passenger2Name = passengerName(2, cruiseShip, i);
                String passenger3Name = passengerName(3, cruiseShip, i);

                if (passenger1Name.equalsIgnoreCase(inputName))
                {
                    Passenger passenger = cruiseShip[i].getPassenger1();
                    passengerExpenses = passenger.getExpenses();
                }
                else if (passenger2Name.equalsIgnoreCase(inputName))
                {
                    Passenger passenger = cruiseShip[i].getPassenger2();
                    passengerExpenses = passenger.getExpenses();
                }
                else if (passenger3Name.equalsIgnoreCase(inputName))
                {
                    Passenger passenger = cruiseShip[i].getPassenger3();
                    passengerExpenses = passenger.getExpenses();
                }
            }
            System.out.println(passengerExpenses);
        }
        //prints total expenses for all customers
        else if (input == 2)
        {
            Double total = 0.0;
             
            for (int i = FIRST_CABIN; i <= MAX_CABINS; i++)
            {  
                Passenger passenger1 = cruiseShip[i].getPassenger1();
                Passenger passenger2 = cruiseShip[i].getPassenger2();
                Passenger passenger3 = cruiseShip[i].getPassenger3();

                passengerExpenses = passenger1.getExpenses() + passenger2.getExpenses() + passenger3.getExpenses();
                total = total + passengerExpenses;
            }
            System.out.println("Total Expenses: " + total);
        }
        else
        {
            System.out.println("Incorrect input, please try again");
        }
    }


    private static Passenger getDetails()
    {
        /** 
        * Method for taking input from user, in relation to customers
        * @return passenger object
        */

        Scanner in = new Scanner(System.in);

        System.out.print("Please enter customer's first name: ");
        String customerFirstName = in.next();

        System.out.print("Please enter customer's surname: ");
        String customerSurname = in.next();

        System.out.print("Please enter customer's expenses: ");
        Double customerExpenses = in.nextDouble();                

        Passenger passenger = new Passenger(customerFirstName, customerSurname, customerExpenses);

        return passenger;
    }


    private static Boolean cruiseShipFull()
    {
        /** 
        * Method for checking whether all cabins are full
        * @return boolean true or false on whether all cabins are full
        */

        int cabinFull = 0;

        for (int i = FIRST_CABIN; i <= MAX_CABINS; i++)
        {
            String passenger1Name = passengerName(1, cruiseShip, i);
            String passenger2Name = passengerName(2, cruiseShip, i);
            String passenger3Name = passengerName(3, cruiseShip, i);                 

            if (passenger1Name != "empty" && passenger2Name != "empty" && passenger3Name != "empty")
            {
                cabinFull++;
            }
        }    
        
        if (cabinFull == MAX_CABINS)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    private static void testQueue()
    {
        /** 
        * Method for filling all cabins with test passengers, used for testing waiting list/queue
        */

        System.out.println("All cabins now filled with test passengers");

        for (int i = FIRST_CABIN; i <= MAX_CABINS; i++)
        {
            int inputCabin = i;

            if (inputCabin == cruiseShip[i].getCabinNumber())
            {
                Passenger passenger = new Passenger("Test", "McTest", 0.01);
                cruiseShip[i].setPassenger(1, passenger);
                cruiseShip[i].setPassenger(2, passenger);
                cruiseShip[i].setPassenger(3, passenger);
            }
        }        
    }


    private static void addQueue(Passenger passenger)
    {
        /** 
        * Method for adding passengers to waiting list queue when all cabins are full
        * @param passenger object
        */

        if (endOfQueue == (frontOfQueue - 1))
        {
            System.out.println("Waiting List is Full");
        }
        else if (endOfQueue == waitingList.length && frontOfQueue == 0)
        {
            System.out.println("Waiting List is Full");
        }
        else if (endOfQueue == waitingList.length && frontOfQueue != 0)
        {
            //loops queue back to start when out of bounds
            endOfQueue = 0;
            waitingList[endOfQueue] = passenger;
            System.out.println(passenger.getName() + " added to Waiting List");
            endOfQueue++;
        }
        else
        {
            //adds passenger to queue
            waitingList[endOfQueue] = passenger;
            System.out.println(passenger.getName() + " added to Waiting List");
            endOfQueue++;
        }

    }


    private static Passenger takeQueue()
    {
        /** 
        * Method for taking passengers from Waiting List queue when cabins become available
        * @return passenger object
        */

        Passenger passenger;

        if (frontOfQueue == endOfQueue)
        {
            //creates an empty passenger
            System.out.println("Waiting List is empty");
            passenger = new Passenger();
        }
        else if (frontOfQueue == waitingList.length && endOfQueue == 0)
        {
            //creates an empty passenger
            System.out.println("Waiting List is empty");
            passenger = new Passenger();
        }
        else if (frontOfQueue == waitingList.length && endOfQueue != 0)
        {
            //loops queue back to start when out of bounds
            frontOfQueue = 0;
            passenger = waitingList[frontOfQueue];
            System.out.println(passenger.getName() + " added to Cabin");
            frontOfQueue++;
        }
        else
        {
            //returns first passenger in queue
            passenger = waitingList[frontOfQueue];
            System.out.println(passenger.getName() + " added to Cabin");
            frontOfQueue++;
        }
        return passenger;
    }
}
