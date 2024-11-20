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

import Data.Models.ChatModel;
import Data.Models.MessageModel;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private static final int VIEW_TYPE_SYSTEM_MESSAGE = 3;

    Activity activity;
    List<MessageModel> messagesList;

    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;

    public MessageAdapter(Activity activity) {
        this.activity = activity;
        this.layoutInflater = LayoutInflater.from(activity);
        this.messagesList = null;
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

    @Override
    public int getItemCount() {
        return (messagesList != null) ? messagesList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        MessageModel message = (MessageModel) messagesList.get(position);
//        TODO poprawic logige xd sprawdzania czy wiadomosc wyslana przez uzytkownika zalogowanego do aplikacji.
        if (message.getUsername().equals("user2")) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else if (message.getUsername().equals("SYSTEM")){
            return VIEW_TYPE_SYSTEM_MESSAGE;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
//        Setup message layout depending on type
        if (viewType == VIEW_TYPE_MESSAGE_SENT){
            view = layoutInflater.inflate(R.layout.item_list_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_SYSTEM_MESSAGE) {
            view = layoutInflater.inflate(R.layout.item_list_message_system, parent, false);
            return new ReceivedMessageHolder(view);
        } else
//            if (viewType == VIEW_TYPE_MESSAGE_RECEIVED)
            {
            view = layoutInflater.inflate(R.layout.item_list_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageModel message = messagesList.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_SYSTEM_MESSAGE:
                ((SystemMessageHolder) holder).bind(message);
                break;
        }
    }



//    ViewHolder for messages sent by user in app
    public class SentMessageHolder extends RecyclerView.ViewHolder {

        TextView messageText, messageTimestamp;

        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.messageText);
            messageTimestamp = (TextView) itemView.findViewById(R.id.messageTimestamp);
            itemView.setTag(this);
        }
        public void bind(MessageModel message) {
            messageText.setText(message.getText());
            messageTimestamp.setText(message.getSentTime());
        }
    }

//    ViewHolder for messages received by user from server
    public class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        TextView messageText, messageUser, messageTimestamp;

        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            messageUser = itemView.findViewById(R.id.messageUser);
            messageTimestamp = itemView.findViewById(R.id.messageTimestamp);
            itemView.setTag(this);
        }
        public void bind(MessageModel message) {
            messageText.setText(message.getText());
            messageTimestamp.setText(message.getSentTime());
            messageUser.setText(message.getUsername());
        }
    }

//    ViewHolder for messages sent by SYSTEM (new user joined etc.)
    public class SystemMessageHolder extends RecyclerView.ViewHolder {

        TextView systemMessage;

        public SystemMessageHolder(@NonNull View itemView) {
            super(itemView);
            systemMessage = itemView.findViewById(R.id.systemMessageText);
        }
        public void bind(MessageModel message){
            systemMessage.setText(message.getText());
        }
    }
}
