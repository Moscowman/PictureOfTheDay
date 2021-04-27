package ru.varasoft.pictureoftheday.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_main.*
import ru.varasoft.pictureoftheday.MainActivity
import ru.varasoft.pictureoftheday.R
import ru.varasoft.pictureoftheday.model.PODServerResponseData
import ru.varasoft.pictureoftheday.model.PictureOfTheDayData
import ru.varasoft.pictureoftheday.viewmodel.PictureOfTheDayViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProviders.of(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
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
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val calendar = Calendar.getInstance()
                when (it.id) {
                    R.id.two_days_ago_chip -> {
                        calendar.add(Calendar.DAY_OF_YEAR, -2);
                        val date: String = sdf.format(calendar.getTime())
                        viewModel.getData(date).observe(
                            viewLifecycleOwner,
                            Observer<PictureOfTheDayData> { renderData(it) })
                    }
                    R.id.yesterday_chip -> {
                        calendar.add(Calendar.DAY_OF_YEAR, -1);
                        val date: String = sdf.format(calendar.getTime())
                        viewModel.getData(date).observe(
                            viewLifecycleOwner,
                            Observer<PictureOfTheDayData> { renderData(it) })
                    }
                    R.id.today_chip -> {
                        val date: String = sdf.format(calendar.getTime())
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
        viewModel.getData("2021-04-23")
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
            lifecycle(this@MainFragment)
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
            MainFragment().apply {
            }
    }
}