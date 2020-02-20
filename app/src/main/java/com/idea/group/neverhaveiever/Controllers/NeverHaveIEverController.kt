package com.idea.group.neverhaveiever.Controllers

import com.idea.group.neverhaveiever.Models.APIModels.IHaveNeverCardAPIModel
import com.idea.group.neverhaveiever.Services.AnalyticsService
import com.idea.group.neverhaveiever.Views.NeverHaveIEverFragment
import mosquito.digital.template.mdpersistence.DatabaseUpdate
import mosquito.digital.template.mdpersistence.PersistenceService

class NeverHaveIEverController(presistenceService : PersistenceService, analyticsService: AnalyticsService, cardType : String)
{
    var presistenceService : PersistenceService
    var analyticsService: AnalyticsService;
    var cardType : String
    init {
        this.presistenceService = presistenceService
        this.cardType = cardType
        this.analyticsService = analyticsService;
    }


    private fun getCards() : List<IHaveNeverCardAPIModel>
    {
        val list = mutableListOf<IHaveNeverCardAPIModel>()
        list.add(IHaveNeverCardAPIModel(id = "1", info = "Had Sex on a Boat", seen = false,votedBad = false, cardType = ""))
        list.add(IHaveNeverCardAPIModel(id = "2", info = "Fallen down a hill drunk", seen = false,votedBad = false, cardType = ""))

        list.removeAll { iHaveNeverCardAPIModel: IHaveNeverCardAPIModel -> list.any {
            iHaveNeverCardAPIModel.votedBad
        } }

        list.random()

        val listOfType = mutableListOf<IHaveNeverCardAPIModel>()

        for (item in list.toList())
        {
            if(item.cardType == cardType)
            {
                list.remove(item)
                listOfType.add(item)
            }
        }

        val listOfSeen = mutableListOf<IHaveNeverCardAPIModel>()

        for (item in list.toList())
        {
            if(item.seen)
            {
                list.remove(item)
                listOfSeen.add(item)
            }
        }
        list.addAll(list.lastIndex,listOfSeen)
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
}