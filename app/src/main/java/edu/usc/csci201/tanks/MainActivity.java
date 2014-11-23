package edu.usc.csci201.tanks;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import edu.usc.csci201.tanks.graphics.MainView;

public class MainActivity extends Activity implements SurfaceHolder.Callback {
    protected MainView tanksView;
    protected Canvas canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SurfaceView surfaceView = (SurfaceView)findViewById(R.id.surface);
        surfaceView.getHolder().addCallback(this);

        tanksView = new MainView(Resources.getSystem());
    }

    public void surfaceCreated(SurfaceHolder holder) {
        this.canvas = holder.lockCanvas();
        if (canvas != null) {
            tanksView.setCanvas(canvas);
            tanksView.setFrame(0,0,canvas.getWidth(),canvas.getHeight());
            tanksView.startDrawing();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int frmt, int w, int h) {
        /*
        holder.unlockCanvasAndPost(canvas);
        this.canvas = holder.lockCanvas();
        if (canvas != null) {
            tanksView.setCanvas(canvas);
            tanksView.setFrame(0,0,canvas.getWidth(),canvas.getHeight());
            tanksView.startDrawing();
        }
        */
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        surfaceHolder.unlockCanvasAndPost(canvas);
        tanksView.stopDrawing();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
