package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;


public class GalleryFragment extends Fragment {
    Uri photoUri;
    ImageView add_image;

    //void 함수이름(result){}가 간단하게 result->
    private final ActivityResultLauncher<Intent> resultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            photoUri = result.getData().getData();//그냥두번쓰기

                            if (photoUri != null) {
                                add_image.setImageURI(photoUri);

                            }
                        } else {
                            //requireActivity().finish();//실제경로: mainactivity--homeactivity--fragment(아주작은요소)
                            //현재 액티비티 끝냄
                        }
                    });

    @Override
    public void onCreate(Bundle savedInstanceState) {//한번만사용
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,//어떤화면쓸지가져오기
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    //화면준비되면실행함수
    public void onViewCreated(@NonNull View view, @Nullable Bundle SavedInstanceState) {
        //
        add_image = view.findViewById(R.id.add_image);

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);//사진선택란으로 화면전환
        photoPickerIntent.setType("image/*");//이미지별:이미지폴더
        resultLauncher.launch(photoPickerIntent);
        Button upload_button=view.findViewById(R.id.btn_upload);
        upload_button.setOnClickListener(v->{
            uploadImageToFirebase(photoUri);
        });
    }

    public void uploadImageToFirebase(Uri imageUri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        String safeEmail = email.replace(".", "_").replace("@", "_");
        long timestamp = new Date().getTime() / 1000;
        StorageReference storageRef = FirebaseStorage.getInstance().getReference()
                .child("image")
                .child(safeEmail)
                .child(timestamp + ".jpg");

        UploadTask uploadTask = storageRef.putFile(imageUri);

        uploadTask.addOnCompleteListener(taskSnapshot->{
            storageRef.getDownloadUrl().addOnSuccessListener(uri->{
                String downloadUri=uri.toString();

                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
                databaseReference.child("users")
                        .child(safeEmail)
                        .child(String.valueOf(timestamp))
                        .setValue(downloadUri);
            });
        });

    }
}