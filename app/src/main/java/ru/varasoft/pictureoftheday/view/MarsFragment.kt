package geekbarains.material.ui.api

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_mars.*
import kotlinx.android.synthetic.main.fragment_pod.*
import ru.varasoft.pictureoftheday.R
import ru.varasoft.pictureoftheday.model.mars.MarsPhotoArrayServerResponseData
import ru.varasoft.pictureoftheday.model.mars.MarsPhotoData
import ru.varasoft.pictureoftheday.viewmodel.MarsPhotoViewModel
import java.text.SimpleDateFormat
import java.util.*

class MarsFragment : Fragment() {

    private var offset: Int = 0

    private val viewModel: MarsPhotoViewModel by lazy {
        ViewModelProviders.of(this).get(MarsPhotoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mars, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData(getDateRelativeToToday(offset))
            .observe(viewLifecycleOwner, Observer<MarsPhotoData> {
                renderData(it)
            })
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
                    //showSuccess()
                    //Coil в работе: достаточно вызвать у нашего ImageView
                    //нужную extension-функцию и передать ссылку и заглушки для placeholder


                    showPicture(url, serverResponseData)
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
        serverResponseData: MarsPhotoArrayServerResponseData
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
