package com.idea.group.neverhaveiever.Controllers;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

 @Suppress("UNCHECKED_CAST")
 class BaseControllerFactory<T>(val creator:() -> T) : ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return creator() as T
    }
}
