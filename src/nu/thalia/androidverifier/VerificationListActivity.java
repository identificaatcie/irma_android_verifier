package nu.thalia.androidverifier;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

public class VerificationListActivity extends FragmentActivity
        implements VerificationListFragment.Callbacks {

    private boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_list);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        // Prevent the screen from turning off
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        if (findViewById(R.id.verification_detail_container) != null) {
            mTwoPane = true;
            ((VerificationListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.verification_list))
                    .setActivateOnItemClick(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.verifications_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        case R.id.menu_stats:
        	Intent settingsIntent = new Intent(this, StatsActivity.class);
        	startActivity(settingsIntent);
        	return true;
        default:
        	return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(long id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putLong(VerificationDetailFragment.ARG_ITEM_ID, id);
            VerificationDetailFragment fragment = new VerificationDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.verification_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, VerificationDetailActivity.class);
            detailIntent.putExtra(VerificationDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
