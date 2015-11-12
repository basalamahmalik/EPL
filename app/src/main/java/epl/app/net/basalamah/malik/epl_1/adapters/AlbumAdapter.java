package epl.app.net.basalamah.malik.epl_1.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import epl.app.net.basalamah.malik.epl_1.MatchData.Facebook.AlbumData;
import epl.app.net.basalamah.malik.epl_1.R;

/**
 * Created by Malik on 08-Nov-15.
 */
public class AlbumAdapter extends BaseAdapter{

    static class ViewHolder {
        TextView description;
        TextView like_counter;
        ImageView like;
        SimpleDraweeView album_image;
    }


    Uri imageUri;
    List<AlbumData> data;
    private Context _context;

    public void setData(List<AlbumData> data){
        this.data = data;
    }

    public AlbumAdapter(Context context, List<AlbumData> data) {
        this._context = context;
        this.data = data;
    }


    @Override
    public Object getItem(int groupPosition) {
        return this.data.get(groupPosition);
    }

    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public long getItemId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater Inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = Inflater.inflate(R.layout.list_item_album, null);

            ViewHolder holder = new ViewHolder();

            holder.description = (TextView)convertView.findViewById(R.id.image_description);
            holder.like_counter= (TextView)convertView.findViewById(R.id.like_counter);
            holder.album_image = (SimpleDraweeView)convertView.findViewById(R.id.album_image);
            holder.like        = (ImageView)convertView.findViewById(R.id.like_image);
            convertView.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.description.setText(data.get(position).getName());
        holder.like_counter.setText(" "+data.get(position).getLikes().getData().size());

        imageUri = Uri.parse(data.get(position).getPicture());
        holder.album_image.setImageURI(imageUri);

        holder.like.setImageResource(R.drawable.ic_like);

        return convertView;
    }

}
