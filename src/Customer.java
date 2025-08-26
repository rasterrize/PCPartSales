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
}
