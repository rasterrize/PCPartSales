public class PSU extends PCPart
{
    private int wattage;
    private String efficiency;

    public PSU(int wattage, String efficiency)
    {
        this.wattage = wattage;
        this.efficiency = efficiency;

        this.category = PartCategory.PSU;
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
