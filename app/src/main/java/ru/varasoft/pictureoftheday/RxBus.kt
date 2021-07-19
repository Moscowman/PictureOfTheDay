package ru.varasoft.pictureoftheday

import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import kotlin.reflect.KClass

class RxBus {

    interface Event

    private val bus: Subject<Event> = PublishSubject.create()

    fun publish(event: Event) = bus.onNext(event)

    fun subscribe(block: (event: Event) -> Unit): Disposable =
        bus.subscribe({ event -> block(event) }, { /* игнорируем */ })

    fun <T : Event> subscribe(clazz: KClass<T>, block: (event: T) -> Unit): Disposable =
        bus.ofType(clazz.java)
            .subscribe({ event -> block(event) }, { /* игнорируем */ })

}