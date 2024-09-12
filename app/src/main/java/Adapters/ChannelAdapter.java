package Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Data.Models.ChannelModel;

//TODO dokonczyc jak beda widoki
public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder> {
    Activity activity;
    List<ChannelModel> channelsList;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;

    public ChannelAdapter(Activity activity) {
        this.activity = activity;
        this.layoutInflater = LayoutInflater.from(activity);
        channelsList = null;
        try {
            onItemClickListener = (OnItemClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.getMessage() + " must implements ChannelAdapter.OnItemClickListener interface");
        }
    }

    @NonNull
    @Override
    public ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface OnItemClickListener {
        void onItemClickListener(ChannelModel channel);
    }

    public class ChannelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ChannelViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
