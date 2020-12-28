package activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginandregistration.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListActivity2 extends AppCompatActivity {

    int productID;
    public static  final String SHOP_PRODUCT_URL="http://192.168.1.2/android_login_api/shop_product.php";
    public static  final String SHOP_URL="http://192.168.1.2/android_login_api/shop.php";
    RecyclerView recyclerView2;
    ListActivityAdaptor2 adaptor;
    List<shop_product> shop_productList;
    List<shop> shopList;
    Location l;
    SharedPreferences pref;
    public ListActivityAdaptor2.RecyclerViewClickListener listener;
    String name;
    public Context mCtx;
    String stringLatitude;
    String stringLongitude;
    double distance;
    String mSortSettings;
    List<shop> tmp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
        pref=getSharedPreferences("sort",MODE_PRIVATE);
        recyclerView2=(RecyclerView)findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        shop_productList= new ArrayList<>();
        shopList=new ArrayList<>();


        //get the product id clicked on as an integer saved in prouduct_ID
        productID=0;
        Bundle extras= getIntent().getExtras();
        if(extras!=null){
            productID=extras.getInt("product");
        }
        name="not set";
        Bundle extras2= getIntent().getExtras();
        if(extras!=null){
            name=extras2.getString("productname");
        }
        mSortSettings =pref.getString("sort","price"); //default sort according to price
        loadProducts();
        loadShops();




    }

    private void loadShops() {
        StringRequest stringRequest2 =new StringRequest(Request.Method.GET, SHOP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray shop=new JSONArray(response);
                            for(int i=0;i<shop.length();i++){
                                JSONObject shopObject=shop.getJSONObject(i);
                                int shop_ID =shopObject.getInt("shop_ID");
                                String shop_name=shopObject.getString("shop_name");
                                String Latitude=shopObject.getString("Latitude");
                                String Longitude=shopObject.getString("Longitude");
                                double latitude1= Double.valueOf(Latitude) ;
                                double longitude1=Double.valueOf(Latitude) ;
                                GPSTracker gpsTracker = new GPSTracker(mCtx);
                                //if (gpsTracker.getIsGPSTrackingEnabled())
                               // {
                                    // stringLatitude = String.valueOf(gpsTracker.latitude);


                                    // stringLongitude = String.valueOf(gpsTracker.longitude);

                                //}
                                double latitude2= Double.valueOf(String.valueOf(gpsTracker.latitude)) ;
                                double longitude2= Double. valueOf(String.valueOf(gpsTracker.longitude)) ;
                                distance= distance(latitude1,longitude1,latitude2,latitude2);
                                shop shops=new shop(shop_ID,shop_name,Latitude,Longitude,distance);
                                for(int j=0;j<shop_productList.size();j++){
                                    shop_product product_ID= (activity.shop_product) shop_productList.get(j);
                                    int shopID= product_ID.getShop_ID();
                                    if(shopID==shop_ID){
                                        shopList.add(shops);
                                    }
                                }
                            }

                            if(mSortSettings.equals("price")){
                                Collections.sort(shop_productList, activity.shop_product.BY_TITLE_PRICE);
                                tmp= new ArrayList<>();
                                for(int a=0;a<shop_productList.size();a++){
                                    shop_product sp=(activity.shop_product)shop_productList.get(a);
                                    int id12=sp.getShop_ID();
                                    for(int b=0;b<shopList.size();b++){
                                        shop s= (activity.shop)shopList.get(b);
                                        int ir=s.getShop_ID();
                                        if(ir==id12){
                                            tmp.add(s);

                                        }
                                    }
                                }
                                shopList=tmp;

                            }
                            if(mSortSettings.equals("distance")){
                                Collections.sort(shopList, activity.shop.BY_TITLE_DISTANCE);
                                ArrayList<shop_product> tmp= new ArrayList<>();
                                for(int a=0;a<shopList.size();a++){
                                    int id12=shopList.get(a).getShop_ID();
                                    for(int b=0;b<shop_productList.size();b++){
                                        if(shopList.get(b).getShop_ID()==id12){
                                            tmp.add(shop_productList.get(b));
                                        }
                                    }
                                }
                                shop_productList=tmp;


                            }
                            setOnClickListener();
                            adaptor=new ListActivityAdaptor2(ListActivity2.this,shop_productList,shopList,listener);
                            recyclerView2.setAdapter(adaptor);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListActivity2.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest2);

    }

    private void setOnClickListener() {
        listener= new ListActivityAdaptor2.RecyclerViewClickListener() {
            @Override
            public void OnClick(View v, int position) {
                Intent intent= new Intent(getApplicationContext(),ListActivity3.class);
                intent.putExtra("shop",shopList.get(position).getShop_name());
                intent.putExtra("price",shop_productList.get(position).getPrice());
                intent.putExtra("product",name);
                startActivity(intent);
            }
        };
    }

    public void loadProducts(){
        StringRequest stringRequest =new StringRequest(Request.Method.GET, SHOP_PRODUCT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray shop_product=new JSONArray(response);
                            for(int i=0;i<shop_product.length();i++){
                                JSONObject shop_productObject=shop_product.getJSONObject(i);
                                int shop_product_ID =shop_productObject.getInt("shop_product_ID");
                                int shop_ID=shop_productObject.getInt("shop_ID");
                                int product_ID=shop_productObject.getInt("product_ID");
                                int price=shop_productObject.getInt("price");
                                String special_offers=shop_productObject.getString("special_offers");
                                shop_product shop_products=new shop_product(shop_product_ID,shop_ID,product_ID,price,special_offers);
                                if(product_ID==productID){
                                    shop_productList.add(shop_products);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListActivity2.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if(id==R.id.sort){
            showSortDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSortDialog() {
        //options to display in dialog
        String[] options={"price","distance"};
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("sort By:");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){// price is clicked
                    SharedPreferences.Editor editor= pref.edit();
                    editor.putString("sort","price"); // sort is the key and price is the value
                    editor.apply();
                    recreate();

                }
                if(which == 1){//distace is clicked
                    SharedPreferences.Editor editor= pref.edit();
                    editor.putString("sort","distance"); // sort is the key and distance is the value
                    editor.apply();
                    recreate();

                }
            }
        });
        builder.create().show(); //show dialog
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