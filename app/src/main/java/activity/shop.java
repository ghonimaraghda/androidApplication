package activity;

public class shop {
    public int shop_ID;
    public String shop_name;
    public String Latitude;
    public String longitude;

    public shop(int shop_ID, String shop_name, String latitude, String longitude) {
        this.shop_ID = shop_ID;
        this.shop_name = shop_name;
        Latitude = latitude;
        this.longitude = longitude;
    }

    public int getShop_ID() {
        return shop_ID;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}

