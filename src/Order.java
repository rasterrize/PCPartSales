import java.util.ArrayList;

public class Order {
    private int orderNm;
    private int customerID;
    private ArrayList<PCPart> parts;
    private String orderDate;
    private String orderTime;

    public Order(int orderNm, int customerID)
    {
        parts = new ArrayList<>();
        this.orderNm = orderNm;
        this.customerID = customerID;
    }

    public int GetOrderNumber() { return orderNm; }
    public int GetCustomerID() { return customerID; }
    public ArrayList<PCPart> GetParts() { return parts; }
    public String GetOrderDate() { return orderDate; }
    public String GetOrderTime() { return orderTime; }

    public void SetOrderNumber(int num) { orderNm = num; }
    public void SetCustomerID(int id) { customerID = id; }
    public void SetOrderDate(String date) { orderDate = date; }
    public void SetOrderTime(String time) { orderTime = time; }
    public void AddPart(PCPart part) { parts.add(part); }
}
