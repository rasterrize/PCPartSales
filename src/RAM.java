public class RAM extends PCPart
{
    private int gigabytes;
    private int speed;

    public RAM(int gigabytes, int speed)
    {
        this.gigabytes = gigabytes;
        this.speed = speed;

        this.category = PartCategory.RAM;
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
