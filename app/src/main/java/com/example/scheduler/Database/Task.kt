package com.example.scheduler.Database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "TasksTable")
data class Task(
    @ColumnInfo val TaskName: String,
    @ColumnInfo val Time: String
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
        id = parcel.readInt()
    }

    override fun equals(other: Any?): Boolean {
        val otherTask: Task = other as Task
        if (otherTask.hashCode() == this.hashCode())
            return true
        return false
    }

    override fun hashCode(): Int {
        var result = TaskName.hashCode()
        result = 31 * result + Time.hashCode()
        result = 31 * result + id
        return result
    }

}