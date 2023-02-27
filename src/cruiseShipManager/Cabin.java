package cruiseShipManager;

/**
 *
 * @author Matt Bradstreet
 */

public class Cabin {
    
    //fields
    private int cabinNumber;
    private Passenger passenger1;
    private Passenger passenger2;
    private Passenger passenger3;

    //Constructors
    public Cabin(Passenger emptyPassenger)
    {
        cabinNumber = 0; 
        passenger1 = emptyPassenger;
        passenger2 = emptyPassenger;
        passenger3 = emptyPassenger;  
    }

    public Cabin(Passenger passenger1, Passenger passenger2, Passenger passenger3)
    {
        cabinNumber = 0;
        this.passenger1 = passenger1;
        this.passenger2 = passenger2;
        this.passenger3 = passenger3;      
    }
    
    //Instance methods
    public void setCabinNumber(int cabinNumber)
    {
        if (cabinNumber > 0)
        {
            this.cabinNumber = cabinNumber;
        }
        else
        {
            cabinNumber = 0;
        }
    }

    public void setPassenger(int passengerNumber, Passenger passenger)
    {
        if (passengerNumber == 1)
        {
            passenger1 = passenger;
        }
        else if (passengerNumber == 2)
        {
            passenger2 = passenger;
        }
        else if (passengerNumber == 3)
        {
            passenger3 = passenger;
        }        
    }    

    public int getCabinNumber()
    {
        return cabinNumber;
    }

    public Passenger getPassenger1()
    {
        return passenger1;
    }

    public Passenger getPassenger2()
    {
        return passenger2;
    }
    
    public Passenger getPassenger3()
    {
        return passenger3;
    }    

}

