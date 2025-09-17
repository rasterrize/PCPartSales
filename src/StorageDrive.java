public class StorageDrive extends PCPart
{
    private String driveType;
    private int storageGB;

    public StorageDrive(int productID, String brandName, String productName, double price, String type, int space)
    {
        super(productID, brandName, productName, price);
        this.category = PartCategory.StorageDrive;
        this.driveType = type;
        this.storageGB = space;
    }

    public String GetDriveType() { return driveType; }
    public int GetStorageSpace() { return storageGB; }

    @Override
    public String GetDetails()
    {
        return String.format(
                "\t- Drive Type: %s \n" +
                "\t- Storage Space: %dGB \n",
                driveType, storageGB);
    }
}
