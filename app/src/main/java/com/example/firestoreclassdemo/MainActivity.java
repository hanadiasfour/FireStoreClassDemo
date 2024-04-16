package com.example.firestoreclassdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private Button read, add;
    private TextView data;
    private EditText name, age, reason;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //setting the views
        setViews();

        //initialize the firebase firestore
         db = FirebaseFirestore.getInstance();


    }

    public void setViews(){
        read= findViewById(R.id.read);
        add = findViewById(R.id.add);
        data = findViewById(R.id.datatxt);
        name = findViewById(R.id.nametxt);
        age = findViewById(R.id.agetxt);
        reason = findViewById(R.id.reasontxt);
    }


    public void addPatient(View view){

        //creates object using info added in the text fields
        Patient p = getPatientObject();

        if(p == null){
            Toast.makeText(this, "Enter all of the Feilds", Toast.LENGTH_SHORT).show();

        }else{

            // Add a new document with a generated ID
            db.collection("Patient")
                    .add(p)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(MainActivity.this, "successfully added", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "didn't add", Toast.LENGTH_SHORT).show();
                            System.out.println("Error adding document "+ e);
                        }
                    });
        }

    }

    public void ReadPatients(View view){

        db.collection("Patient")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        String d ="";
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                d+= document.getData() +"\n\n";
                            }

                            data.setText(d);
                        } else {
                            Toast.makeText(MainActivity.this, "Can't read", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public Patient getPatientObject(){

        if(name.getText().toString().isEmpty() ||age.getText().toString().isEmpty()||reason.getText().toString().isEmpty() ){
            return null;
            
        }else{
            
            String n = name.getText().toString();
            String r = reason.getText().toString();
            int a = Integer.parseInt(age.getText().toString());
            return new Patient(n,a,r);
        }
    }
}
