package slicker.com.slicker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import slicker.com.slicker.Model.MyConstants;
import slicker.com.slicker.Model.Photo;
import slicker.com.slicker.R;
import slicker.com.slicker.View.FullscreenActivity;

/**
 * Created by squiggie on 2/25/16.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder>{
    private Context mContext;
    private List<Photo> mPhotos = new ArrayList<>();

    public PhotoAdapter(Context context){
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.grid_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final MyViewHolder vh = (MyViewHolder) holder;
        String farm = String.valueOf(mPhotos.get(position).getFarm());
        String server = String.valueOf(mPhotos.get(position).getServer());
        String id = mPhotos.get(position).getId();
        String secret = mPhotos.get(position).getSecret();
        String size = "b";

        String url = String.format(MyConstants.IMAGE_URL,farm,server,id,secret,size);
        if (mPhotos.get(position) != null) {
            Glide
                    .with(mContext)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(new ColorDrawable(Color.GRAY))
                    .crossFade()
                    .into(vh.imageView);
        } else {
            Glide.clear(vh.imageView);
            vh.imageView.setImageDrawable(null);
        }
    }


    public void add(Photo photo){
        mPhotos.add(photo);
    }

    public void clear(){
        mPhotos.clear();
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Launch full screen image activity
            Intent intent = new Intent(mContext, FullscreenActivity.class);
            intent.putExtra("farm",mPhotos.get(getPosition()).getFarm());
            intent.putExtra("server",mPhotos.get(getPosition()).getServer());
            intent.putExtra("id",mPhotos.get(getPosition()).getId());
            intent.putExtra("secret",mPhotos.get(getPosition()).getSecret());
            mContext.startActivity(intent);
        }
    }
}