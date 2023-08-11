package com.csc396.restaurantrater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private List<Review> reviews;

    public CustomAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int position) {
        return reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_review, parent, false);
        }
        Review thisReview = reviews.get(position);

        RatingBar reviewIsFavorite = convertView.findViewById(R.id.ratingBar_isFavorite);
        TextView reviewName = convertView.findViewById(R.id.textView_restaurant_name);
        RadioGroup reviewMeal = convertView.findViewById(R.id.radioGroup_meal);
        ProgressBar reviewRating = convertView.findViewById(R.id.progressBar_rating);

        if (thisReview.isFavorite()) {
            reviewIsFavorite.setProgress(reviewIsFavorite.getMax());
        }
        else {
            reviewIsFavorite.setProgress(reviewIsFavorite.getMin());
        }
        reviewName.setText(thisReview.getName());

        switch (thisReview.getMeal()) {
            case "Breakfast":
                reviewMeal.check(R.id.radioButton_breakfast);
                break;
            case "Lunch":
                reviewMeal.check(R.id.radioButton_lunch);
                break;
            case "Dinner":
                reviewMeal.check(R.id.radioButton_dinner);
                break;
        }

        reviewRating.setProgress(thisReview.getRating());

        return convertView;
    }
}
