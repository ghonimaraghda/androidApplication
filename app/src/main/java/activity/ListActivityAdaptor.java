package activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.loginandregistration.R;

import java.util.List;

// 2 classes one for recycleView.adapter and the other for recycleView.holder
public class ListActivityAdaptor extends RecyclerView.Adapter<ListActivityAdaptor.ListActivityViewHolder> {
    public Context mCtx;
    public List<product> productList;
    public RecyclerViewClickListener listener;



    public ListActivityAdaptor(Context mCtx, List<product> productList,RecyclerViewClickListener listener) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ListActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view= inflater.inflate(R.layout.recycler_item, null);
        ListActivityViewHolder holder= new ListActivityViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListActivityViewHolder holder, int position) {
        product product=productList.get(position);
        holder.text1.setText(product.getproduct_name());
        holder.text2.setText(product.getDescription());
        Glide.with(mCtx)
                .load(product.getImage_url())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {

        return productList.size();
    }

    public  class ListActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView text1,text2;
        public ListActivityViewHolder( final View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            text1=itemView.findViewById(R.id.text1);
            text2=itemView.findViewById(R.id.text2);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick( View v)
        {
            listener.OnClick(v,getAdapterPosition());
        }
    }
    public interface RecyclerViewClickListener{
        void OnClick(View v,int position);
    }





}
