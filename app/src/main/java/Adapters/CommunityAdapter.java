package Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.szampchat.R;

import java.util.List;

import Data.Models.CommunityModel;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder> {
    Activity activity;
    List<CommunityModel> mCommunitiesList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public CommunityAdapter(Activity context) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.activity = context;
        mCommunitiesList = null;
        try {
            mOnItemClickListener = (OnItemClickListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(CommunityModel community);
    }

    @NonNull
    @Override
    public CommunityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_grid_community, parent, false);
        return new CommunityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityViewHolder holder, int position) {
        CommunityModel community = mCommunitiesList.get(position);
        holder.setCommunityName(community.getCommunityName());
    }

    @Override
    public int getItemCount() {
        return (mCommunitiesList != null) ? mCommunitiesList.size() : 0;
    }
    public void setCommunitiesList(List<CommunityModel> communitiesList) {
        this.mCommunitiesList = communitiesList;
        notifyDataSetChanged();
    }

    public class CommunityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTextView;

        public CommunityViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.itemGridCommunityName);

            itemView.setOnClickListener(this);
        }
        public void setCommunityName(String communityName){
            mTextView.setText(communityName);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                CommunityModel community = mCommunitiesList.get(position);
                mOnItemClickListener.onItemClickListener(community);
            }
        }
    }
}
