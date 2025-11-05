/*
    Author: Jye Curtis-Smith
    Version: 1.0
    Date: 3/11/2025
    RAM class, pc part used to store temporary data to run the pc. This class holds RAM unique data.
 */

public class RAM extends PCPart
{
    private int gigabytes;
    private int speed;

    public RAM(int productID, String brandName, String productName, double price, int gigabytes, int speed)
    {
        super(productID, brandName, productName, price);
        this.category = PartCategory.RAM;
        this.gigabytes = gigabytes;
        this.speed = speed;
    }

    public int GetGigabytes() { return gigabytes; }
    public int GetSpeed() { return speed; }

    @Override
    public String GetDetails()
    {
        return String.format(
                "\t- Capacity: %dGB \n" +
                "\t- Speed: %dMhz \n",
                gigabytes, speed);
    }
}
