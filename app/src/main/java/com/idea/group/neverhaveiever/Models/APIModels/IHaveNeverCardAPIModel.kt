package com.idea.group.neverhaveiever.Models.APIModels

import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass

data class IHaveNeverCardAPIModel(@PrimaryKey var id: String = "",
                                  var info: String = "",var seen: Boolean,var votedBad: Boolean, var cardType : String) : RealmObject()