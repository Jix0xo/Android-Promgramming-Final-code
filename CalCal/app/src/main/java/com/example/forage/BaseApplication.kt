package com.example.forage

import android.app.Application
import com.example.forage.data.ForageDatabase

class BaseApplication : Application() {
    // ForageDatabase 인스턴스를 지연 초기화하여 애플리케이션 전체에서 공유합니다.
    val database: ForageDatabase by lazy {
        ForageDatabase.getDatabase(this)
    }
}
