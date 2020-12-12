package activity;

public class shop_product {
    private int shop_product_ID;
    private int shop_ID;
    private int product_ID;
    private int price;
    private String special_offers;


    public shop_product(int shop_product_ID, int shop_ID, int product_ID, int price, String special_offers) {
        this.shop_product_ID = shop_product_ID;
        this.shop_ID = shop_ID;
        this.product_ID = product_ID;
        this.price = price;
        this.special_offers = special_offers;
    }

    public int getShop_product_ID() {
        return shop_product_ID;
    }

    public int getShop_ID() {
        return shop_ID;
    }

    public int getProduct_ID() {
        return product_ID;
    }

    public int getPrice() {
        return price;
    }

    public String getSpecial_offers() {
        return special_offers;
    }
}
