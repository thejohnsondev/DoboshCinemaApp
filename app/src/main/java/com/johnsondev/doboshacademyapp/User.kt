package com.johnsondev.doboshacademyapp

import android.os.Parcel
import android.os.Parcelable

data class User(
        val name: String?,
        val age: Int?
) : Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readInt()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        writeString(name)
        if (age != null) {
            writeInt(age)
        }
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(parcel: Parcel): User {
                return User(parcel)
            }

            override fun newArray(size: Int): Array<User?> {
                return arrayOfNulls(size)
            }
        }
    }
}