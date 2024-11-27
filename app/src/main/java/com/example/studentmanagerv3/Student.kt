package com.example.studentmanagerv3

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable


data class Student(
    var id: String,
    var name: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    // Ghi dữ liệu vào Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    // Mô tả loại đặc biệt (luôn trả về 0)
    override fun describeContents(): Int = 0

    // Companion object để tạo đối tượng từ Parcel
    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(parcel: Parcel): Student {
            return Student(parcel)
        }

        override fun newArray(size: Int): Array<Student?> {
            return arrayOfNulls(size)
        }
    }
}