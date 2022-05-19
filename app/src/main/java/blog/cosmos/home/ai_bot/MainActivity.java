package blog.cosmos.home.ai_bot;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    int currentPosition = 0;

    boolean isScrollToLastRequired= false;
    // creating variables for our
    // widgets in xml file.
    private RecyclerView chatsRV;
    private ImageButton sendMsgIB;
    private EditText userMsgEdt;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";

    // creating a variable for
    // our volley request queue.
    private RequestQueue mRequestQueue;

    private static final String TAG = "TAG";
    // creating a variable for array list and adapter class.
    private ArrayList<Message> messageModalArrayList;
    private MessageAdapter messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // on below line we are initializing all our views.
        chatsRV = findViewById(R.id.idRVChats);

        sendMsgIB = findViewById(R.id.idIBSend);
        userMsgEdt = findViewById(R.id.idEdtMessage);

        // below line is to initialize our request queue.
        mRequestQueue = Volley.newRequestQueue(MainActivity.this);
        mRequestQueue.getCache().clear();

        // creating a new array list
        messageModalArrayList = new ArrayList<>();



        // adding on click listener for send message button.
        sendMsgIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking if the message entered
                // by user is empty or not.
                if (userMsgEdt.getText().toString().isEmpty()) {
                    // if the edit text is empty display a toast message.
                    Toast.makeText(MainActivity.this, "Please enter your message..", Toast.LENGTH_SHORT).show();
                    return;
                }

                // calling a method to send message
                // to our bot to get response.
                sendMessage(userMsgEdt.getText().toString());

                // below line we are setting text in our edit text as empty
                userMsgEdt.setText("");
            }
        });

        // on below line we are initialing our adapter class and passing our array list to it.
        messageAdapter = new MessageAdapter(messageModalArrayList, this);



        // below line we are creating a variable for our linear layout manager.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);





        /**VV IMPORTANT this block of code scrolls recyclerview to bottom when the keyboard is open and the user sends something
         * Will probably use this feature in other chat apps too
         *
         *
         * (or maybe not if i dont want to get on the bottom of chatlist when using a chatroom type of application and i've scrolled up and im typing something so my keyboard is open,
         * i wont want this feature in that situation, because i dont want to be updated instantaneously if some new message appers while my keyboard is on.
         * I applied this feature for this app because i WANT TO BE NOTIFIED OF THE BOTS RESPONSE,
         * if i dont add this feature here in this app, then i will have to close keyboard and then scroll down to see the new message
         * )
         *
         *
         * **/

        RecyclerView.AdapterDataObserver dataObserver = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                chatsRV.smoothScrollToPosition(messageAdapter.getItemCount());

            }
        };
        messageAdapter.registerAdapterDataObserver(dataObserver);



        /**
         * VVV IMPORTANT -
         * Similar to previous block of code, this block of code scrolls the recyclerview to bottom when keyboard is open (so keyboard doesnt hide recyclerview)
         * BUT, this code works only if we already are at the bottom of recyclerview and user clicks on keyboard
         * if we are not at the bottom of screen and user clicks keyboard, then we do not scroll to bottom or recyclerview
         *
         * This is how whatsapp chats are used too
         *
         *
         * Solution found at: https://stackoverflow.com/questions/52385793/how-to-achieve-chat-screen-type-soft-keyboard-behaviour
         * answered Sep 18, 2018 at 11:40
         * Ashutosh Tiwari
         * **/

        //Setup editText behavior for opening soft keyboard
        userMsgEdt.setOnTouchListener((view, motionEvent) -> {
            InputMethodManager keyboard = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (keyboard != null) {
                isScrollToLastRequired = linearLayoutManager.findLastVisibleItemPosition() == messageAdapter.getItemCount() - 1;
                keyboard.showSoftInput(findViewById(R.id.idIBSend), InputMethodManager.SHOW_FORCED);
            }
            return false;
        });
        //Executes recycler view scroll if required.
        chatsRV.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (bottom < oldBottom && isScrollToLastRequired) {
                chatsRV.postDelayed(() -> chatsRV.scrollToPosition(
                       chatsRV.getAdapter().getItemCount() - 1), 100);
            }
        });



        // below line is to set layout
        // manager to our recycler view.
        chatsRV.setLayoutManager(linearLayoutManager);

        //   linearLayoutManager.setStackFromEnd(true);

        // linearLayoutManager.setReverseLayout(true);
        // below line we are setting
        // adapter to our recycler view.
        chatsRV.setAdapter(messageAdapter);




    }

    private void sendMessage(String userMsg) {


        String url1 = Utils.URL+"?apiKey="+Utils.apiKey+"&message="+userMsg+"&chatBotID="+Utils.chatBotID+"&externalID="+Utils.externalID;

        // below line is to pass message to our
        // array list which is entered by the user.
        messageModalArrayList.add(new Message(userMsg, USER_KEY));
        messageAdapter.notifyDataSetChanged();

        // url for our brain
        // make sure to add mshape for uid.
        // make sure to add your url.
        String url =
                "http://api.brainshop.ai/get?bid=166510&key=YBpfIjUIAdTfbCg7&uid=mshape&msg=" + userMsg;

        // creating a variable for our request queue.
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // on below line we are making a json object request for a get request and passing our url .
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG,"response "+response.toString());

                try {

                    if(response.getInt("success")==1){

                        JSONObject m = response.getJSONObject("message");
                        Log.d(TAG,"response "+m.toString());
                        String botResponse = m.getString("message");

                        messageModalArrayList.add(new Message(botResponse,BOT_KEY));

                        messageAdapter.notifyDataSetChanged();

                    }
                    else
                    {
                        String error=response.getString("errorMessage");
                        Toast.makeText(getApplicationContext(), "Error: "+error, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    Log.d(TAG,"exception");

                    // handling error response from bot.
                    messageModalArrayList.add(new Message("No response", BOT_KEY));
                    messageAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error handling.
                messageModalArrayList.add(new Message("Sorry no response found", BOT_KEY));
                Toast.makeText(MainActivity.this, "No response from the bot..", Toast.LENGTH_SHORT).show();
            }
        });

        // at last adding json object
        // request to our queue.
        queue.add(jsonObjectRequest);
    }
}
