package com.example.forage.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.forage.data.ForageableDao
import com.example.forage.model.Forageable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// ForageableViewModel은 데이터베이스와 상호작용하여 UI에 필요한 데이터를 제공하는 클래스입니다.
class ForageableViewModel(
    private val forageableDao: ForageableDao
) : ViewModel() {
    val forageables = forageableDao.getForageables().asLiveData()

    // ID에 해당하는 Forageable을 가져오는 함수입니다.
    fun getForageable(id: Long) = forageableDao.getForageable(id).asLiveData()

    // 새로운 Forageable을 추가하는 함수입니다.
    fun addForageable(
        date: String,
        morning: String,
        lunch: String,
        dinner: String,
        morning_kcal: String,
        lunch_kcal: String,
        dinner_kcal: String,
        kcal_total: String
    ) {
        val forageable = Forageable(
            morning = morning,
            lunch = lunch,
            date = date,
            dinner = dinner,
            morning_kcal = morning_kcal.toInt(),
            lunch_kcal = lunch_kcal.toInt(),
            dinner_kcal = dinner_kcal.toInt(),
            kcal_total = kcal_total.toInt()
        )

        val forageableNew = forageable.copy(kcal_total = forageable.morning_kcal + forageable.lunch_kcal + forageable.dinner_kcal)

        viewModelScope.launch(Dispatchers.IO) {
            forageableDao.insert(forageableNew)
        }
    }

    // Forageable을 업데이트하는 함수입니다.
    fun updateForageable(
        id: Long,
        date: String,
        morning: String,
        lunch: String,
        dinner: String,
        morning_kcal: String,
        lunch_kcal: String,
        dinner_kcal: String,
        kcal_total: String
    ) {
        val forageable = Forageable(
            id = id,
            date = date,
            morning = morning,
            lunch = lunch,
            dinner = dinner,
            morning_kcal = morning_kcal.toInt(),
            lunch_kcal = lunch_kcal.toInt(),
            dinner_kcal = dinner_kcal.toInt(),
            kcal_total = kcal_total.toInt()
        )

        val forageableNew = forageable.copy(kcal_total = forageable.morning_kcal + forageable.lunch_kcal + forageable.dinner_kcal)

        viewModelScope.launch(Dispatchers.IO) {
            forageableDao.update(forageableNew)
        }
    }

    // Forageable을 삭제하는 함수입니다.
    fun deleteForageable(forageable: Forageable) {
        viewModelScope.launch(Dispatchers.IO) {
            forageableDao.delete(forageable)
        }
    }

    // 입력된 값들이 유효한지 확인하는 함수입니다.
    fun isValidEntry(date: String, morning: String, lunch: String, dinner: String,
                     morning_kcal: String, lunch_kcal: String, dinner_kcal: String): Boolean {
        return date.isNotBlank() && morning.isNotBlank() && lunch.isNotBlank() && dinner.isNotBlank()
                && morning_kcal.isNotBlank() && lunch_kcal.isNotBlank() && dinner_kcal.isNotBlank()
    }
}

// ForageableViewModel 인스턴스를 생성하기 위한 ViewModelProvider.Factory입니다.
class ForageableViewModelFactory(private val forageableDao: ForageableDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForageableViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ForageableViewModel(forageableDao) as T
        }
        throw IllegalArgumentException("Unexpected class: $modelClass")
    }
}
