package com.idea.group.neverhaveiever.Models.UIModels

import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class SampleUIModel : RealmModel {
    var name: String = ""
}