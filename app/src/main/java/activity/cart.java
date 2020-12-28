package activity;

public class cart {
    public int cart_ID;
    public String shopName;
    public String productName;
    public int Price;
    public String user_ID;




    public cart(int cart_ID, String shopName, String productName, int Price, String user_ID) {
        this.cart_ID = cart_ID;
        this.shopName=shopName;
        this.productName = productName;
        this.Price = Price;
        this.user_ID=user_ID;
    }

    public int getCart_ID() {
        return cart_ID;
    }

    public String getShopName() {
        return shopName;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return Price;
    }
    public String getUser_ID() {
        return user_ID;
    }
}

