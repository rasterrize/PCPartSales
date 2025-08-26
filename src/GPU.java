public class GPU extends PCPart
{
    private int vramMB;
    private int coreCount;

    public GPU(int vramMB, int coreCount)
    {
        this.vramMB = vramMB;
        this.coreCount = coreCount;

        this.category = PartCategory.GPU;
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
