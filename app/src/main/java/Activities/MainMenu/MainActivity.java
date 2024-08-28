package Activities.MainMenu;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.szampchat.R;

import java.util.List;

import Activities.Community.CommunityActivity;
import Adapters.CommunityAdapter;
import Data.Models.CommunityModel;
import Data.ViewModels.CommunityViewModel;

public class MainActivity extends AppCompatActivity implements CommunityAdapter.OnItemClickListener{
    List<CommunityModel> mCommunitiesList;
    CommunityViewModel mCommunityViewModel;
    CommunityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.communitiesGrid);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        adapter = new CommunityAdapter(this);
        recyclerView.setAdapter(adapter);

        mCommunityViewModel = new ViewModelProvider(this).get(CommunityViewModel.class);
        mCommunityViewModel.getAllCommunities().observe(this, communityModels -> adapter.setCommunitiesList(communityModels));
    }


    @Override
    public void onItemClickListener(CommunityModel community) {
        Intent intent = new Intent(MainActivity.this, CommunityActivity.class);
        startActivity(intent);
    }
}