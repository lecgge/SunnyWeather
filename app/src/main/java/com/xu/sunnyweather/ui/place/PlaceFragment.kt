package com.xu.sunnyweather.ui.place

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xu.sunnyweather.R
import com.xu.sunnyweather.databinding.FragmentPlaceBinding
import com.xu.sunnyweather.ui.weather.WeatherActivity

class PlaceFragment : Fragment() {
    private lateinit var binding: FragmentPlaceBinding
    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaceBinding.bind(inflater.inflate(R.layout.fragment_place, container, false))
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (viewModel.isPlaceSaved()) {
            val place = viewModel.getSavedPlace()
            val intent = Intent(context,WeatherActivity::class.java).apply {
                putExtra("location_lng", place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }

        //设置layoutManager
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
        //设置Adapter
        val adapter = PlaceAdapter(this,viewModel.placeList)
        binding.recyclerView.adapter = adapter
        //编辑框监听事件
        binding.searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            //当搜索框中的内容发生编号，将内容传递给ViewModel
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                //当编辑框中为空时，将RecyclerView隐藏，显示背景图片
                binding.recyclerView.visibility = View.GONE
                binding.bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }

        //对viewModel中的placeLiveData进行观察，当数据发生变化就回调Observer接口
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->
            val places = result.getOrNull()
            //对回调数据进行判断，当数据不为空时，更新数据
            if (places != null) {
                binding.recyclerView.visibility = View.VISIBLE
                binding.bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            } else {
                //当数据为空时，弹出一个Toast提示并抛出异常
                Toast.makeText(activity,"未能查询到任何地点",Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }


}