@file:Suppress("ClassName")

package com.todoroo.astrid.service

import com.todoroo.astrid.data.Task.Companion.NOTIFY_AFTER_DEADLINE
import com.todoroo.astrid.data.Task.Companion.NOTIFY_AT_DEADLINE
import org.tasks.R
import org.tasks.preferences.Preferences
import javax.inject.Inject

class Upgrade_11_12 @Inject constructor(
    private val preferences: Preferences
) {
    internal fun migrateDefaultReminderPreference() {
        val pref = preferences.getIntegerFromString(
            LEGACY_PREFERENCE,
            NOTIFY_AT_DEADLINE or NOTIFY_AFTER_DEADLINE
        )
        preferences.setStringSet(
            R.string.p_default_reminders_key,
            listOf(NOTIFY_AT_DEADLINE, NOTIFY_AFTER_DEADLINE)
                .filter { pref and it > 0 }
                .map { it.toString() }
                .toHashSet()
        )
    }

    companion object {
        const val VERSION = 111200
        private const val LEGACY_PREFERENCE = "p_def_reminders"
    }
}