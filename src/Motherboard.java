public class Motherboard extends PCPart
{
    private String socketType;
    private int ramSlots;

    public Motherboard(String socketType, int ramSlots)
    {
        this.socketType = socketType;
        this.ramSlots = ramSlots;

        this.category = PartCategory.Motherboard;
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
