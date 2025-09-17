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
