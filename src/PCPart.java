/*
    Author: Jye Curtis-Smith
    Version: 1.0
    Date: 3/11/2025
    Abstract base class for a pc part. Stores common data shared between every pc part (brand name, product id, etc)
 */

public abstract class PCPart {
    protected int partNumber;
    protected String brandName;
    protected String productName;
    protected PartCategory category;
    protected double price;

    protected PCPart(int partNumber, String brandName, String productName, double price)
    {
        this.partNumber = partNumber;
        this.brandName = brandName;
        this.productName = productName;
        this.price = price;
    }

    public int GetPartNumber() { return partNumber; }
    public String GetBrandName() { return brandName; }
    public String GetProductName() { return productName; }
    public PartCategory GetCategory() { return category; }
    public double GetPrice() { return price; }

    public void SetPartNumber(int partNumber) { this.partNumber = partNumber; }
    public void SetBrandName(String brandName) { this.brandName = brandName; }
    public void SetProductName(String productName) { this.productName = productName; }
    public void SetPrice(double price) { this.price = price; }

    public abstract String GetDetails();

    @Override
    public String toString() {
        return String.format("PCPart:\n" +
                "- Part Number: %d \n" +
                "- Brand Name: %s \n" +
                "- Product Name: %s \n" +
                "- Category: %s \n" +
                "- Price: %.2f \n" +
                "Unique part details: \n" +
                "%s\n",
                partNumber, brandName, productName, category.toString(), price, GetDetails()
        );
    }
}
