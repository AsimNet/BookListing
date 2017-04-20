package asim.net.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asimaltwijry on 4/19/17.
 */

public class ListAdapter extends ArrayAdapter<Book>{



    static class ViewHolder{
        @BindView(R.id.title)
        TextView bookTitle;

        @BindView(R.id.author)
        TextView author;


        ViewHolder(View view){
            ButterKnife.bind(this,view);
        }

    }
    public ListAdapter(@NonNull Context context, List<Book> objects) {
        super(context,0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        Book currentBook = getItem(position);

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        }else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.bookTitle.setText(currentBook.getTitle());
        holder.author.setText(currentBook.getAuthor());

        return convertView;
    }


}
