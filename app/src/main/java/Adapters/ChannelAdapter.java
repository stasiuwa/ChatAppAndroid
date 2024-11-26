package Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.szampchat.R;

import java.util.List;

import Data.DTO.ChannelType;
import Data.Models.Channel;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder> {

    public static final int VIEW_TYPE_VOICE_CHANNEL = 1;
    public static final int VIEW_TYPE_TEXT_CHANNEL = 0;

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

    @Override
    public int getItemViewType(int position) {
        Channel channel = (Channel) channelsList.get(position);
        if (channel.getType().equals(ChannelType.VOICE_CHANNEL))
            return VIEW_TYPE_VOICE_CHANNEL;
        else
            return VIEW_TYPE_TEXT_CHANNEL;
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

        if (channel.getType().equals(ChannelType.VOICE_CHANNEL)) holder.setIcon(R.drawable.outline_mic_24);
        else holder.setIcon(R.drawable.baseline_chat_bubble_outline_24);
    }

    public class ChannelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView channelNameTextView, channelSubnameTextView;
        ImageView iconImageView;

        public ChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.channelIcon);
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

        @SuppressLint("UseCompatLoadingForDrawables")
        public void setIcon(int icon) {
            iconImageView.setImageDrawable(activity.getDrawable(icon));
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
