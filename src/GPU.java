public class GPU extends PCPart
{
    private int vramMB;
    private int coreCount;

    public GPU(int productID, String brandName, String productName, double price, int vramMB, int coreCount)
    {
        super(productID, brandName, productName, price);
        this.category = PartCategory.GPU;
        this.vramMB = vramMB;
        this.coreCount = coreCount;
    }

    public int GetVRAM() { return vramMB; }
    public int GetCoreCount() { return coreCount; }

    @Override
    public String GetDetails()
    {
        return String.format(
                "\t- VRAM: %dMB \n" +
                "\t- Core Count: %d \n",
                vramMB, coreCount);
    }
}
