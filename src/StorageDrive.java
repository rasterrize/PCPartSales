public class StorageDrive extends PCPart
{
    private String driveType;
    private int storageGB;

    public StorageDrive(String type, int space)
    {
        this.driveType = type;
        this.storageGB = space;

        this.category = PartCategory.StorageDrive;
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
