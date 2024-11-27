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

import Data.Models.User;

//TODO Dorobic do konca adapter, fragment recycelr no cały pakiet do widoku
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    Activity activity;
    List<User> userList;

    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;

    public UserAdapter(Activity activity) {
        this.activity = activity;
        this.layoutInflater = LayoutInflater.from(activity);
        this.userList = null;
        try {
            onItemClickListener = (OnItemClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.getMessage() + " must implements UserAdapter.OnItemClickListener interface");
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_list_users, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.setUsername(user.getUsername());
    }

    @Override
    public int getItemCount() {
        return (userList != null) ? userList.size() : 0;
    }

    public interface OnItemClickListener{
        void onItemClickListener(User user);
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView usernameTextView, userRolesTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            usernameTextView = itemView.findViewById(R.id.username);
//            userRolesTextView = itemView.findViewById(R.id.userRoles);

            itemView.setTag(this);
            itemView.setOnClickListener(this);
        }

        public void setUsername(String username) {
            this.usernameTextView.setText(username);
        }

        public void setRoles(List<Long> userRoles) {
            String conc="";
            for (Long l : userRoles){
                conc=conc.concat(l + ", ");
            }
            this.userRolesTextView.setText(conc);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION){
                User user = userList.get(position);
                onItemClickListener.onItemClickListener(user);
            }
        }
    }
}
