package com.example.egardening.egardening;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.graphics.*;
import android.os.Bundle;
import android.view.Display;
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

        //Ayman 14/02/15
        //Profile info on the slider menu
        SharedPreferences sp = getSharedPreferences("SessionUser", 0);
        String username = sp.getString("USERNAME", null);
        //End Ayman


        menuItems = new MenuItem[]{
                new MenuItem("\ue9a4", "My garden"),
                new MenuItem("\uea0a", "Select plants"),
                new MenuItem("\ue953", "Calendar"),
                new MenuItem("\ue9d4", "Weather"),
                new MenuItem("\ue994", "Settings"),
        };

        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        dList = (ListView) findViewById(R.id.left_drawer);
        //Ayman 14/02/15
        //Setting the header of the slider menu
        View header = getLayoutInflater().inflate(R.drawable.profile_list_row, dList, false);
        //set header height programatically
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        header.getLayoutParams().height = height/3;
        ///
        dList.addHeaderView(header);
        TextView menuUsername = (TextView) findViewById(R.id.menu_username);
        menuUsername.setText("Hey " +username+ "!");
        //End Ayman
        adapter = new CustomListAdapter(this, R.drawable.list_row, menuItems);
        dList.setAdapter(adapter);
        dList.setSelector(android.R.color.darker_gray);

        //LinearLayout listHeader = (LinearLayout) findViewById(R.id.profile_info_container);
        //listHeader.getLayoutParams().height = 20;


        dList.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
                dLayout.closeDrawers();
                Fragment fragment = new FragmentsController();
                Bundle bundle = new Bundle();
                //bundle.putString("Menu", menu[position]);
                System.out.println(position);
                bundle.putInt("Menu", position);
                //bundle.putString("User");
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

                fragmentTitle = (TextView) findViewById(R.id.fragment_title);
                //Ayman 15/02/15 Fix
                if(position == 0 )
                    fragmentTitle.setText(menuItems[position].title);
                else
                    fragmentTitle.setText(menuItems[position-1].title);
                //End Ayman
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



        //Ayman 13/02/15
        //Automatically redirect to My Garden fragment when login is successfull
        Fragment fragment = new FragmentsController();
        Bundle bundle = new Bundle();
        int position = 0;
        bundle.putInt("Menu", position);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        fragmentTitle = (TextView) findViewById(R.id.fragment_title);
        fragmentTitle.setText(menuItems[position].title);
        //End Ayman

    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Main.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
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
