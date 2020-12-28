package activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
import java.util.List;

public class ListActivity extends AppCompatActivity {
    public static  final String PRODUCT_URL="http://192.168.1.2/android_login_api/product.php";
    RecyclerView recyclerView;
    ListActivityAdaptor adaptor;
    List<product> productList;
    public ListActivityAdaptor.RecyclerViewClickListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        productList= new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadProducts();


    }
    public void loadProducts(){
        StringRequest stringRequest =new StringRequest(Request.Method.GET, PRODUCT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray products=new JSONArray(response);
                            for(int i=0;i<products.length();i++){
                                JSONObject productObject=products.getJSONObject(i);
                                int id =productObject.getInt("product_ID");
                                String product_name=productObject.getString("product_name");
                                String description=productObject.getString("description");
                                String image_url=productObject.getString("image_url");
                                product product=new product(id,product_name,description,image_url);
                                productList.add(product);
                            }


                            setOnClickListener();
                            adaptor=new ListActivityAdaptor(ListActivity.this,productList,listener);
                            recyclerView.setAdapter(adaptor);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void setOnClickListener() {
        listener= new ListActivityAdaptor.RecyclerViewClickListener() {
            @Override
            public void OnClick(View v, int position) {
                Intent intent= new Intent(getApplicationContext(),ListActivity2.class);
                intent.putExtra("product",productList.get(position).getProduct_ID());
                intent.putExtra("productname",productList.get(position).getproduct_name());
                startActivity(intent);
            }
        };
    }




}