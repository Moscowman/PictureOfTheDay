package ru.varasoft.pictureoftheday

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class AppModule(val app: Application) {
    @Provides
    fun app(): Application {
        return app
    }
}
