package com.example.gardenguru
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.Notification
import com.example.gardenguru.Notifications
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import com.example.gardenguru.databinding.FragmentNotificationsBinding
import java.util.Calendar
import android.provider.Settings
import android.util.Log
import java.util.Date


class FragmentNotifications: Fragment() {

    private lateinit var binding: FragmentNotificationsBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using ViewBinding
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        createNotificationChannel()

        binding.submitButton.setOnClickListener { scheduleNotification() }

        return binding.root // Return the root view from the ViewBinding
    }

    private fun scheduleNotification() {
        Log.d("FragmentNotifications", "This is a debug log message")
        val appContext = requireActivity()
        val intent = Intent(appContext, Notifications::class.java)
        val title = binding.titleET.text.toString()
        val message = binding.messageET.text.toString()
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            appContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = getSystemService(appContext, AlarmManager::class.java)
        val time = getTime()
        Log.d("FragmentNotifications", "All the stuff before hand happened!")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager != null) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        time,
                        pendingIntent
                    )
                    Log.d("FragmentNotifications", "alarmManager != null")
                } else {
                    val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                    startActivity(intent)
                }
                showAlert(time, title, message)
            }
        } else {
            // For older versions, just set the alarm without the permission check
            alarmManager?.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                time,
                pendingIntent
            )
        }

    }

    private fun showAlert(time: Long, title: String, message: String) {
        val appContext = requireActivity()
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(appContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(appContext)

        AlertDialog.Builder(appContext)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: " + title +
                "\nMessage: " + message +
                "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date))
            .setPositiveButton("Okay"){_,_ ->}
            .show()
    }

    private fun getTime(): Long {
        val minute = binding.timePicker.minute
        val hour = binding.timePicker.hour
        val day = binding.datePicker.dayOfMonth
        val month = binding.datePicker.month
        val year = binding.datePicker.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Notification Channel"
        val desc = "A description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = requireContext().getSystemService(NotificationManager::class.java) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }

    companion object {
        fun newInstance(): FragmentNotifications {
            return FragmentNotifications()
        }
    }
}






