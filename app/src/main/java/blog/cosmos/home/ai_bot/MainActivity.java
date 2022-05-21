package blog.cosmos.home.ai_bot;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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



/*Is Main screen of the app which creates a list of messages from user and bot,combined. It then displays this list in recyclerview */

public class MainActivity extends AppCompatActivity {

    private int currentPosition = 0;
    private  boolean isScrollToLastRequired = false;
    private RecyclerView chatsRV;
    private ImageButton sendMsgIB;
    private EditText userMsgEdt;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";
    private RequestQueue mRequestQueue;

    private static final String TAG = "TAG";

    
    private ArrayList<Message> messageModalArrayList;
    private MessageAdapter messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        chatsRV = findViewById(R.id.idRVChats);

        sendMsgIB = findViewById(R.id.idIBSend);
        userMsgEdt = findViewById(R.id.idEdtMessage);

        // Make a new volley request queue
        mRequestQueue = Volley.newRequestQueue(MainActivity.this);
        mRequestQueue.getCache().clear();

        messageModalArrayList = new ArrayList<>();

       


        sendMsgIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               
                if (userMsgEdt.getText().toString().isEmpty()) {
        
                    Toast.makeText(MainActivity.this, "Please enter your message..", Toast.LENGTH_SHORT).show();
                    return;
                }
                    sendMessage(userMsgEdt.getText().toString());
    
                userMsgEdt.setText("");
            }
        });

        messageAdapter = new MessageAdapter(messageModalArrayList, this,isFemaleBot);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);


        /**This block of code scrolls recyclerview to bottom when the keyboard is open and the user sends something
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
         * 
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


        chatsRV.setLayoutManager(linearLayoutManager);

  
        chatsRV.setAdapter(messageAdapter);

    }




  // Send message request using volley
    private void sendMessage(String userMsg) {


        String url1 = Utils.URL + "?apiKey=" + Utils.apiKey + "&message=" + userMsg + "&chatBotID=" + Utils.chatBotID + "&externalID=" + Utils.externalID;

        
        messageModalArrayList.add(new Message(userMsg, USER_KEY));
        messageAdapter.notifyDataSetChanged();

 
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "response " + response.toString());

                try {

                    //JSON parsing to get data back
                    if (response.getInt("success") == 1) {

                        JSONObject m = response.getJSONObject("message");
                        Log.d(TAG, "response " + m.toString());
                        String botResponse = m.getString("message");

                        messageModalArrayList.add(new Message(botResponse, BOT_KEY));

                        messageAdapter.notifyDataSetChanged();

                    } else {
                        String error = response.getString("errorMessage");
                        Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    Log.d(TAG, "exception");

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

       
        queue.add(jsonObjectRequest);
    }

    
}
