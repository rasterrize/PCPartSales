/*
    Author: Jye Curtis-Smith
    Version: 1.0
    Date: 3/11/2025
    Motherboard class, pc part used as a main pcb to connect multiple parts the pc. This class holds Motherboard unique data.
 */

public class Motherboard extends PCPart
{
    private String socketType;
    private int ramSlots;

    public Motherboard(int productID, String brandName, String productName, double price, String socketType, int ramSlots)
    {
        super(productID, brandName, productName, price);
        this.category = PartCategory.Motherboard;
        this.socketType = socketType;
        this.ramSlots = ramSlots;
    }

    public String GetSocketType() { return socketType; }
    public int GetRamSlots() { return ramSlots; }

    @Override
    public String GetDetails()
    {
        return String.format(
                "\t- Socket Type: %s \n" +
                "\t- Ram Slots: %d \n",
                socketType, ramSlots);
    }
}
