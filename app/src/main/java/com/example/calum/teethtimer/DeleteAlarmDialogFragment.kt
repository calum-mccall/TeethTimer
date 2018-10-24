package com.example.calum.teethtimer

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle

class DeleteAlarmDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Test")
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                        // Positive button
                        var setAlarm = SetAlarm()
                        setAlarm.deleteAlarm(context)
                        this.activity.finish()
                    })
                    .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                        // Negative button
                    })
                    builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}