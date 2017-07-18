package com.conflux.coinflux.demoobjectbox;

import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.app.RemoteAction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.util.Rational;
import android.view.View;
import android.widget.Button;

import com.conflux.coinflux.demoobjectbox.Widget.MovieView;

import java.util.ArrayList;

public class PictureModeActivity extends BaseActivity {

    /**
     * The arguments to be used for Picture-in-Picture mode.
     */
    private final PictureInPictureParams.Builder mPictureInPictureParamsBuilder =
            new PictureInPictureParams.Builder();

    /**
     * This shows the video.
     */
    private MovieView mMovieView;

//    /** The bottom half of the screen; hidden on landscape */
//    private ScrollView mScrollView;

    /**
     * A {@link BroadcastReceiver} to receive action item events from Picture-in-Picture mode.
     */
    private BroadcastReceiver mReceiver;

    private Button mEnter;
    private String mPlay;
    private String mPause;

    //Step -1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_mode);

        mPlay = getString(R.string.play);
        mPause = getString(R.string.pause);
        mMovieView = findViewById(R.id.movie);

        // Set up the video; it automatically starts.
        mMovieView.setMovieListener(mMovieListener);
        //mEnter = findViewById(R.id.enter);

//        mEnter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View view) {
//                startActivity(new Intent(PictureModeActivity.this, AutoFillActivity.class));
//                finish();
//            }
//        });
    }


    /**
     * Callbacks from the MovieView
     * Step -2
     */
    private MovieView.MovieListener mMovieListener = new MovieView.MovieListener() {

        //Play video Now and PiP mode show action in pause to play
        @Override
        public void onMovieStarted() {
            updatePictureInPictureActions(R.drawable.ic_pause_24dp, mPause, CONTROL_TYPE_PAUSE,
                    REQUEST_PAUSE);
        }

        //when video is stopped or end
        @Override
        public void onMovieStopped() {
            updatePictureInPictureActions(R.drawable.ic_play_arrow_24dp, mPlay, CONTROL_TYPE_PLAY,
                    REQUEST_PLAY);
        }

        // The MovieView wants us to minimize it. We enter Picture-in-Picture mode now.
        @Override
        public void onMovieMinimized() {
            minimize();
        }
    };


    /**
     * Step 3 if it not minimized else minimize will be the step 3
     * Update the state
     *
     * @param iconId      icon
     * @param title       title
     * @param controlType action.
     * @param requestCode request code
     */
    void updatePictureInPictureActions(@DrawableRes int iconId, String title, int controlType,
                                       int requestCode) {
        final ArrayList<RemoteAction> actions = new ArrayList<>();

        // This is the PendingIntent that is invoked when a user clicks on the action item.
        // You need to use distinct request codes for play and pause, or the PendingIntent won't
        // be properly updated.
        final PendingIntent intent = PendingIntent.getBroadcast(this,
                requestCode, new Intent(ACTION_MEDIA_CONTROL)
                        .putExtra(EXTRA_CONTROL_TYPE, controlType), 0);
        final Icon icon = Icon.createWithResource(this, iconId);
        actions.add(new RemoteAction(icon, title, title, intent));

        // Another action item. This is a fixed action.
        actions.add(new RemoteAction(
                Icon.createWithResource(this, R.drawable.ic_info_24dp),
                getString(R.string.info), getString(R.string.info_description),
                PendingIntent.getActivity(this, REQUEST_INFO,
                        new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.info_uri))),
                        0)));

        mPictureInPictureParamsBuilder.setActions(actions).build();

        // This is how you can update action items (or aspect ratio) for Picture-in-Picture mode.
        // Note this call can happen even when the app is not in PiP mode. In that case, the
        // arguments will be used for at the next call of #enterPictureInPictureMode.
        setPictureInPictureParams(mPictureInPictureParamsBuilder.build());
    }


    /**
     * Enters Picture-in-Picture mode.
     * Step 3 Part - 2
     */
    void minimize() {
        if (mMovieView == null) {
            return;
        }
        // Hide the controls in picture-in-picture mode.
        mMovieView.hideControls();
        // Calculate the aspect ratio of the PiP screen.
        Rational aspectRatio = new Rational(mMovieView.getWidth(), mMovieView.getHeight());
        mPictureInPictureParamsBuilder.setAspectRatio(aspectRatio).build();
        enterPictureInPictureMode(mPictureInPictureParamsBuilder.build());
    }


    @Override
    protected void onStop() {
        // On entering Picture-in-Picture mode, onPause is called, but not onStop.
        // For this reason, this is the place where we should pause the video playback.
        mMovieView.pause();
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!isInPictureInPictureMode()) {
            // Show the video controls so the video can be easily resumed.
            mMovieView.showControls();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adjustFullScreen(newConfig);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            adjustFullScreen(getResources().getConfiguration());
        }
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (isInPictureInPictureMode) {
            // Starts receiving events from action items in PiP mode.
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent == null || !ACTION_MEDIA_CONTROL.equals(intent.getAction())) {
                        return;
                    }


                    // This is where we are called back from Picture-in-Picture action items.
                    final int controlType = intent.getIntExtra(EXTRA_CONTROL_TYPE, 0);
                    switch (controlType) {
                        case CONTROL_TYPE_PLAY:
                            mMovieView.play();
                            break;
                        case CONTROL_TYPE_PAUSE:
                            mMovieView.pause();
                            break;
                    }
                }
            };
            registerReceiver(mReceiver, new IntentFilter(ACTION_MEDIA_CONTROL));
        } else {
            // We are out of PiP mode. We can stop receiving events from it.
            unregisterReceiver(mReceiver);
            mReceiver = null;
            // Show the video controls if the video is not playing
            if (mMovieView != null && !mMovieView.isPlaying()) {
                mMovieView.showControls();
            }
        }
    }


    /**
     * Adjusts immersive full-screen flags depending on the screen orientation.
     *
     * @param config The current {@link Configuration}.
     */
    private void adjustFullScreen(Configuration config) {
        final View decorView = getWindow().getDecorView();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            // mScrollView.setVisibility(View.GONE);
            mMovieView.setAdjustViewBounds(false);
        } else {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //  mScrollView.setVisibility(View.VISIBLE);
            mMovieView.setAdjustViewBounds(true);
        }
    }

}
