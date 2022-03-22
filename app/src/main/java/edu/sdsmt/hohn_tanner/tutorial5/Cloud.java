package edu.sdsmt.hohn_tanner.tutorial5;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Cloud {

    /**
     * method to load a specific hatting
     * @param view The view we are loading the hatting into
     * @param catId the id of the hatting
     * @param dlg the dialog box showing the loading state
     */
    public void loadFromCloud(final HatterView view, String catId, final Dialog dlg)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("hattings").child(catId);

        // Read from the database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                view.loadJSON(dataSnapshot);

                dlg.dismiss();
                if (view.getContext() instanceof HatterActivity)
                    ((HatterActivity) view.getContext()).updateUI();
                view.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Error condition!
                view.post(() -> {
                    Toast.makeText(view.getContext(), R.string.loading_fail, Toast.LENGTH_SHORT).show();
                    dlg.dismiss();
                });

            }
        });
    }

    /**
     * Save a hatting to the cloud.
     * @param name name to save under
     * @param view view we are getting the data from
     */
    public void saveToCloud(String name, HatterView view) {
        name = name.trim();
        if(name.length() == 0) {
            /*
             *  If we fail to save, display a
             */
            // Please fill this in...
            // Error condition!
            view.post(() -> Toast.makeText(view.getContext(), R.string.save_fail, Toast.LENGTH_SHORT).show());;
        }
        else{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference hattingsList = database.getReference("hattings");
            String key =hattingsList .push().getKey();
            hattingsList = hattingsList.child(key);
            hattingsList.child("name").setValue(name);

            view.saveJSON(hattingsList);
            hattingsList.child("name").setValue(name, new DatabaseReference.CompletionListener() {
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if(databaseError != null) {
                        // Error condition!
                        /*
                         * make a toast
                         */
                        view.post(() -> Toast.makeText(view.getContext(), R.string.fail, Toast.LENGTH_SHORT).show());;
                    }
                }});
        }

    }

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

        public CatalogCallback clickEvent;

        /**
         * Constructor
         */
        public CatalogAdapter(final View view,  CatalogCallback click) {
            items = getCatalog(view);
            clickEvent = click;
        }
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

        public ArrayList getCatalog(final View view) {
            final ArrayList<Item> newItems = new ArrayList<>();

            //connect to the database (hattings child)
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            database.goOnline();
            DatabaseReference myRef = database.getReference("hattings");

            // Read from the database
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    //look at each child
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Log.d("output", "Value is: " + child.toString());

                        Item tempItem = new Item();
                        tempItem.name = child.child("name").getValue().toString();
                        tempItem.id = child.getKey();
                        newItems.add(tempItem);
                    }

                    view.post(new Runnable() {

                        @Override
                        public void run() {
                            // Tell the adapter the data set has been changed
                            notifyItemRangeChanged(0, newItems.size());
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("output", "Failed to read value.", error.toException());

                    // Error condition!
                    view.post(() -> Toast.makeText(view.getContext(), R.string.catalog_fail, Toast.LENGTH_SHORT).show());;
                }
            });

            return newItems;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Get the item of the one we want to load
                    Item catItem = getItem(position);

                    // let the client class do its job
                    clickEvent.callback(catItem);
                }

            });

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
