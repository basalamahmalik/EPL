package epl.app.net.basalamah.malik.epl_1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import epl.app.net.basalamah.malik.epl_1.Adapters.ViewPagerAdapter;
import epl.app.net.basalamah.malik.epl_1.Fragments.FixtureFragment;
import epl.app.net.basalamah.malik.epl_1.Fragments.StandingFragment;


public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.ic_grade,
            R.drawable.ic_list
    };

   @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


       setContentView(R.layout.activity_main);


       toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
       toolbar.setLogo(R.mipmap.ic_logo);

       viewPager = (ViewPager) findViewById(R.id.pager);
       setupViewPager(viewPager);
       //mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
       //viewPager.setAdapter(mAdapter);
       // Give the TabLayout the ViewPager
       tabLayout = (TabLayout) findViewById(R.id.tabs);
       tabLayout.setupWithViewPager(viewPager);
       setupTabIcons();
   }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

   @Override
   protected void onStart() {
        super.onStart();
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FixtureFragment(),  this.getResources().getString(R.string.fixture));
        adapter.addFragment(new StandingFragment(), this.getResources().getString(R.string.standing));
        viewPager.setAdapter(adapter);
    }

    public static boolean InternetConnection(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);


		        /* wifi confirm */
        NetworkInfo wifiNetworkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Boolean isWifi = (wifiNetworkInfo == null) ? false : wifiNetworkInfo.isConnectedOrConnecting();

        NetworkInfo mobileNetworkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		        /* 3G confirm */
        Boolean is3g = (mobileNetworkInfo == null) ? false : mobileNetworkInfo.isConnectedOrConnecting();


        if (isWifi | is3g ) {
            //Ok and do nothing
            return true;
        } else {
            //there is no internet activity and return false to let app show dialog to enable interner
            return false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
