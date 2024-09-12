package Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
