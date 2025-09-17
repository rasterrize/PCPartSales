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
