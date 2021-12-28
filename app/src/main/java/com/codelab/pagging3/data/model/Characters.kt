package com.codelab.pagging3.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Characters(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val type: String,
    val gender: String,
    val image: String,
    val url: String,
    val origin: Origin,
    val episode: List<String>
): Parcelable
