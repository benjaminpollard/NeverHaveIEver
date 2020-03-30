package com.idea.group.neverhaveiever.Controllers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.idea.group.neverhaveiever.Models.APIModels.IHaveNeverCardModel
import com.idea.group.neverhaveiever.Models.APIModels.QuestionAPIModel
import com.idea.group.neverhaveiever.Services.AnalyticsService
import com.idea.group.neverhaveiever.Services.DatabaseUpdate
import com.idea.group.neverhaveiever.Services.PersistenceService
import com.idea.group.neverhaveiever.Services.QuestionApiService

class NeverHaveIEverController(val presistenceService : PersistenceService, val analyticsService: AnalyticsService, val questionService : QuestionApiService, val cardType : String) :
    ViewModel()
{

    val cards: MutableLiveData<List<IHaveNeverCardModel>> by lazy {
        MutableLiveData<List<IHaveNeverCardModel>>()
    }

    suspend fun getCards()
    {
        val cardsL = questionService.getQuestions()

        var oldList = presistenceService.GetItems(IHaveNeverCardModel::class.java)

        updateLocalCardList(cardsL, oldList,"CLEAN")
        updateLocalCardList(cardsL, oldList,"NAUGHTY")
        updateLocalCardList(cardsL, oldList,"EXPOSED")
        updateLocalCardList(cardsL, oldList,"COUPLES")

        val list = mutableListOf<IHaveNeverCardModel>()
        var b = presistenceService.GetItems(IHaveNeverCardModel::class.java).size;
        for (item in presistenceService.GetItems(IHaveNeverCardModel::class.java))
        {
            if (item.cardType == cardType)
            {
                list.add(item)
            }
        }
//        list.add(IHaveNeverCardModel(id = "1", info = "Had Sex on a Boat", seen = false,votedBad = false, cardType = ""))
//        list.add(IHaveNeverCardModel(id = "2", info = "Fallen down a hill drunk", seen = false,votedBad = false, cardType = ""))

        list.removeAll { iHaveNeverCardModel: IHaveNeverCardModel -> list.any {
            iHaveNeverCardModel.votedBad
        } }

        if(list.size == 0)
        {
            //will crash random on size 0 list
            return
        }


        val listOfSeen = mutableListOf<IHaveNeverCardModel>()

        for (item in list.toList())
        {
            if(item.votedBad)
            {
                list.remove(item)
            }
            else if(item.seen)
            {
                list.remove(item)
                listOfSeen.add(item)
            }

        }
        list.addAll(list.lastIndex - 1,listOfSeen)

        cards.postValue(list)
    }

    private fun updateLocalCardList(
        cards: QuestionAPIModel,
        oldList: MutableList<IHaveNeverCardModel>,
    cardType : String) {

        var localCards = cards.Clean

        when (cardType) {
            "NAUGHTY" -> localCards = cards.Naughty
            "COUPLES" -> localCards = cards.Couples
            "EXPOSED" -> localCards = cards.Exposed
        }

        for (cardText in localCards) {

            var alreadyAdded = false;
            for (oldCard in oldList) {
                if (oldCard.info == cardText && oldCard.cardType == cardType) {
                    alreadyAdded = true
                }
            }
            if (!alreadyAdded) {
                presistenceService.SaveItem(
                    IHaveNeverCardModel(
                        "",
                        cardText,
                        false,
                        false,
                        cardType
                    )
                )
            }
        }
    }

    private fun getLocalCards() : List<IHaveNeverCardModel>
    {

        val fullCardList = presistenceService.GetItems(IHaveNeverCardModel::class.java)
        val localList =  mutableListOf<IHaveNeverCardModel>();
        for (card in fullCardList)
        {
            if(card.cardType == cardType)
            localList.add(card)
        }
        return localList
    }

    fun ItemVotedBad(pos: Int)
    {
            val item = getLocalCards()[pos]

            presistenceService.Update(object : DatabaseUpdate(){
                override fun Update() {
                    item.votedBad = true
                }
            })
            analyticsService.OnVotedBad(id = item.id, info = item.info)

    }

    fun ItemSeen(pos: Int)
    {
            val item = getLocalCards()[pos]

            presistenceService.Update(object : DatabaseUpdate(){
                override fun Update() {
                    item.votedBad = true
                }
            })
    }

//    fun getTestData(contentType : String) : List<IHaveNeverCardModel>
//    {
//        val items = mutableListOf<IHaveNeverCardModel>();
//        items.add(IHaveNeverCardModel(id = "1", info = "Had Sex on a Boat",votedBad = false, seen = false,cardType = contentType!!))
//        items.add(IHaveNeverCardModel(id = "2", info = "Fallen down a hill drunk",votedBad = false, seen = false,cardType = contentType!!))
//        return items;
//    }

}