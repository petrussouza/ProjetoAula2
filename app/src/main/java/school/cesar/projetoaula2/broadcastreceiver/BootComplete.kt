package school.cesar.projetoaula2.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import school.cesar.projetoaula2.jobs.LembreteWork
import java.util.concurrent.TimeUnit

class BootComplete : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.d("MyFirstBoot", "Aconteceu o boot completed")
            val myWorkBuilder: PeriodicWorkRequest.Builder = PeriodicWorkRequest.Builder(LembreteWork::class.java, 24, TimeUnit.HOURS);
            val myWork: PeriodicWorkRequest = myWorkBuilder.build();
            WorkManager.getInstance().enqueueUniquePeriodicWork("jobLembrete", ExistingPeriodicWorkPolicy.KEEP, myWork);
        } else {
            Log.e("MyFirstBoot", "Erro no BOOT receiver, Action: ${intent?.action}")
        }

    }

}