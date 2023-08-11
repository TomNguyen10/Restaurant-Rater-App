package com.csc396.restaurantrater;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.csc396.restaurantrater.databinding.ActivityAddReviewBinding;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class AddReviewActivity extends AppCompatActivity {

    private ActivityAddReviewBinding binding;

    public static final String EXTRA_NEW_REVIEW = "com.csc396.restaurantrater.EXTRA_NEW_REVIEW";

    private DatePickerDialog.OnDateSetListener datepicker_orderdate_dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String date = String.valueOf(month+1) + "/" + String.valueOf(dayOfMonth) + "/" + String.valueOf(year);
            binding.edittextReviewDate.setText(date);
        }
    };

    private View.OnClickListener edit_text_review_date_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar myCalendar = Calendar.getInstance();
            DatePickerDialog myDater = new DatePickerDialog(AddReviewActivity.this, datepicker_orderdate_dateSetListener, myCalendar.get(Calendar.YEAR),
                                                                                 myCalendar.get(Calendar.MONTH),
                                                                                 myCalendar.get(Calendar.DAY_OF_MONTH));
            myDater.show();
        }
    };

    private TimePickerDialog.OnTimeSetListener timepicker_ordertime_timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String half = "AM";
            if (hourOfDay > 12)  {
                hourOfDay -= 12;
                half = "PM";
            }
            String hour = String.valueOf(hourOfDay);
            String theMinute = String.valueOf(minute);
            if (minute < 10) {
                theMinute = "0"+theMinute;
            }
            String time = hour + ":" + theMinute + " " + half;
            binding.edittextReviewTime.setText(time);
        }
    };

    private View.OnClickListener edit_text_review_time_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar myCalendar = Calendar.getInstance();
            TimePickerDialog myTimer = new TimePickerDialog(AddReviewActivity.this, timepicker_ordertime_timeSetListener, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true);
            myTimer.show();
        }
    };

    private View.OnClickListener button_add_review_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent returnIntent = new Intent();
            String name = binding.edittextRestaurantName.getText().toString();
            String date = binding.edittextReviewDate.getText().toString();
            String time = binding.edittextReviewTime.getText().toString();
            RadioButton checkedMeal = findViewById(binding.radiogroupMeals.getCheckedRadioButtonId());
            String meal = checkedMeal.getText().toString();
            int progressRating = binding.seekbarRating.getProgress();
            String rating = String.valueOf(progressRating);
            boolean isFavoriteChecked = binding.checkboxFavorite.isChecked();
            String isFavorite = (isFavoriteChecked) ? "1" : "0";

            try {
                FileWriter fileWriter = new FileWriter("/data/data/com.csc396.restaurantrater/files/reviews.csv", true);
                fileWriter.append(name+","+date+","+time+","+meal+","+rating+","+isFavorite+"\n");
                fileWriter.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            Review addReview = new Review(name, date, time, meal, progressRating, isFavoriteChecked);
            returnIntent.putExtra(EXTRA_NEW_REVIEW, addReview);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.edittextReviewDate.setOnClickListener(edit_text_review_date_clickListener);
        binding.edittextReviewTime.setOnClickListener(edit_text_review_time_clickListener);
        binding.buttonAddReview.setOnClickListener(button_add_review_clickListener);
        Intent myIntent = getIntent();
    }
}