package com.example.moviesearch.ui


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.example.moviesearch.R
import com.example.moviesearch.databinding.FragmentMainBinding
import com.example.moviesearch.utils.hideProgress
import com.example.moviesearch.utils.showProgress
import com.example.moviesearch.viewmodel.MainViewModel
import com.example.moviesearch.viewmodel.MainViewModelFactory
import com.squareup.picasso.Picasso
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() , KodeinAware {

    lateinit var binding: FragmentMainBinding
    override val kodeinContext = kcontext<Fragment>(this)
    override val kodein by kodein()

    private val factory : MainViewModelFactory by instance()
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(activity!!, factory).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate<FragmentMainBinding>(inflater, R.layout.fragment_main, container, false)
        binding.viewmodel = viewModel
        viewModel.getImageData()
        binding.progressBar.showProgress()

        binding.calander.setOnClickListener {
            showCalenderDialog()
        }

        binding.zoom.setOnClickListener {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigate(R.id.detailsFragment, null)
        }
        return binding.root
    }

    private fun showCalenderDialog() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(activity!!, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            var month = monthOfYear + 1
            val date : String = "$year-$month-$dayOfMonth"
            viewModel.getImageData(date)
            binding.progressBar.showProgress()
            binding.constraintLayout.visibility = View.GONE
        }, year, month, day)

        dpd.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getImageDataLivedata().observe(viewLifecycleOwner, Observer {
            binding.progressBar.hideProgress()
            binding.constraintLayout.visibility = View.VISIBLE
            if (it != null) {
                binding.title.text = it.title
                binding.description.text = it.explanation

                if(it.media_type == "image") {
                    binding.zoom.setImageDrawable(resources.getDrawable(R.drawable.ic_zoom_in_black_24dp))
                    Glide
                        .with(this)
                        .load(it.hdurl)
                        .centerCrop()
                        .placeholder(R.drawable.image_2)
                        .into(binding.image)
                } else {
                    binding.zoom.setImageDrawable(resources.getDrawable(R.drawable.ic_play_circle_outline_black_24dp))
                    val option = RequestOptions().frame(5000)
                    Glide
                        .with(this)
                        .asBitmap()
                        .load(it.hdurl)
                        .apply(option)
                        .centerCrop()
                        .placeholder(R.drawable.image_2)
                        .into(binding.image)
                }
            }
        })
    }

}
