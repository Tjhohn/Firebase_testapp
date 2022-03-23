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
    public String getImageName(){return imageName; }

    public void setCatId(String catId) {
        this.catId = catId;
    }
    public void setImageName(String name) {
        this.imageName = name;
    }

    /**
     * Id for the image we are removing
     */
    private String catId;
    /**
     * name for the image we are removing
     */
    private String imageName;
    private final static String ID = "id";
    private final static String NAME = "name";
    private AlertDialog dlg;

    /**
     * Create the dialog box
     */
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        if(bundle != null) {
            catId = bundle.getString(ID);
            imageName = bundle.getString(NAME);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set the title
        builder.setTitle(R.string.delete_title);
        builder.setMessage(getResources().getString(R.string.delete_sure) + " " + imageName+ "?");

        // Add a cancel button
        builder.setNegativeButton(android.R.string.cancel, (dialog, id) -> {
            dialog.dismiss();
        });
        builder.setPositiveButton(android.R.string.ok, (dialog, id) -> {
            //this now delete save(editName.getText().toString());
            Cloud cloud = new Cloud();
            final HatterView view = getActivity().findViewById(R.id.hatterView);
            cloud.removeFromCloud(view, catId, dlg);
        });

        // Create the dialog box
        dlg = builder.create();

        return dlg;
    }

    /**
     * Save the instance state
     */
    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        bundle.putString(ID, catId);
        bundle.putString(NAME, imageName);
    }

    @Override
    public void callback(Cloud.Item catItem) {
        this.dismiss();
    }
}
