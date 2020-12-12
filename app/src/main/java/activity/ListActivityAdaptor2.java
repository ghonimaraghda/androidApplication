package activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginandregistration.R;

import java.util.List;

public class ListActivityAdaptor2 extends RecyclerView.Adapter<ListActivityAdaptor2.ListActivityViewHolder2> {
    public Context mCtx;
    public List<shop_product> shop_productList;
    public List<shop> shopList;
    String stringLatitude;
    String stringLongitude;

    public ListActivityAdaptor2(Context mCtx, List<shop_product> shop_productList,List<shop> shopList) {
        this.mCtx = mCtx;
        this.shop_productList = shop_productList;
        this.shopList = shopList;
    }

    @NonNull
    @Override
    public ListActivityViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view= inflater.inflate(R.layout.recycler_item2, null);
        ListActivityAdaptor2.ListActivityViewHolder2 holder= new ListActivityAdaptor2.ListActivityViewHolder2(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListActivityViewHolder2 holder, int position) {
        shop_product shop_product=shop_productList.get(position);
        shop shop=shopList.get(position);

        holder.shop_name.setText(shop.getShop_name());
        holder.price.setText(String.valueOf(shop_product.getPrice()));
        holder.specialOffer.setText(shop_product.getSpecial_offers());
        double latitude1= Double. valueOf(shop.getLatitude()) ;
        double longitude1= Double. valueOf(shop.getLongitude()) ;
        GPSTracker gpsTracker = new GPSTracker(mCtx);
        if (gpsTracker.getIsGPSTrackingEnabled())
        {
             stringLatitude = String.valueOf(gpsTracker.latitude);


             stringLongitude = String.valueOf(gpsTracker.longitude);

        }
        double latitude2= Double. valueOf(stringLatitude) ;
        double longitude2= Double. valueOf(stringLongitude) ;
        double distance= distance(latitude1,longitude1,latitude2,latitude2);




        holder.distance.setText(String.valueOf(distance));

    }

    @Override
    public int getItemCount() {

        return shopList.size();

    }

    class ListActivityViewHolder2 extends RecyclerView.ViewHolder{
        TextView shop_name,price,specialOffer,distance;

        public ListActivityViewHolder2(@NonNull View itemView) {
            super(itemView);
            shop_name=itemView.findViewById(R.id.shop_name);
            price=itemView.findViewById(R.id.price);
            specialOffer=itemView.findViewById(R.id.specialOffer);
            distance=itemView.findViewById(R.id.distance);
        }
    }
    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    public double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }






}
