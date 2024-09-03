package com.tutorial.chatbot;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity {

    int flag =0;
    int ref=1;
    DatabaseReference databaseReference;
    ArrayList<String> List;
    public String s;

    RecyclerView recyclerView;
    public EditText editText;
    ImageView imageView;
    ArrayList<Chatsmodal> chatsmodalArrayList;
    ChatAdapter chatAdapter;
    private  final String USER_KEY = "user";
    private  final String BOT_KEY = "bot";
    //public Object List;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.chat_recycler);
        editText = findViewById(R.id.edt_msg);

        imageView = findViewById(R.id.send_btn);
        chatsmodalArrayList = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatsmodalArrayList,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        databaseReference= FirebaseDatabase.getInstance().getReference("");




        recyclerView.setAdapter(chatAdapter);

        chatsmodalArrayList.add(new Chatsmodal("Hi, There... \n i am in chatmode now..... \n to switch me into shoping mode pls type shop....  ", BOT_KEY));
        chatAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
        //Intent intent2 = getIntent();

        // receive the value by getStringExtra() method
        // and key must be same which is send by first activity
        //String str = intent2.getStringExtra("message_key");
        //editText.setText(str);
        //Intent intent = new Intent(getApplicationContext(),CoursesGVAdapter.class);
        //Intent intent = getIntent();
        //s = intent.getStringExtra("message_key");
        //Log.d("sample","valu od s is "+s);
        //editText.setText(s);
        //editText.setText("shop");
        // display the string into textView

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"Please enter your message",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (editText.getText().toString().equals("shop") == true) {
                    Toast.makeText(MainActivity.this, "Opning shoping mode", Toast.LENGTH_SHORT).show();
                    flag = 1;
                }
                if (editText.getText().toString().equals("normal") == true) {
                    Toast.makeText(MainActivity.this, "Opning shoping mode", Toast.LENGTH_SHORT).show();
                    flag = 0;
                }
                if (editText.getText().toString().equals("normal") == true) {
                    Toast.makeText(MainActivity.this, "Opning shoping mode", Toast.LENGTH_SHORT).show();
                    ref = 0;
                }

                getResponse(editText.getText().toString());
               // databaseReference.setValue(editText.getText().toString());
                HashMap<String,Object>map=new HashMap<>();
                map.put("data",editText.getText().toString());

                FirebaseDatabase.getInstance().getReference().child("all info").push()
                        .setValue(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.i("tag","oncomplet") ;
                            }
                        });


                //new jugad

                if (flag == 0) {
                    if (ref == 0)
                    {
                        // Toast.makeText(MainActivity.this, "Opning shoping mode", Toast.LENGTH_SHORT).show();

                        chatsmodalArrayList.add(new Chatsmodal("back to normal", BOT_KEY));
                        chatAdapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
                        ref++;
                    }
                    else
                    {


                    }
                } else {


                    chatsmodalArrayList.add(new Chatsmodal("i am in shoping mode", BOT_KEY));
                    chatAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);

                    //Intent activity2Intent = new Intent(getApplicationContext(), Main22Activity.class);
                    //startActivity(activity2Intent);

                }






                    editText.setText("");
                //editText.setText(str);
            }
        });





    }



    //http://api.brainshop.ai/get?bid=164165&key=mrUquOej5HM6zSQc&uid=[uid]&msg=

    private void getResponse(String message) {
        chatsmodalArrayList.add(new Chatsmodal(message,USER_KEY));
        chatAdapter.notifyDataSetChanged();
        String url = "\n" +
                "http://api.brainshop.ai/get?bid=164165&key=mrUquOej5HM6zSQc&uid=[uid]&msg="+message;
        String BASE_URL = "http://api.brainshop.ai/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetroFitApi retroFitApi = retrofit.create(RetroFitApi.class);
        Call<MsgModal> call = retroFitApi.getMessage(url);
        call.enqueue(new Callback<MsgModal>() {
            @Override
            public void onResponse(Call<MsgModal> call, Response<MsgModal> response) {
                if(response.isSuccessful()) {

                    if (editText.getText().toString().equals("normal") == true) {
                        Toast.makeText(MainActivity.this, "back to bot", Toast.LENGTH_SHORT).show();
                        flag = 0;
                    }

                    if (flag == 0) {
                        if (ref == 0)
                        {
                            // Toast.makeText(MainActivity.this, "Opning shoping mode", Toast.LENGTH_SHORT).show();
                            //MsgModal msgModal = response.body();
                            chatsmodalArrayList.add(new Chatsmodal("back to normal", BOT_KEY));
                            chatAdapter.notifyDataSetChanged();
                            recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
                            ref++;
                        }
                        else
                            {
                            MsgModal msgModal = response.body();
                            chatsmodalArrayList.add(new Chatsmodal(msgModal.getCnt(), BOT_KEY));
                            chatAdapter.notifyDataSetChanged();
                            recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
                            }
                    } else
                        {

                        //MsgModal msgModal = response.body();
                        chatsmodalArrayList.add(new Chatsmodal("i am in shoping mode", BOT_KEY));
                        chatAdapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);

                           Intent activity2Intent = new Intent(getApplicationContext(), Main22Activity.class);
                            startActivity(activity2Intent);

                            //Intent activity2Intent = new Intent(getApplicationContext(), CheckoutActivity.class);
                            //startActivity(activity2Intent);

                            // Read from the database
                            List = new ArrayList<String>();
                            //=new ArrayList<>();
                            //List.clear();
                            FirebaseDatabase.getInstance().getReference().child("User").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // This method is called once with the initial value and again
                                    // whenever data at this location is updated.
                                    // String value =dataSnapshot.getChildren(String.class);
                                    // String value = dataSnapshot.getValue(String.class);
                                    //Log.d(TAG, "Value is: " + value);
                                    // System.out.println("the value is "+value);

                                    for (DataSnapshot Snapshot : dataSnapshot.getChildren())
                                    {
                                       // User user=Snapshot.getValue(User.class);
                                        //String t="\nModel name is :-"+user.getModel()+"\n"+"Model Price is :- \n"+user.getPrice();


                                        // String t1=user.getModel();


                                        //List.add(t);


                                        //recyclerView.add(t);
                                    }

                                    //Object map=0;


                                    chatsmodalArrayList.add(new Chatsmodal(""+List, BOT_KEY));

                                    // System.out.println("this is list -------------------------------------------------------------------------------------------------------------------------"+List);
                                    chatAdapter.notifyDataSetChanged();

                                    recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
                                    //List.clear();
                                    //Log.d("tag","this is the data :-  "+ value);
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value
                                    Log.w("tag", "Failed to read value.", error.toException());
                                }
                            });
                    }
                }
            }

            @Override
            public void onFailure(Call<MsgModal> call, Throwable t) {
                chatsmodalArrayList.add(new Chatsmodal("Pls Check Internet Connection",BOT_KEY));
                chatAdapter.notifyDataSetChanged();
            }
        });
    }

    public void onResume()
    {
        super.onResume();
        Intent intent = getIntent();
        s = intent.getStringExtra("message_key");
        Log.d("sample","valu od s is "+s);







    }
}



