package edu.sdsmt.hohn_tanner.tutorial5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Cloud {

    /**
     * An class that holds a line's contents for later updating
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data line
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    /**
     * Nested class to store one catalog row underlying data
     */
    public static class Item {
        public String name = "";
        public String id = "";
    }

    /**
     * An adapter so that list boxes can display a list of filenames from
     * the cloud server.
     */
    public static class CatalogAdapter extends RecyclerView.Adapter<ViewHolder> {

        /**
         * The items we display in the list box. Initially this is
         * null until we get items from the server.
         */
        private ArrayList<Item> items = new ArrayList<>();

        public Item getItem(int position) {
            return items.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            TextView tv = holder.view.findViewById(R.id.textItem);
            String text =  items.get(position).name;
            tv.setText( text );
        }

        @Override
        public int getItemCount() {
            return items.size();
        }


    }
}
