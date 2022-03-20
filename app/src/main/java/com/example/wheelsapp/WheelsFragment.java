package com.example.wheelsapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.security.acl.Permission;

public abstract class WheelsFragment extends Fragment {

    private ProgressDialog loadingDialog;

    protected void showLoading(String message) {
        loadingDialog = new ProgressDialog(getContext());
        loadingDialog.setTitle("Wheels");
        loadingDialog.setMessage(message);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }

    protected void stopLoading() {
        loadingDialog.dismiss();
        loadingDialog = null;
    }


    protected void openGallery() {
        if(getContext()==null || getActivity()==null) return;
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else {
            Intent i = new Intent(Intent.ACTION_PICK);
            i.setType("image/*");
            startActivity(i);
        }
    }

    protected void openCamera() {
        if(getContext()==null || getActivity()==null) return;
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},1);
        }else {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(i);
        }
    }


    protected void showToast(String m) {
        Toast.makeText(getContext(),m,Toast.LENGTH_SHORT).show();
    }


    protected boolean isValidFields(EditText[] fields) {
        for(EditText input : fields) {
            if(input.getText().toString().trim().isEmpty()) {
                input.requestFocus();
                showToast(input.getHint().toString().isEmpty() ? "Please fill all the fields" : input.getHint().toString() + " is required");
                return false;
            }
        }
        return true;
    }


}
