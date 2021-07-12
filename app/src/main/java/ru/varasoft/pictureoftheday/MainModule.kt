package ru.varasoft.pictureoftheday

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.varasoft.pictureoftheday.model.EarthModel
import ru.varasoft.pictureoftheday.model.EarthModelImpl
import ru.varasoft.pictureoftheday.model.RetrofitImpl
import ru.varasoft.pictureoftheday.model.pod.*
import javax.inject.Singleton

@Module
class MainModule {


    @Singleton
    @Provides
    fun database(app: Application): Database = Room.databaseBuilder(
        app, Database::class.java,
        Database.DB_NAME
    ).build()

    @Singleton
    @Provides
    fun retrofitImpl(): RetrofitImpl = RetrofitImpl()

    @Singleton
    @Provides
    fun earthModel(): EarthModel = EarthModelImpl()

    @Singleton
    @Provides
    fun podCache(database: Database): IPODCache = roomPODCache(database)

    @Singleton
    @Provides
    fun networkStatus(app: App): INetworkStatus = AndroidNetworkStatus(app)

    @Singleton
    @Provides
    fun roomPODCache(database: Database): RoomPODCache = RoomPODCache(database)

    @Singleton
    @Provides
    fun nasaPODRepo(
        retrofitImpl: RetrofitImpl,
        podCache: RoomPODCache,
        context: Context
    ): INasaPODRepo = NasaPODRepo(retrofitImpl, podCache, context)

}
