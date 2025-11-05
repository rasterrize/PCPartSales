/*
    Author: Jye Curtis-Smith
    Version: 1.0
    Date: 3/11/2025
    GPU class, pc part used to run parallel computations on a pc, aka Graphics Processing Unit.
    This class holds GPU unique data.
 */

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
