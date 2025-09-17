public class CPU extends PCPart
{
    private String architecture;
    private int coreCount;
    private float coreFrequency;

    public CPU(int productID, String brandName, String productName, double price, String arch, int coreCount, float coreFreq)
    {
        super(productID, brandName, productName, price);
        this.category = PartCategory.CPU;
        this.architecture = arch;
        this.coreCount = coreCount;
        this.coreFrequency = coreFreq;
    }

    public String GetArchitecture() { return architecture; }
    public int GetCoreCount() { return coreCount; }
    public float GetCoreFrequency() { return coreFrequency; }

    @Override
    public String GetDetails()
    {
        return String.format(
                "\t- CPU Architecture: %s \n" +
                "\t- Core Count: %d \n" +
                "\t- Core Frequency: %.2fGHZ\n",
                architecture, coreCount, coreFrequency);
    }
}
