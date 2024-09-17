package Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.szampchat.R;

import java.util.Date;
import java.util.List;

import Data.Models.MessageModel;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    Activity activity;
    List<MessageModel> messagesList;

    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;

    public MessageAdapter(Activity activity) {
        this.activity = activity;
        this.layoutInflater = LayoutInflater.from(activity);
        messagesList = null;
        try {
            onItemClickListener = (OnItemClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.getMessage() + " - must implement MessageAdapter.OnItemClickListener interface");
        }
    }
    public interface OnItemClickListener {
        void onItemClickListener(MessageModel message);
    }

    public void setMessagesList(List<MessageModel> messagesList) {
        this.messagesList = messagesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_list_messages, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MessageModel message = messagesList.get(position);
        holder.setMessageText(message.getText());
        holder.setMessageUser(message.getUsername());

    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }
    public class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView messageText, messageUser, messageTimestamp;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            messageUser = itemView.findViewById(R.id.messageUser);
            messageTimestamp = itemView.findViewById(R.id.messageTimestamp);
            itemView.setTag(this);
            itemView.setOnClickListener(this);
        }

        public void setMessageText(String messageText) {
            this.messageText.setText(messageText);
        }

        public void setMessageUser(String messageUser) {
            this.messageUser.setText(messageUser);
        }
        public void setMessageTimestamp(String timestamp){
            this.messageTimestamp.setText(timestamp);
        }
        public void setMessageTimestamp(Date date){
            this.messageTimestamp.setText(String.valueOf(date));
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position!=RecyclerView.NO_POSITION){
                MessageModel message = messagesList.get(position);
                onItemClickListener.onItemClickListener(message);
            }
        }
    }
}
