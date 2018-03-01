package matas.com.hayalhanem;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import matas.com.hayalhanem.fragment.HomePageFragment;
import matas.com.hayalhanem.fragment.MyContains;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = MainActivity.class.getSimpleName();

    /*@BindView(R.id.bt_video)
    Button btVideo;
    */

    private TextView bagis;
    private TextView iletisim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Always cast your custom Toolbar here, and set it as the ActionBar.
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home button
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        ab.setDisplayShowTitleEnabled(false); // disable the default title element here (for centered title)

        new MyContains(getSupportFragmentManager());

        Fragment mFragment = null;
        FragmentManager mFragmentManager = getSupportFragmentManager();
        mFragment = new HomePageFragment();
        mFragmentManager.beginTransaction().replace(R.id.main_fragment, mFragment).commit();

        //bagis = (TextView) findViewById(R.id.tv_bagis);
        //iletisim = (TextView) findViewById(R.id.tv_iletisim);

        //bagis.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/AdventPro-SemiBold.ttf"));
        //iletisim.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/AdventPro-SemiBold.ttf"));

    }
/*
    @OnClick(R.id.tv_bagis)
    public void onClickBagis() {
        Intent i = new Intent(MainActivity.this, BagisActivity.class);
        startActivity(i);
    }
*/

}
