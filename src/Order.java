import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Order {
    private int orderNm;
    private int customerID;
    private ArrayList<PCPart> parts;
    private String orderDate;
    private String orderTime;

    public Order(int orderNm, int customerID)
    {
        this.orderNm = orderNm;
        this.customerID = customerID;
        LocalDateTime currentDateTime = java.time.LocalDateTime.now();
        orderDate = currentDateTime.toLocalDate().toString();
        orderTime = currentDateTime.toLocalTime().truncatedTo(ChronoUnit.SECONDS).toString();
        parts = new ArrayList<>();
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

    @Override
    public String toString() {
        return String.format("Order:\n" +
                "- Order Number: %d \n" +
                "- Customer ID: %s \n" +
                "- Order Date: %s \n" +
                "- Order Time: %s \n",
                orderNm, customerID, orderDate, orderTime
        );
    }
}
