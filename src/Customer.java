/*
    Author: Jye Curtis-Smith
    Version: 1.0
    Date: 3/11/2025
    Customer class, Customer is a user of the program who makes pc part orders.
 */

public class Customer
{
    private int id;
    private String fullName;
    private String emailAddress;

    public Customer(int id, String fullName, String emailAddress)
    {
        this.id = id;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
    }

    public int GetID() { return id; }
    public String GetName() { return fullName; }
    public String GetEmail() { return emailAddress; }

    public void SetID(int id) { this.id = id; }
    public void SetName(String name) { this.fullName = name; }
    public void SetEmail(String email) { this.emailAddress = email; }

    @Override
    public String toString() {
        return String.format("Customer:\n" +
                "- ID: %d \n" +
                "- Full Name: %s \n" +
                "- Email Address: %s \n",
                id, fullName, emailAddress
        );
    }
}
