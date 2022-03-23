package edu.sdsmt.hohn_tanner.tutorial5;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DeletingDlg extends DialogFragment implements CatalogCallback {
    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    /**
     * Id for the image we are loading
     */
    private String catId;
    private final static String ID = "id";
    private AlertDialog dlg;

    /**
     * Create the dialog box
     */
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        if(bundle != null) {
            catId = bundle.getString(ID);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set the title
        builder.setTitle(R.string.delete_title);

        // Add a cancel button
        builder.setNegativeButton(android.R.string.cancel, (dialog, id) -> {
            // Cancel just closes the dialog box
            dialog.dismiss();
        });
        builder.setPositiveButton(android.R.string.ok, (dialog, id) -> {
            EditText editName = dlg.findViewById(R.id.editName);
            //this now delete save(editName.getText().toString());
        });

        // Create the dialog box
        dlg = builder.create();

        // Get a reference to the view we are going to load into
        //final HatterView view = getActivity().findViewById(R.id.hatterView);
        Cloud cloud = new Cloud();
        //delete from cload here?
        // cloud.loadFromCloud(view, catId, dlg);

        return dlg;
    }

    /**
     * Save the instance state
     */
    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        bundle.putString(ID, catId);
    }

    @Override
    public void callback(Cloud.Item catItem) {
        this.dismiss();
    }
}
