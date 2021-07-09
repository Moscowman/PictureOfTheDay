package ru.varasoft.pictureoftheday.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.load
import com.github.terrakok.cicerone.Router
import kotlinx.android.synthetic.main.fragment_mars.*
import moxy.ktx.moxyPresenter
import ru.varasoft.pictureoftheday.R
import ru.varasoft.pictureoftheday.model.RetrofitImpl
import ru.varasoft.pictureoftheday.model.mars.MarsPhotoData
import ru.varasoft.pictureoftheday.presenter.MarsPhotoPresenter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MarsFragment : AbsFragment(R.layout.fragment_mars), MarsView, BackButtonListener {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var retrofitImpl: RetrofitImpl

    override fun displayPicture(url: String) {
        showPicture(url)
    }

    private val presenter: MarsPhotoPresenter by moxyPresenter {
        MarsPhotoPresenter(router, retrofitImpl)
    }

    override fun backPressed() = presenter.backPressed()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mars, container, false)
    }

    private fun getDateRelativeToToday(offset: Int): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, offset);
        return sdf.format(calendar.getTime())
    }

    private fun renderData(data: MarsPhotoData) {
        when (data) {
            is MarsPhotoData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.photos[0].imgSrc
                if (url.isNullOrEmpty()) {
                    //Отобразите ошибку
                    //showError("Сообщение, что ссылка пустая")
                } else {
                    //Отобразите фото
                    showPicture(url)
                    //Coil в работе: достаточно вызвать у нашего ImageView
                    //нужную extension-функцию и передать ссылку и заглушки для placeholder


                    showPicture(url)
                }
            }
            is MarsPhotoData.Loading -> {
                //Отобразите загрузку
                //showLoading()
            }
            is MarsPhotoData.Error -> {
                toast(data.error.message)
            }
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }


    private fun showPicture(
        url: String?,
    ) {
        mars_image_view.load(url) {
            lifecycle(this@MarsFragment)
            error(R.drawable.ic_load_error_vector)
            placeholder(R.drawable.ic_no_photo_vector)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MarsFragment().apply {
            }
    }
}
