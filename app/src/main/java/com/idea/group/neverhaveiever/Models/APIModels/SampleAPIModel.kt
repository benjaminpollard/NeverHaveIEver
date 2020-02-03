package com.idea.group.neverhaveiever.Models.APIModels

import com.squareup.moshi.Json

data class UserEntity(@field:Json(name = "id") val id: String,
                      @field:Json(name = "name") val name: String)