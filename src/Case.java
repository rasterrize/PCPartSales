public class Case extends PCPart
{
    private String formFactor;
    private boolean hasRGB;

    public Case(String formFactor, boolean hasRGB)
    {
        this.formFactor = formFactor;
        this.hasRGB = hasRGB;

        this.category = PartCategory.Case;
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
