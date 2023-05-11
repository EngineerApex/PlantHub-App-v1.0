package com.example.final_test.CameraFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.final_test.R;
import android.graphics.Bitmap;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;


public class CameraFragment extends Fragment {

    private static final int REQUEST_CODE = 22;
    private static final int RESULT_OK = -1;
    private static final int REQUEST_CODE_GALLERY = 24;
    private static final int REQUEST_CODE_CAMERA = 25;

    Button capture;
    Button gallery;
    ImageView cameraw;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_camera, container, false);

        capture = rootView.findViewById(R.id.camerabutton);
        cameraw = rootView.findViewById(R.id.camerawindow);
        gallery = rootView.findViewById(R.id.gallerybutton);

        //Capture Button
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_CODE);
            }
        });

        //Upload from Gallery Button
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /*
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            cameraw.setImageBitmap(photo);
        }
        else {
            Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
        */
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                // Handle camera result
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                cameraw.setImageBitmap(photo);
                int width = photo.getWidth();
                int height = photo.getHeight();
                Toast.makeText(getContext(), "Image Resolution: " + width + " x " + height, Toast.LENGTH_SHORT).show();
            } else if (requestCode == REQUEST_CODE_GALLERY) {
                // Handle gallery result
                Uri selectedImageUri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();
                Bitmap photo = BitmapFactory.decodeFile(filePath);
                cameraw.setImageBitmap(photo);
                int width = photo.getWidth();
                int height = photo.getHeight();
                Toast.makeText(getContext(), "Image Resolution: " + width + " x " + height, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}