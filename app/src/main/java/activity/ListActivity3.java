package activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginandregistration.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.AppConfig;
import app.AppController;
import helper.SQLiteHandler;
import helper.SessionManager;


public class ListActivity3 extends Activity {
    public static  final String CART2_URL="http://192.168.1.2/android_login_api/cart2.php";
    RecyclerView recyclerView3;
    Button productList;
    ListActivityAdaptor3 adaptor;
    List<cart> cartList;
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    public static  final String CARTUSER_URL="http://192.168.1.2/android_login_api/cartUser.php";
    List<cartUser> cartUserList;
    TextView cartUser1;
    String id1="ffg";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list3);
        productList = (Button) findViewById(R.id.productList);
        cartUserList= new ArrayList<>();
        productList.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        ListActivity.class);
                startActivity(i);
                finish();
            }
        });
        cartUser1=(TextView)findViewById(R.id.cartUser);

        //get user cart
        loadProducts();





        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());






        cartList= new ArrayList<>();
        recyclerView3=(RecyclerView)findViewById(R.id.recyclerView3);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));






    }

    public void loadProducts(){
        StringRequest stringRequest =new StringRequest(Request.Method.GET, CARTUSER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray cartUsers=new JSONArray(response);
                            for(int i=0;i<cartUsers.length();i++){
                                JSONObject productObject=cartUsers.getJSONObject(i);
                                int id =productObject.getInt("id");
                                String unique_id=productObject.getString("unique_id");
                                String name=productObject.getString("name");
                                String email=productObject.getString("email");
                                String encrypted_password=productObject.getString("encrypted_password");
                                String salt=productObject.getString("salt");
                                String created_at=productObject.getString("created_at");
                                String updated_at=productObject.getString("updated_at");
                                String address=productObject.getString("address");
                                String number=productObject.getString("number");
                                cartUser product=new cartUser(id,unique_id,name, email,encrypted_password,salt,address,number,created_at,updated_at);
                                cartUserList.add(product);
                            }
                            for(int j=0;j<cartUserList.size();j++){
                                cartUser cartUser=cartUserList.get(j);
                                String email=cartUser.getEmail();
                                String mail=LoginActivity.mail;
                                if(mail.equals(email)){
                                    id1=cartUser.getUnique_id();
                                    cartUser1.setText(cartUser.getUnique_id());
                                    // the shop name
                                    String shop="not set";
                                    Bundle extras= getIntent().getExtras();
                                    if(extras!=null){
                                        shop=extras.getString("shop");
                                    }
                                    // the price
                                    int price3=0;
                                    Bundle extras2= getIntent().getExtras();
                                    if(extras2!=null){
                                        price3=extras2.getInt("price");
                                    }
                                    //product name
                                    String product="not set";
                                    Bundle extras3= getIntent().getExtras();
                                    if(extras3!=null){
                                        product=extras3.getString("product");
                                    }
                                    if (!shop.isEmpty() && !product.isEmpty() && (price3!=0)&& !id1.isEmpty() ) {
                                        addCart(shop,product, String.valueOf(price3),id1);
                                        loadcarts();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),
                                                "Please enter your details!", Toast.LENGTH_LONG)
                                                .show();
                                    }


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
                        Toast.makeText(ListActivity3.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void addCart(final String shopName, final String productName,
                              final String Price, final String user_ID) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CART, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite

                        JSONObject cart = jObj.getJSONObject("cart");
                        String shopName = cart.getString("shopName");
                        String productName = cart.getString("productName");
                        String Price = cart.getString("Price");
                        String user_ID = cart.getString("user_ID");

                        // Inserting row in cart table
                        db.addCart(shopName, productName, Price,user_ID);

                        Toast.makeText(getApplicationContext(), "product successfully added to cart", Toast.LENGTH_LONG).show();

                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to cart url
                Map<String, String> params = new HashMap<String, String>();
                params.put("shopName", shopName);
                params.put("productName", productName);
                params.put("Price", Price);
                params.put("user_ID", user_ID);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }



    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    public void loadcarts(){
        StringRequest stringRequest =new StringRequest(Request.Method.GET, CART2_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray carts=new JSONArray(response);
                            for(int i=0;i<carts.length();i++){
                                JSONObject cartObject=carts.getJSONObject(i);
                                int id =cartObject.getInt("cart_ID");
                                String shopName=cartObject.getString("shopName");
                                String productName=cartObject.getString("productName");
                                int Price=cartObject.getInt("Price");
                                String user_ID=cartObject.getString("user_ID");
                                cart cart=new cart(id,shopName,productName,Price,user_ID);
                                if(user_ID.equals(id1)){
                                    cartList.add(cart);
                                }

                            }
                            //sort option


                            adaptor=new ListActivityAdaptor3(ListActivity3.this,cartList);
                            recyclerView3.setAdapter(adaptor);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListActivity3.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);

    }
}
