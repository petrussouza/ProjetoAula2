package school.cesar.projetoaula2.jobs

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import school.cesar.projetoaula2.R
import school.cesar.projetoaula2.ui.activity.MainActivity

class LembreteWork(private val context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        lancaNotifacaoDiaria(context)
        return Result.success()
    }

    private fun lancaNotifacaoDiaria(context: Context){
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pedingIntent = PendingIntent.getActivity(context, 1234,
            intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, context.getString(R.string.my_app))
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        builder.setContentTitle(context.getString(R.string.app_name))
        builder.setContentText(context.getString(R.string.msg_nao_fique_de_fora_utilize_o_aplicativo))
        builder.setPriority(NotificationCompat.PRIORITY_HIGH)
        builder.setAutoCancel(true)
        builder.setContentIntent(pedingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(context.getString(R.string.my_app),
                context.getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notification = builder.build()
        notificationManager.notify(1234, notification)
    }
}