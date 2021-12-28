package com.codelab.pagging3.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Episode(
    val id: Int,
    val name: String,
    val airDate: String,
    val code: String,
    val characters: List<String>,
    var page: Int?
): Parcelable