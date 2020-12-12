package activity;

import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
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
                                shop shops=new shop(shop_ID,shop_name,Latitude,Longitude);
                                for(int j=0;j<shop_productList.size();j++){
                                    shop_product product_ID= (activity.shop_product) shop_productList.get(j);
                                    int shopID= product_ID.getShop_ID();
                                    if(shopID==shop_ID){
                                        shopList.add(shops);
                                    }
                                }
                            }
                            adaptor=new ListActivityAdaptor2(ListActivity2.this,shop_productList,shopList);
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





}