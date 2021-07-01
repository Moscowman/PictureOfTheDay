package ru.varasoft.popularlibs

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import moxy.MvpPresenter
import ru.varasoft.pictureoftheday.view.PODFragment

class MainPresenter(val router: Router) : MvpPresenter<MainView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(FragmentScreen{ PODFragment.newInstance()})
    }

}