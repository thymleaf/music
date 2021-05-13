package com.thymleaf.music.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.thymleaf.music.R
import com.thymleaf.music.adapter.TrackAdapter
import com.thymleaf.music.base.BaseSimpleFragment
import com.thymleaf.music.databinding.FragmentTrackListBinding
import com.thymleaf.music.model.service.MediaApi
import com.thymleaf.music.util.RecyclerViewUtil

class TrackListFragment : BaseSimpleFragment() {

    private lateinit var binding: FragmentTrackListBinding

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: TrackAdapter


    override fun setViewBinding(): ViewBinding {
        binding = FragmentTrackListBinding.inflate(layoutInflater)
        return binding
    }

    override fun initFragment(savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        adapter = TrackAdapter(requireContext(), R.layout.item_track_container)
//        adapter.addFooterView(LayoutInflater.from(requireContext()).inflate(R.layout.footer_recycler_view, null, false))
        adapter.setOnItemChildClickListener{ adapter, view, positon ->

        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        RecyclerViewUtil.setItemDividerDuration(requireContext(), recyclerView)


        loadData()
    }

    private fun loadData()
    {
        loadRepository(repository.obtainRetrofitService(MediaApi::class.java).getTracks(30)){
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle?) =
                TrackListFragment().apply {
                    arguments = Bundle().apply {
                        arguments = bundle
                    }
                }
    }


}