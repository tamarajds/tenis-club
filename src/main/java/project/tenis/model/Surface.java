package project.tenis.model;

/**
 * Enumeration class for tennis court surface
 */
public enum Surface {
    GRASS,
    CLAY,
    HARD,
    ARTIFICIAL_GRASS;

    /**
     * Returns prices per minute for specific surface
     *
     * @return price per minute
     */
    public double getPricePerMinute(){
        double price;
        switch(this) {
            case GRASS:
                price = 2.1;
                break;
            case CLAY:
                price = 2.4;
                break;
            case HARD:
                price = 3;
                break;
            case ARTIFICIAL_GRASS:
                price = 2.5;
                break;
            default:
                price = 0;
                break;
        }
        return price;
    }
}
