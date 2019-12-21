package com.example.listmaker

import android.os.Parcel
import android.os.Parcelable

class TaskList constructor(val name: String?, val tasks: ArrayList<String>? = ArrayList<String>()) : Parcelable {

    constructor(source: Parcel) : this(
        source.readString(),
        source.createStringArrayList()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeStringList(tasks)
    }

    override fun describeContents() = 0

    companion object CREATOR: Parcelable.Creator<TaskList> {
        override fun createFromParcel(source: Parcel): TaskList {
            return TaskList(source)
        }

        override fun newArray(size: Int): Array<TaskList?> = arrayOfNulls(size)
    }
}