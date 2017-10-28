package com.kartikshah.reddit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.kartikshah.reddit.ui.CustomTextView;


public class ItemDetailActivity extends AppCompatActivity {


    public static final String title ="title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);



        CustomTextView body=(CustomTextView) findViewById(R.id.Body);
        body.setText(getIntent().getStringExtra(ItemDetailActivity.title));

        Bundle arguments = new Bundle();
        arguments.putString(ItemDetailFragment.title,
                getIntent().getStringExtra(ItemDetailFragment.title));

        arguments.putString(ItemDetailFragment.url,
                getIntent().getStringExtra(ItemDetailFragment.url));
        ItemDetailFragment fragment = new ItemDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.item_detail_container, fragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            //NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
