package com.example.gogreen_new;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.gogreen_new.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MealsFragment extends Fragment {
    StringBuilder sb = new StringBuilder();

    /**
     * opens meal fragment
     * @param inflater inflater
     * @param container container
     * @param savedInstanceState instance state
     * @return View
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mealView = inflater.inflate(R.layout.meals_fragment, container, false);
        final JSONObject obj = new JSONObject();
        final CheckBox box = mealView.findViewById(R.id.checkBox);
        final Button button = mealView.findViewById(R.id.add_meal);
        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (box.isChecked()) {
                    try {
                        obj.put("ingredient", "ingredient");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(obj.toString());
            }
        });
        return mealView;

    }
}


