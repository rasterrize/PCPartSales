/*
    Author: Jye Curtis-Smith
    Version: 1.0
    Date: 3/11/2025
    PSU class, pc part used to supply power, aka Power Supply Unit. This class holds PSU unique data.
 */

public class PSU extends PCPart
{
    private int wattage;
    private String efficiency;

    public PSU(int productID, String brandName, String productName, double price, int wattage, String efficiency)
    {
        super(productID, brandName, productName, price);
        this.category = PartCategory.PSU;
        this.wattage = wattage;
        this.efficiency = efficiency;
    }

    public int GetWattage() { return wattage; }
    public String GetEfficiency() { return efficiency; }

    @Override
    public String GetDetails()
    {
        return String.format(
                "\t- Wattage: %d \n" +
                "\t- Efficiency: %s \n",
                wattage, efficiency);
    }
}
