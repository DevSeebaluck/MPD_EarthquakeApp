/**
 * Common
 * @author Dev Seebaluck
 * @matric S1903333
 **/

package org.me.gcu.equakestartercode.Common;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import org.me.gcu.equakestartercode.Model.Item;
import org.me.gcu.equakestartercode.R;

public class ListAdapter extends ArrayAdapter<String> {
    ArrayList<Item> items;
    Activity context;
    boolean title;

    public ListAdapter(Activity context, ArrayList<Item> items, String[] array, boolean title) {
        super(context, R.layout.row, array);
        this.items = items;
        this.context = context;
        this.title = title;
    }

    @NonNull
    @Override
    public View getView(int i, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ListHolder listHolder = null;

        if(view == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.row, null, true);
            listHolder = new ListHolder(view, title);
            view.setTag(listHolder);
        }else{
            listHolder = (ListHolder) view.getTag();
        }

        listHolder.getLocation().setText(items.get(i).getLocation());
        listHolder.getDate().setText(items.get(i).getDate());

        if(title){
            listHolder.getTitle().setText(items.get(i).getTitle());
        }

        listHolder.getDepth().setText(items.get(i).getDepth());

        listHolder.getMagnitude().setText(items.get(i).getMagnitude());
        listHolder.getDescription().setText(items.get(i).getDescription());

        return view;
    }

}
