package com.idea.group.neverhaveiever.Controllers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.idea.group.neverhaveiever.Models.APIModels.IHaveNeverCardModel
import com.idea.group.neverhaveiever.Services.AnalyticsService
import mosquito.digital.template.mdpersistence.DatabaseUpdate
import mosquito.digital.template.mdpersistence.PersistenceService

class NeverHaveIEverController(presistenceService : PersistenceService, analyticsService: AnalyticsService, cardType : String) :
    ViewModel()
{
    var presistenceService : PersistenceService
    var analyticsService: AnalyticsService;
    var cardType : String
    init {
        this.presistenceService = presistenceService
        this.cardType = cardType
        this.analyticsService = analyticsService;
    }

    val cards: MutableLiveData<List<IHaveNeverCardModel>> by lazy {
        MutableLiveData<List<IHaveNeverCardModel>>()
    }

    private fun getCards() : List<IHaveNeverCardModel>
    {
        val list = mutableListOf<IHaveNeverCardModel>()
        list.add(IHaveNeverCardModel(id = "1", info = "Had Sex on a Boat", seen = false,votedBad = false, cardType = ""))
        list.add(IHaveNeverCardModel(id = "2", info = "Fallen down a hill drunk", seen = false,votedBad = false, cardType = ""))

        list.removeAll { iHaveNeverCardModel: IHaveNeverCardModel -> list.any {
            iHaveNeverCardModel.votedBad
        } }

        list.random()

        val listOfType = mutableListOf<IHaveNeverCardModel>()

        for (item in list.toList())
        {
            if(item.cardType == cardType)
            {
                list.remove(item)
                listOfType.add(item)
            }
        }

        val listOfSeen = mutableListOf<IHaveNeverCardModel>()

        for (item in list.toList())
        {
            if(item.seen)
            {
                list.remove(item)
                listOfSeen.add(item)
            }
        }
        list.addAll(list.lastIndex,listOfSeen)

        cards.postValue(list)
        return list
    }

    fun ItemVotedBad(pos: Int)
    {
            val item = getCards()[pos]

            presistenceService.Update(object : DatabaseUpdate(){
                override fun Update() {
                    item.votedBad = true
                }
            })
            analyticsService.OnVotedBad(id = item.id, info = item.info)

    }

    fun ItemSeen(pos: Int)
    {
            val item = getCards()[pos]

            presistenceService.Update(object : DatabaseUpdate(){
                override fun Update() {
                    item.votedBad = true
                }
            })
    }

    fun getTestData(contentType : String) : List<IHaveNeverCardModel>
    {
        val items = mutableListOf<IHaveNeverCardModel>();
        items.add(IHaveNeverCardModel(id = "1", info = "Had Sex on a Boat",votedBad = false, seen = false,cardType = contentType!!))
        items.add(IHaveNeverCardModel(id = "2", info = "Fallen down a hill drunk",votedBad = false, seen = false,cardType = contentType!!))
        return items;
    }

}