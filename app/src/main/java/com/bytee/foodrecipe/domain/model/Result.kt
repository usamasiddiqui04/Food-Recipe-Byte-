package com.bytee.foodrecipe.domain.model

import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("id")
    val id: Int,
    @SerialName("language")
    val language: String?="",
    @SerialName("name")
    val name: String?="",
    @SerialName("SerialName")
    val num_servings: Int?=0,
    @SerialName("nutrition_visibility")
    val nutrition_visibility: String?="",
    @SerialName("original_video_url")
    val original_video_url: String?="",
    @SerialName("prep_time_minutes")
    val prep_time_minutes: Int?=0,
    @SerialName("promotion")
    val promotion: String?="",
    @SerialName("seo_title")
    val seo_title: String?="",
    @SerialName("servings_noun_plural")
    val servings_noun_plural: String?="",
    @SerialName("servings_noun_singular")
    val servings_noun_singular: String?="",
    @SerialName("show_id")
    val show_id: Int?=0,
    @SerialName("slug")
    val slug: String?="",
    @SerialName("thumbnail_alt_text")
    val thumbnail_alt_text: String?="",
    @SerialName("thumbnail_url")
    val thumbnail_url: String?="",
    @SerialName("tips_and_ratings_enabled")
    val tips_and_ratings_enabled: Boolean?=false,
    @SerialName("total_time_minutes")
    val total_time_minutes: Int? = 0,
    @SerialName("updated_at")
    val updated_at: Int?=0,
    @SerialName("video_ad_content")
    val video_ad_content: String?= "",
    @SerialName("video_id")
    val video_id: Int?=0,
    @SerialName("video_url")
    val video_url: String?="",
    @SerialName("yields")
    val yields: String?="",
    @SerialName("description")
    val description : String?=""
)