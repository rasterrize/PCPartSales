/*
    Author: Jye Curtis-Smith
    Version: 1.0
    Date: 3/11/2025
    Case class, pc part used to physically hold the different parts of a pc. This class holds case unique data.
 */

public class Case extends PCPart
{
    private String formFactor;
    private boolean hasRGB;

    public Case(int productID, String brandName, String productName, double price, String formFactor, boolean hasRGB)
    {
        super(productID, brandName, productName, price);
        this.category = PartCategory.Case;
        this.formFactor = formFactor;
        this.hasRGB = hasRGB;
    }

    public String GetFormFactor() { return formFactor; }
    public boolean HasRGB() { return hasRGB; }

    @Override
    public String GetDetails()
    {
        return String.format(
                "\t- Form Factor: %s \n" +
                "\t- Has RGB: %s \n",
                formFactor, hasRGB);
    }
}
