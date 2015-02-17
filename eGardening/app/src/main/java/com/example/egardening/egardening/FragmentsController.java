package com.example.egardening.egardening;

/**
 * Created by Ayman on 25/12/2014.
 */
import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import com.example.egardening.egardening.Main;
import com.facebook.Session;
import com.facebook.android.Facebook;

public class FragmentsController extends Fragment {
    TextView text;
    Typeface font;
    //Calendar variables
    TextView lArrow, rArrow, season;
    ArrayList<String> seasons;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle bundle) {

        View view = null;

        Bundle b = getArguments();
        int itemPosition = b.getInt("Menu");


        if((itemPosition==0) || (itemPosition==1)) {
            view = inflater.inflate(R.layout.my_garden_fragment, container, false);
        }

        else if(itemPosition==2) {
            view = inflater.inflate(R.layout.select_fragment, container, false);
        }

        else if(itemPosition==3) {
            view = inflater.inflate(R.layout.calendar_fragment, container, false);
            manageCalendar(view);
        }

        else if(itemPosition==4) {
            view = inflater.inflate(R.layout.weather_fragment, container, false);
        }

        else if(itemPosition==5) {
            System.out.println("settings");
            view = inflater.inflate(R.layout.settings_fragment, container, false);
            manageSettings(view);
        }


        return view;
    }


    public void manageMyGarden(View view) {

    }

    public void manageCalendar(View view){
        lArrow = (TextView) view.findViewById(R.id.left_arrow);
        rArrow = (TextView) view.findViewById(R.id.right_arrow);
        font = Typeface.createFromAsset(getActivity().getAssets(), "IcoMoon-Free.ttf");

        lArrow.setTypeface(font);
        rArrow.setTypeface(font);

        seasons = new ArrayList<String>();
        seasons.add("Winter"); seasons.add("Spring"); seasons.add("Summer"); seasons.add("Autumn");

        season = (TextView) view.findViewById(R.id.season);
        season.setText(getSeason());

        lArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (season.getText().toString().equals("Winter"))
                    season.setText("Autumn");
                else if (season.getText().toString().equals("Spring"))
                    season.setText("Winter");
                else if (season.getText().toString().equals("Summer"))
                    season.setText("Spring");
                else if (season.getText().toString().equals("Autumn"))
                    season.setText("Summer");
            }
        });

        rArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (season.getText().toString().equals("Winter"))
                    season.setText("Spring");
                else if (season.getText().toString().equals("Spring"))
                    season.setText("Summer");
                else if (season.getText().toString().equals("Summer"))
                    season.setText("Autumn");
                else if (season.getText().toString().equals("Autumn"))
                    season.setText("Winter");
            }
        });
    }


    public void manageSettings(View view) {
        final Session session = Session.getActiveSession();
        Button logout = (Button) view.findViewById(R.id.button_logout);
        logout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                session.closeAndClearTokenInformation();
            }
        });
    }

    public String getSeason(){
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);

        if(month==11 || month==0 || month==1){
            return "Winter";
        }

        else if(2<=month && month<=4){
            return "Spring";
        }

        else if(5<=month && month<=7){
            return "Summer";
        }
        else if(9<=month && month<=11){
            return "Autumn";
        }

        return null;
    }




}
