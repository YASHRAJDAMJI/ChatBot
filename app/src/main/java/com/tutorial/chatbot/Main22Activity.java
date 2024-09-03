package com.tutorial.chatbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Main22Activity extends AppCompatActivity {
 TextView url;
 ImageView imge,wbg;
 ImageButton sr,button1;
 AutoCompleteTextView edtsr;
 String arr[]={"samsung M30","Poco X3"};
int flag=0;
    GridView coursesGV;
    ArrayList<DataModal> dataModalArrayList;
    ArrayList<DataModal> dataModalArrayList1;
    FirebaseFirestore db;
 FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();

 DatabaseReference reference=firebaseDatabase.getReference();
 DatabaseReference childreference=reference.child("link");
 RecyclerView mRecyclearView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);
        edtsr=findViewById(R.id.editTextTextPersonName);
        coursesGV = findViewById(R.id.idGVCourses);
        dataModalArrayList = new ArrayList<>();
        dataModalArrayList1 = new ArrayList<>();
        //db = FirebaseDatabase.getInstance();
        db = FirebaseFirestore.getInstance();
        loadDatainGridView();
       wbg=findViewById(R.id.imageView2);
        //url=findViewById(R.id.url);
        imge=findViewById(R.id.imageea);
        //CollectionReference citiesRef = db.collection("Data");
        //Query query = citiesRef.whereEqualTo("User", "M30");
        //Log.d("Sample","query is "+query.toString());


        //sr=findViewById(R.id.imageButton2);
        //edtsr=findViewById(R.id.editTextTextPersonName);
        button1=findViewById(R.id.imageButton2);
                //mRecyclearView=findViewById(R.id.recyclerview);

        //citiesRef.whereGreaterThanOrEqualTo("name", "Poco x3").get();









        edtsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // wbg.setVisibility(View.VISIBLE);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag==0)
                {
                    Log.d("sample","in if");
                    edtsr.setVisibility(View.VISIBLE);
                    String srch=edtsr.getText().toString();
                    Log.d("sample","value of getlib=ne is ");


                    if(edtsr.getLineCount()==-1)
                    {
                        edtsr.setVisibility(View.INVISIBLE);
                    }
                }
                else
                {
                    Log.d("sample","i  ma cjivled");
                    edtsr.setVisibility(View.INVISIBLE);
                    flag=0;
                }





            }
        });









    }

    private void loadDatainGridView() {
        // below line is use to get data from Firebase
        // firestore using collection in android.
        db.collection("Data").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are hiding our
                            // progress bar and adding our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {


                                DataModal dataModal = d.toObject(DataModal.class);
                                //DataModal dataModal1 = d.toObject(DataModal.class);
                               // Log.e(dataModalArrayList);
                                dataModalArrayList.add(dataModal);
                                //dataModalArrayList1.add(dataModal);
                            }

                            CoursesGVAdapter adapter = new CoursesGVAdapter(Main22Activity.this, dataModalArrayList);
                          //  ArrayAdapter<String> adapter1=new ArrayAdapter<String>(Main22Activity.this, android.R.layout.simple_dropdown_item_1line,arr);
                           // edtsr.setAdapter(adapter1);
                            coursesGV.setAdapter(adapter);


                        } else {

                            Toast.makeText(Main22Activity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(Main22Activity.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();











        childreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataaSnapshot) {
                String message=dataaSnapshot.getValue(String.class);
                // url.setText(message);





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}