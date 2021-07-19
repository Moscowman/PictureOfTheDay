package ru.varasoft.pictureoftheday.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.*
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import coil.load
import com.github.terrakok.cicerone.Router
import com.google.android.material.chip.Chip
import dagger.android.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_pod_start.*
import moxy.ktx.moxyPresenter
import ru.gb.gb_popular_libs.data.network.NetworkStateRepository
import ru.varasoft.pictureoftheday.R
import ru.varasoft.pictureoftheday.model.RetrofitImpl
import ru.varasoft.pictureoftheday.model.pod.INasaPODRepo
import ru.varasoft.pictureoftheday.model.pod.IPODCache
import ru.varasoft.pictureoftheday.model.pod.PODServerResponseData
import ru.varasoft.pictureoftheday.model.pod.RoomPODCache
import ru.varasoft.pictureoftheday.presenter.PictureOfTheDayPresenter
import java.util.*
import javax.inject.Inject

class PODFragment : AbsFragment(R.layout.fragment_pod_start), PODView, BackButtonListener {
    companion object {
        fun newInstance() = PODFragment()
    }

    @Inject
    lateinit var retrofitImpl: RetrofitImpl

    @Inject
    lateinit var roomPODCache: IPODCache

    @Inject
    lateinit var nasaPODRepo: INasaPODRepo

    @Inject
    lateinit var networkStateRepository: NetworkStateRepository

    private val presenter: PictureOfTheDayPresenter by moxyPresenter {
        PictureOfTheDayPresenter(networkStateRepository, router, nasaPODRepo)
    }

    override fun backPressed() = presenter.backPressed()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val inflater = inflater.inflate(R.layout.fragment_pod_start, container, false)
        return inflater
    }

    override fun displayPicture(podData: PODServerResponseData) {
        renderData(podData)
    }

    private fun showComponents() {
        Handler().postDelayed({
            val constraintSet = ConstraintSet()
            constraintSet.clone(requireContext(), R.layout.fragment_pod_end_constraint)

            val transition = ChangeBounds()
            transition.interpolator = AnticipateOvershootInterpolator(1.0f)
            transition.duration = 2000

            TransitionManager.beginDelayedTransition(constraint_container, transition)
            constraintSet.applyTo(constraint_container)
        }, 1000)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chip_group.setOnCheckedChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position)?.let {
                when (it.id) {
                    R.id.two_days_ago_chip -> {
                        presenter.renderPuctureRelativeToToday(-2)
                    }
                    R.id.yesterday_chip -> {
                        presenter.renderPuctureRelativeToToday(-1)
                    }
                    R.id.today_chip -> {
                        presenter.renderPuctureRelativeToToday(0)
                    }
                }
            }
        }
        setHasOptionsMenu(true)
        showComponents()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> Toast.makeText(context, "Favourite", Toast.LENGTH_SHORT).show()
            R.id.app_bar_search -> Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show()
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
            R.id.app_bar_settings -> {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.add(R.id.container, SettingsFragment())?.addToBackStack(null)?.commit()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderData(serverResponseData: PODServerResponseData) {
        val url = if (serverResponseData.mediaType == "video")
            serverResponseData.thumbnailUrl
        else serverResponseData.url
        if (url.isNullOrEmpty()) {
            //Отобразите ошибку
            //showError("Сообщение, что ссылка пустая")
        } else {
            showPicture(url, serverResponseData)
            pod_description.text = serverResponseData.explanation
            pod_description_header.text = serverResponseData.title
        }
    }

    private fun showPicture(
        url: String?,
        serverResponseData: PODServerResponseData
    ) {
        image_view.load(url) {
            lifecycle(this@PODFragment)
            error(R.drawable.ic_load_error_vector)
            placeholder(R.drawable.ic_no_photo_vector)
        }
        if (serverResponseData.mediaType == "video") {
            image_view.setOnClickListener(View.OnClickListener {
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(serverResponseData.url)
                )
                requireContext().startActivity(webIntent)
            })
        } else {
            image_view.setOnClickListener(null)
        }
    }
}