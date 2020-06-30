package com.example.moviesearch.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

import com.example.moviesearch.R
import com.example.moviesearch.databinding.FragmentDetailsBinding
import com.example.moviesearch.viewmodel.MainViewModel
import com.example.moviesearch.viewmodel.MainViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

/**
 * A simple [Fragment] subclass.
 */
class DetailsFragment : Fragment(), KodeinAware {

    lateinit var binding: FragmentDetailsBinding
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
        binding = DataBindingUtil.inflate<FragmentDetailsBinding>(inflater, R.layout.fragment_details, container, false)
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getImageDataLivedata().observe(viewLifecycleOwner, Observer {
            if(it.media_type == "image") {
                binding.imageview.visibility = View.VISIBLE
                binding.videoview.visibility = View.GONE
                Glide
                    .with(this)
                    .load(it.hdurl)
                    .centerCrop()
                    .placeholder(R.drawable.image_2)
                    .into(binding.imageview)
            } else {
                binding.imageview.visibility = View.GONE
                binding.videoview.visibility = View.VISIBLE
                binding.videoview.setVideoPath(it.hdurl)
                binding.videoview.start()
            }
        })
    }


}
