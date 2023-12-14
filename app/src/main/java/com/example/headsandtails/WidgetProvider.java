package com.example.headsandtails;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.widget.RemoteViews;

import java.util.Random;
import java.util.logging.Handler;

public class WidgetProvider extends AppWidgetProvider {

    private static final String ACTION_CHANGE_TEXT = "com.example.headsandtails.ACTION_CHANGE_TEXT";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            Intent intent = new Intent(context, WidgetProvider.class);
            intent.setAction(ACTION_CHANGE_TEXT);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            remoteViews.setOnClickPendingIntent(R.id.resultImageView, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent.getAction() != null && intent.getAction().equals(ACTION_CHANGE_TEXT)) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            int randomIndex = new Random().nextInt(2); // 0 или 1
            int[] imageResources = {R.drawable.ic_yes, R.drawable.ic_no, R.drawable.ic_load1, R.drawable.ic_load2, R.drawable.ic_load3, R.drawable.ic_load4};
            int[] goofyAssResources = {R.drawable.pic, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4, R.drawable.pic5, R.drawable.pic6, R.drawable.pic7, R.drawable.pic8,
                    R.drawable.pic9, R.drawable.pic10};
            int randomImageResource = imageResources[randomIndex];

            for (int i = 2; i < 6; i++) {
                remoteViews.setImageViewResource(R.id.resultImageView, imageResources[i]);

                int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            randomIndex = new Random().nextInt(10);
            if (randomIndex <= 4){
                randomIndex = new Random().nextInt(9);
                randomImageResource =  goofyAssResources[randomIndex];
            }

            remoteViews.setImageViewResource(R.id.resultImageView, randomImageResource);
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }
}
