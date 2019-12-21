package com.example.listmaker

import android.content.Context
import android.preference.PreferenceManager

class ListDataManager(val context: Context) {

    fun saveList(list: TaskList) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPreferences.putStringSet(list.name, list.tasks?.toHashSet()) // cannot save directly into String, so use HashSet
        sharedPreferences.apply()
    }

    fun readLists(): ArrayList<TaskList> {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val sharedPreferenceContents = sharedPreferences.all // get contents of SharedPreferences as a Map
                                                             // Map has unique keys. A map holds only one value for each key
        val taskLists = ArrayList<TaskList>()
        for (taskList in sharedPreferenceContents) {
            val itemsHashSet = taskList.value as HashSet<String>
            val list = TaskList(taskList.key, ArrayList(itemsHashSet))
            taskLists.add(list)
        }
        return taskLists
    }
}