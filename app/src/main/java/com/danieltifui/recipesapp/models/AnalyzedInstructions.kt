package com.danieltifui.recipesapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class AnalyzedInstructions(
    @SerializedName("steps")
    val steps: @RawValue List<Step>
) : Parcelable