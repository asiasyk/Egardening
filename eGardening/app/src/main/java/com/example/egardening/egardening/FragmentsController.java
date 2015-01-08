package com.example.egardening.egardening;

/**
 * Created by Ayman on 25/12/2014.
 */
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

public class FragmentsController extends Fragment {
    TextView text;
    Typeface font;
    //Calendar variables
    TextView lArrow, rArrow, season;
    ArrayList<String> seasons;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle bundle) {

        View view = null;
        //String itemPosition = getArguments().getString("Menu");

        Bundle b = getArguments();
        int itemPosition = b.getInt("Menu", 0);


        if(itemPosition==0)
            view = inflater.inflate(R.layout.my_garden_fragment, container, false);
        else if(itemPosition==1)
            view = inflater.inflate(R.layout.select_fragment, container, false);
        else if(itemPosition==2){
            view = inflater.inflate(R.layout.calendar_fragment, container, false);
            manageCalendar(view);
        }

        else if(itemPosition==3)
            view = inflater.inflate(R.layout.weather_fragment, container, false);
        else if(itemPosition==4)
            view = inflater.inflate(R.layout.settings_fragment, container, false);

        //text= (TextView) view.findViewById(R.id.title);
        //text.setText(String.valueOf(itemPosition));

        return view;
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
