package com.example.summercoding;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.summercoding.Adapter.TabPagerAdapter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tb = (Toolbar) findViewById(R.id.app_toolbar);
        tb.setTitle("");
        setSupportActionBar(tb);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Monthly"));
        tabLayout.addTab(tabLayout.newTab().setText("Weekly"));
        tabLayout.addTab(tabLayout.newTab().setText("Daily"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);

        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // 앱 실행시 lastFragment.txt라는 파일이 있으면 해당 파일에서 마지막으로 사용한 Fragment 번호를 가져와 해당 Fragment를 띄워줌
        File Fp = new File(getFilesDir(), "lastFragment.txt");
        FileReader Fr = null;
        try{
            if(Fp.exists()){
                Fr = new FileReader(Fp);
                int lastFragmentInt = Fr.read();
                char lastFragmentChar = (char)lastFragmentInt;
                int curItem = Integer.parseInt(lastFragmentChar + "");
                tabLayout.getTabAt(curItem).select();
                if(Fr != null) Fr.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu) ;
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add :
                intent = new Intent(MainActivity.this,AddSchedule.class);
                startActivity(intent);
                return true ;
            default :
                return super.onOptionsItemSelected(item) ;
        }
    }

    @Override
    protected void onDestroy() {        // 앱 종료시 마지막으로 사용한 fragment 번호호를 파로 저장
        File Fp = new File(getFilesDir(), "lastFragment.txt");
        FileWriter Fw = null;
        try {
            if (!Fp.exists()) {
                Fp.createNewFile();
            }
            Fw = new FileWriter(Fp);
            Fw.write(Integer.toString(tabLayout.getSelectedTabPosition()));
            if(Fw != null) Fw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        super.onDestroy();
    }
}