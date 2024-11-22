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

import Data.Models.Channel;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder> {

    Activity activity;
    List<Channel> channelsList;
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

    public interface OnItemClickListener {
        void onItemClickListener(Channel channel);
    }

    public void setChannelsList(List<Channel> channelsList){
        this.channelsList = channelsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (channelsList != null) ? channelsList.size() : 0;
    }

    @NonNull
    @Override
    public ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_list_channel, parent, false);
        return new ChannelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelViewHolder holder, int position) {
        Channel channel = channelsList.get(position);
        holder.setChannelName(channel.getName());
        holder.setChannelSubname("ilość użytkowników onlin");
    }

    public class ChannelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView channelNameTextView, channelSubnameTextView;

        public ChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            channelNameTextView = itemView.findViewById(R.id.channelName);
            channelSubnameTextView = itemView.findViewById(R.id.channelSubname);
            itemView.setTag(this);
            itemView.setOnClickListener(this);
        }

        public void setChannelName(String channelName){
            channelNameTextView.setText(channelName);
        }
        public void setChannelSubname(String channelSubname){
            channelSubnameTextView.setText(channelSubname);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position!=RecyclerView.NO_POSITION) {
                Channel channel = channelsList.get(position);
                onItemClickListener.onItemClickListener(channel);
            }
        }
    }
}
