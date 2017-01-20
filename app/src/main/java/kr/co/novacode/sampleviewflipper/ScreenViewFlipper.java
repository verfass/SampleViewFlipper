package kr.co.novacode.sampleviewflipper;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * Created by verfa on 2017-01-20.
 */

public class ScreenViewFlipper extends LinearLayout implements View.OnTouchListener {

    public static int countIndexes = 3;
    LinearLayout buttonLayout;
    ImageView[] indexButtons;
    View[] views;
    ViewFlipper flipper;

    float downX;
    float upX;

    int currentIndex = 0;

    public ScreenViewFlipper(Context context) {
        super(context);
        init(context);
    }

    public ScreenViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        setBackgroundColor(0xffbfbfbf);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.screenview, this, true);

        buttonLayout = (LinearLayout)findViewById(R.id.buttonLayout);
        flipper = (ViewFlipper)findViewById(R.id.flipper);
        flipper.setOnTouchListener(this);

        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        params.leftMargin = 50;

        indexButtons = new ImageView[countIndexes];
        views = new TextView[countIndexes];

        for (int i = 0; i < countIndexes; i++) {
            indexButtons[i] = new ImageView(context);

            if (i == currentIndex) {
                indexButtons[i].setImageResource(R.drawable.green);
            } else {
                indexButtons[i].setImageResource(R.drawable.white);
            }

            indexButtons[i].setPadding(10, 10, 10, 10);
            buttonLayout.addView(indexButtons[i], params);

            TextView curView = new TextView(context);
            curView.setText("View #" + i);
            curView.setTextColor(Color.RED);
            curView.setTextSize(32);
            views[i] = curView;

            flipper.addView(views[i]);
        }

    }

    private void updateIndexes() {
        for (int i = 0; i < countIndexes; i++) {
            if (i == currentIndex) {
                indexButtons[i].setImageResource(R.drawable.green);
            } else {
                indexButtons[i].setImageResource(R.drawable.white);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v != flipper) return false;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downX = event.getX();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            upX = event.getX();

            if (upX < downX) {
                flipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.wallpaper_open_enter));
                flipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.wallpaper_open_exit));

                if (currentIndex < (countIndexes-1)) {
                    flipper.showNext();
                    currentIndex++;
                    updateIndexes();
                }
            } else if (upX > downX) {
                flipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_right_in));
                flipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_right_out));

                if (currentIndex > 0) {
                    flipper.showPrevious();
                    currentIndex--;
                    updateIndexes();
                }
            }
        }
        return true;
    }
}
