package edu.sdsmt.hohn_tanner.tutorial5;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DeleteDlg extends DialogFragment implements CatalogCallback{

    @Override
    public void callback(Cloud.Item catItem) {
        DeletingDlg deletingDlg = new DeletingDlg();
        deletingDlg.setCatId(catItem.id);
        deletingDlg.show(getParentFragmentManager(), "loading");
        this.dismiss();
    }

    private AlertDialog dlg;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());// Set the title
        builder.setTitle(R.string.delete_title);

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.catalog_dlg, null);
        builder.setView(view);

        // Add a cancel button
        builder.setNegativeButton(android.R.string.cancel, (dialog, id) -> {
            // Cancel just closes the dialog box
            dialog.dismiss();
        });

        dlg = builder.create();

        RecyclerView list = view.findViewById(R.id.listHattings);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        final Cloud.CatalogAdapter adapter = new Cloud.CatalogAdapter(list, this::callback);
        list.setAdapter(adapter);

        return dlg;
    }
}
