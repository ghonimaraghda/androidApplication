package activity;

import java.util.Comparator;

public class
shop {
    public int shop_ID;
    public String shop_name;
    public String Latitude;
    public String longitude;
    public double distance;


    public shop(int shop_ID, String shop_name, String latitude, String longitude,double distance) {
        this.shop_ID = shop_ID;
        this.shop_name = shop_name;
        Latitude = latitude;
        this.longitude = longitude;
        this.distance=distance;
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

    public double getDistance() {
        return distance;
    }

    public static final Comparator<shop> BY_TITLE_DISTANCE = new Comparator<shop>() {
        @Override
        public int compare(shop o1, shop o2) {
            return (int) (o1.getDistance()-o2.getDistance());
        }
    };

}

