package cruiseShipManager;

/**
 *
 * @author Matt Bradstreet
 */

public class Passenger {
    
    //fields
    private String firstName, surname;
    private double expenses;

    //Constructors
    public Passenger()
    {
        firstName = "empty";
        surname = "empty";
        expenses = 0.0;
    }

    public Passenger(String firstName, String surname, double expenses)
    {
        this.firstName = firstName;
        this.surname = surname;
        this.expenses = expenses;
    }    

    //Instance methods
    public void changeFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void changeSurname(String surname)
    {
        this.surname = surname;
    }

    public void changeExpenses(double expenses)
    {
        if (expenses > 0)
        {
            this.expenses = expenses;
        }
        else
        {
            expenses = 0;
        }
    }    

    public String getFirstName()
    {
        return firstName;
    }

    public String getSurname()
    {
        return surname;
    }    

    public String getName()
    {
        String name = (firstName + " " + surname);
        return name;
    }    

    public double getExpenses()
    {
        return expenses;
    }    
}

