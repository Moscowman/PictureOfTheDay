package geekbarains.material.ui.api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ru.varasoft.pictureoftheday.R
import ru.varasoft.pictureoftheday.model.MarsPhotoData
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
                val a = 4
            })
    }

    private fun getDateRelativeToToday(offset: Int): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, offset);
        return sdf.format(calendar.getTime())
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MarsFragment().apply {
            }
    }
}
