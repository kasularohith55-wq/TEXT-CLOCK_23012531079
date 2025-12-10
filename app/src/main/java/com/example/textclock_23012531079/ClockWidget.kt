package com.example.textclock_23012531079


import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.*

class ClockWidget : AppWidgetProvider() {

    companion object {
        private val timeFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
        private val ampmFormat = SimpleDateFormat("a", Locale.getDefault())
        private val dateFormat = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (widgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, widgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        // refresh widget if time tick broadcast received
        if (intent.action == Intent.ACTION_TIME_TICK || intent.action == Intent.ACTION_TIME_CHANGED) {
            val mgr = AppWidgetManager.getInstance(context)
            val ids = mgr.getAppWidgetIds(ComponentName(context, ClockWidget::class.java))
            for (id in ids) updateAppWidget(context, mgr, id)
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val now = Date()
        val views = RemoteViews(context.packageName, R.layout.widget_clock)
        views.setTextViewText(R.id.widget_time, timeFormat.format(now))
        views.setTextViewText(R.id.widget_ampm, ampmFormat.format(now))
        views.setTextViewText(R.id.widget_date, dateFormat.format(now))

        // make widget open the main app when tapped
        val intent = Intent(context, MainActivity::class.java)
        val pending = PendingIntent.getActivity(
            context,
            0,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            else PendingIntent.FLAG_UPDATE_CURRENT
        )
        views.setOnClickPendingIntent(R.id.widgetRoot, pending)
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}
