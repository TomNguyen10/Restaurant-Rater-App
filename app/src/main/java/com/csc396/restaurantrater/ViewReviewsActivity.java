package com.csc396.restaurantrater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.csc396.restaurantrater.databinding.ActivityViewReviewsBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ViewReviewsActivity extends AppCompatActivity {

    private ActivityViewReviewsBinding binding;
    ArrayList<Review> myReview = new ArrayList<>();
    CustomAdapter myAdapter = null;

    public static final int FROM_ACTIVITY_ADD_NEW_REVIEW = 1;

    private AdapterView.OnItemClickListener listviewReview_onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Review theReview = (Review) parent.getItemAtPosition(position);
            String date = theReview.getDate();
            String time = theReview.getTime();
            AlertDialog.Builder myBuilder = new AlertDialog.Builder(ViewReviewsActivity.this);
            myBuilder.setTitle("Review Details")
                     .setMessage("This review was created on " + date + " at " + time);
            AlertDialog myDialog = myBuilder.create();
            myDialog.show();
        }
    };

    private View.OnClickListener button_add_review_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent theIntent = new Intent(ViewReviewsActivity.this, AddReviewActivity.class);
            startActivityForResult(theIntent, FROM_ACTIVITY_ADD_NEW_REVIEW);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewReviewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        readCSV();
        myAdapter = new CustomAdapter(this, myReview);
        binding.listviewReviews.setAdapter(myAdapter);
        binding.listviewReviews.setOnItemClickListener(listviewReview_onItemClickListener);
        binding.buttonAddReview.setOnClickListener(button_add_review_clickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FROM_ACTIVITY_ADD_NEW_REVIEW && resultCode == Activity.RESULT_OK) {
            myReview.add((Review) data.getSerializableExtra(AddReviewActivity.EXTRA_NEW_REVIEW));
            myAdapter.notifyDataSetChanged();
        }
    }

    public void readCSV () {
        try {
            File file = new File("/data/data/com.csc396.restaurantrater/files/reviews.csv");
            Scanner myScanner = new Scanner(file);
            while (myScanner.hasNextLine()) {
                String line = myScanner.nextLine();
                Log.d("396", line);
                String[] reviews = line.split(",");
                myReview.add(new Review(reviews[0], reviews[1], reviews[2], reviews[3], Integer.valueOf(reviews[4]), reviews[5].equals("1")));
            }
            myScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}