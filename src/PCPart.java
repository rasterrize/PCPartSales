public abstract class PCPart {
    protected int partNumber;
    protected String brandName;
    protected String productName;
    protected PartCategory category;
    protected double price;

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
}
