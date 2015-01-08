package com.example.egardening.egardening;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.graphics.*;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class Main extends Activity {

    String[] menu;
    DrawerLayout dLayout;
    ListView dList;
    CustomListAdapter adapter;
    Typeface menuFont;
    TextView menuIcon, fragmentTitle;
    MenuItem menuItems[];
    LinearLayout menuIconContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        menuFont = Typeface.createFromAsset(getAssets(), "IcoMoon-Free.ttf");
        menuIcon = (TextView) findViewById(R.id.menu_icon);
        menuIcon.setTypeface(menuFont);
        menuIcon.setText("\ue9bd");

        menuItems = new MenuItem[]{
                new MenuItem("\ue9a4", "My garden"),
                new MenuItem("\uea0a", "Select plants"),
                new MenuItem("\ue953", "Calendar"),
                new MenuItem("\ue9d4", "Weather"),
                new MenuItem("\ue994", "Settings"),
        };

        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        dList = (ListView) findViewById(R.id.left_drawer);
        adapter = new CustomListAdapter(this, R.drawable.list_row, menuItems);
        dList.setAdapter(adapter);
        dList.setSelector(android.R.color.darker_gray);




        dList.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
                dLayout.closeDrawers();
                Fragment fragment = new FragmentsController();
                Bundle bundle = new Bundle();
                //bundle.putString("Menu", menu[position]);
                bundle.putInt("Menu", position);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

                fragmentTitle = (TextView) findViewById(R.id.fragment_title);
                fragmentTitle.setText(menuItems[position].title);

            }
        });

        menuIconContainer = (LinearLayout) findViewById(R.id.menu_icon_container);

        menuIconContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dLayout.isDrawerOpen(dList)){
                    dLayout.closeDrawers();
                }
                else{
                    dLayout.openDrawer(dList);
                }

            }


        });

    }

    /*
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
    }*/
}
