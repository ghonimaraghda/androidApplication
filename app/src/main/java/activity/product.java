
package activity;

public class product {
    public int product_ID;
    public String product_name;
    public String description;
    public String image_url;


    public product(int product_ID, String product_name, String description, String image_url) {
        this.product_ID = product_ID;
        this.product_name=product_name;
        this.description = description;
        this.image_url = image_url;
    }

    public int getProduct_ID() {
        return product_ID;
    }

    public String getproduct_name() {
        return product_name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_url() {
        return image_url;
    }
}
