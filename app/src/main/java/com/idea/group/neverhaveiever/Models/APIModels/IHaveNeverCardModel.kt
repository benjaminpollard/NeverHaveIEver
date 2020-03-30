package com.idea.group.neverhaveiever.Models.APIModels

import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

//cant use data class with realm yet
@RealmClass
public open class IHaveNeverCardModel( var id: String = "",
                                @PrimaryKey var info: String = "", var seen: Boolean = false, var votedBad: Boolean= false, var cardType : String = "") : RealmObject()