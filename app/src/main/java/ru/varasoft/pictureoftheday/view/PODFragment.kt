package ru.varasoft.pictureoftheday.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.load
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_main.*
import ru.varasoft.pictureoftheday.R
import ru.varasoft.pictureoftheday.model.PODServerResponseData
import ru.varasoft.pictureoftheday.model.PictureOfTheDayData
import ru.varasoft.pictureoftheday.viewmodel.PictureOfTheDayViewModel
import java.text.SimpleDateFormat
import java.util.*

class PODFragment : Fragment() {

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProviders.of(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    private fun getDateRelativeToToday(offset: Int): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, offset);
        return sdf.format(calendar.getTime())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }

        chip_group.setOnCheckedChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position)?.let {
                when (it.id) {
                    R.id.two_days_ago_chip -> {
                        val date: String = getDateRelativeToToday(-2)
                        viewModel.getData(date).observe(
                            viewLifecycleOwner,
                            Observer<PictureOfTheDayData> { renderData(it) })
                    }
                    R.id.yesterday_chip -> {
                        val date: String = getDateRelativeToToday(-1)
                        viewModel.getData(date).observe(
                            viewLifecycleOwner,
                            Observer<PictureOfTheDayData> { renderData(it) })
                    }
                    R.id.today_chip -> {
                        val date: String = getDateRelativeToToday(0)
                        viewModel.getData(date).observe(
                            viewLifecycleOwner,
                            Observer<PictureOfTheDayData> { renderData(it) })
                    }
                }
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData(getDateRelativeToToday(0))
            .observe(viewLifecycleOwner, Observer<PictureOfTheDayData> { renderData(it) })
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

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = if (serverResponseData.mediaType == "video")
                    serverResponseData.thumbnailUrl
                else serverResponseData.url
                if (url.isNullOrEmpty()) {
                    //Отобразите ошибку
                    //showError("Сообщение, что ссылка пустая")
                } else {
                    //Отобразите фото
                    //showSuccess()
                    //Coil в работе: достаточно вызвать у нашего ImageView
                    //нужную extension-функцию и передать ссылку и заглушки для placeholder


                    showPicture(url, serverResponseData)
                    bottom_sheet_description.text = serverResponseData.explanation
                    bottom_sheet_description_header.text = serverResponseData.title
                }
            }
            is PictureOfTheDayData.Loading -> {
                //Отобразите загрузку
                //showLoading()
            }
            is PictureOfTheDayData.Error -> {
                toast(data.error.message)
            }
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
                context!!.startActivity(webIntent)
            })
        } else {
            image_view.setOnClickListener(null)
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PODFragment().apply {
            }
    }
}