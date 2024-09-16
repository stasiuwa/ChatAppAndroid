package Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.szampchat.R;

import java.util.List;

import Data.Models.ChatModel;

//TODO dokonczyc jak beda gotowe widoki
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    Activity activity;
    List<ChatModel> chatsList;

    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;

    public ChatAdapter(Activity activity) {
        this.activity = activity;
        this.layoutInflater = LayoutInflater.from(activity);
        chatsList = null;
        try {
            onItemClickListener = (OnItemClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.getMessage() + " - must implement ChatAdapter.OnItemClickListener interface");
        }

    }

    public interface OnItemClickListener {
        void onItemClickListener(ChatModel chat);
    }

    public void setChatsList(List<ChatModel> chatsList) {
        this.chatsList = chatsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_list_chat, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatModel chat = chatsList.get(position);
        holder.setChatName(chat.getChatName());
        holder.setChatSubname("Ilość nieprzeczytanych wiadomosci");
    }



    @Override
    public int getItemCount() {
        return chatsList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView chatNameTextView, chatSubnameTextView;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            chatNameTextView = itemView.findViewById(R.id.chatName);
            chatSubnameTextView = itemView.findViewById(R.id.chatSubname);
            itemView.setTag(this);
            itemView.setOnClickListener(this);
        }

        public void setChatName(String chatName){
            chatNameTextView.setText(chatName);
        }

        public void setChatSubname(String chatSubname){
            chatSubnameTextView.setText(chatSubname);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                ChatModel chat = chatsList.get(position);
                onItemClickListener.onItemClickListener(chat);
            }
        }
    }
}
