package com.example.firebasechatroomlab;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.FirebaseListAdapter;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    private Firebase mFirebaseRootRef;
    private FirebaseListAdapter<String> mFirebaseAdapter;
    private Firebase mFirebaseChat;
    private ArrayList<String> mMessages;
    private ListView mChatListView;
    private EditText mChatEditText;
    private Button mChatButton;
    private String mUserName = "User";
    private Random mUserNameGenerator;
    private Toolbar mToolbar;
    private ActionBar mActionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mUserNameGenerator = new Random();
        mUserName += mUserNameGenerator.nextInt(1000);

        setSupportActionBar(mToolbar);
        mActionbar = getSupportActionBar();
        mActionbar.setTitle("Chatting as " + mUserName);


        mFirebaseRootRef = new Firebase("https://blinding-heat-9466.firebaseio.com");
        mFirebaseChat = mFirebaseRootRef.child("current_text");
        mChatListView = (ListView)findViewById(R.id.chatListView);
        mChatEditText = (EditText)findViewById(R.id.chatEditText);
        mChatButton = (Button)findViewById(R.id.chatButton);
        mMessages = new ArrayList<>();


        mFirebaseChat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String message = dataSnapshot.getValue(String.class);
                mMessages.add(message);
                mFirebaseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        mFirebaseAdapter = new FirebaseListAdapter<String>(this,String.class,android.R.layout.simple_list_item_1, mFirebaseRootRef) {
            @Override
            protected void populateView(View view, String s, int i) {
                TextView textView = (TextView)view.findViewById(android.R.id.text1);
                textView.setText(s);
            }
        };

        mChatListView.setAdapter(mFirebaseAdapter);

        mChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseChat.push().setValue(mUserName + ": " + mChatEditText.getText().toString());
                mChatEditText.setText("");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
