package blog.cosmos.home.ai_bot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**This class populates our Recyclerview with its dataset which contains user messages and bot messages**/
public class MessageAdapter extends RecyclerView.Adapter {


    private List<Message> mMessageModalArrayList;
    private Context context;

    

    public MessageAdapter(ArrayList<Message> mMessageModalArrayList, Context context) {
        this.mMessageModalArrayList = mMessageModalArrayList;
        Collections.reverse(mMessageModalArrayList);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
    
        switch (viewType) {
            case 0:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_message, parent, false);

                return new UserViewHolder(view);
            case 1:
       
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_message, parent, false);
                return new BotViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // this method is use to set data to our layout file.
        Message modal = mMessageModalArrayList.get(position);
        switch (modal.getSender()) {
            case "user":

                ((UserViewHolder) holder).userTV.setText(modal.getMessage());
                break;
            case "bot": {
      
                ((BotViewHolder) holder).botTV.setText(modal.getMessage());

                
                    }


                break;
        }
    }

    @Override
    public int getItemCount() {
        
        return mMessageModalArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        // below line of code is to set position.
        switch (mMessageModalArrayList.get(position).getSender()) {
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }

    public void addAll(List<Message> data){
        if (data != null && !data.isEmpty()) {
          
            mMessageModalArrayList = data;
       
            notifyDataSetChanged();
        }
    }


    public void clear(){
        if(mMessageModalArrayList!=null && !mMessageModalArrayList.isEmpty()) {
            int size = mMessageModalArrayList.size();
            mMessageModalArrayList.clear();

            notifyItemRangeRemoved(0, size);
        }

    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

      
        TextView userTV;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
          
            userTV = itemView.findViewById(R.id.idTVUser);
        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder {


        TextView botTV;

       

        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
           
            botTV = itemView.findViewById(R.id.idTVBot);
          
        }
    }
}
