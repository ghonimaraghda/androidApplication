package activity;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginandregistration.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListActivityAdaptor3 extends RecyclerView.Adapter<ListActivityAdaptor3.ListActivityViewHolder3> {
    public Context mCtx;
    public List<cart> cartList;




    public ListActivityAdaptor3(Context mCtx, List<cart> cartList) {
        this.mCtx = mCtx;
        this.cartList = cartList;

    }

    @NonNull
    @Override
    public ListActivityViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view= inflater.inflate(R.layout.recycler_item3, null);
        ListActivityAdaptor3.ListActivityViewHolder3 holder= new ListActivityAdaptor3.ListActivityViewHolder3(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListActivityViewHolder3 holder, int position) {
        final cart cart=cartList.get(position);

        holder.shopName.setText(cart.getShopName());
        holder.productName.setText(cart.getProductName());
        holder.Price.setText(String.valueOf(cart.getPrice()));
        StrictMode.enableDefaults();
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cart_ID= cart.getCart_ID();
                postRequest(cart_ID);


                Intent intent=new Intent(holder.removeButton.getContext(),ListActivity.class);
                holder.removeButton.getContext().startActivity(intent);
                }

            }
        );


    }

    private void postRequest(int cart_id) {
        RequestQueue requestQueue= Volley.newRequestQueue(mCtx);
        String URL_CART = "http://192.168.1.2/android_login_api/delete.php ";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("cart_ID",String.valueOf(cart_id));
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    @Override
    public int getItemCount() {

        return cartList.size();

    }

    class ListActivityViewHolder3 extends RecyclerView.ViewHolder {
        TextView shopName, productName, Price;
        Button removeButton;

        public ListActivityViewHolder3(@NonNull View itemView) {
            super(itemView);
            shopName = itemView.findViewById(R.id.shopName);
            productName = itemView.findViewById(R.id.productName);
            Price = itemView.findViewById(R.id.Price);
            removeButton = itemView.findViewById(R.id.removeButton);

        }
    }


}
