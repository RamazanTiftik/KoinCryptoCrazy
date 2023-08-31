package com.ramazantiftik.koincrptocrazy.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.ramazantiftik.koincrptocrazy.R
import com.ramazantiftik.koincrptocrazy.adapter.RecyclerAdapter
import com.ramazantiftik.koincrptocrazy.databinding.FragmentCryptoListBinding
import com.ramazantiftik.koincrptocrazy.model.Crypto
import com.ramazantiftik.koincrptocrazy.service.RetrofitAPI
import com.ramazantiftik.koincrptocrazy.util.Constans.BASE_URL
import com.ramazantiftik.koincrptocrazy.viewmodel.CryptoViewModel
import kotlinx.coroutines.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class CryptoListFragment : Fragment(), RecyclerAdapter.Listener {

    //viewBinding
    private var _binding: FragmentCryptoListBinding ?= null
    private val binding get() = _binding!!

    //adapter
    private lateinit var adapter: RecyclerAdapter


    /* -> app içerisinde dependency injection yaparken bu şekilde çekiliyor, constructor inject yapılmak istenmezse
    private val api = get<RetrofitAPI>()
    private val apilazy by inject<RetrofitAPI>() -> lazy -> kullanılana kadar instance olmaz
     */

    private val viewModel by viewModel<CryptoViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //viewBinding
        _binding= FragmentCryptoListBinding.inflate(inflater,container,false)
        val view=binding.root

        //adapter
        val layoutManager: RecyclerView.LayoutManager= LinearLayoutManager(context)
        binding.recyclerView.layoutManager=layoutManager

        //viewModel
        //viewModel=ViewModelProvider(this).get(CryptoViewModel::class.java)
        viewModel.getDataFromAPI()

        observeLiveData()

        // Inflate the layout for this fragment
        return view
    }

    private fun observeLiveData(){

        viewModel.cryptoList.observe(viewLifecycleOwner){cryptos ->
            cryptos?.let {

                binding.recyclerView.visibility=View.VISIBLE
                adapter= RecyclerAdapter(ArrayList(cryptos.data) ?: arrayListOf(),this@CryptoListFragment)
                binding.recyclerView.adapter=adapter

            }
        }

        viewModel.cryptoError.observe(viewLifecycleOwner){error ->
            error?.let {
                if (it.data == true){
                    binding.cryptoErrorText.visibility=View.VISIBLE
                    binding.recyclerView.visibility=View.GONE
                    binding.cryptoProgressBar.visibility=View.GONE
                } else {
                    binding.cryptoErrorText.visibility=View.GONE
                }
            }
        }

        viewModel.cryptoLoading.observe(viewLifecycleOwner){loading ->
            loading?.let {
                if (loading.data == true){
                    binding.cryptoErrorText.visibility=View.GONE
                    binding.recyclerView.visibility=View.GONE
                    binding.cryptoProgressBar.visibility=View.VISIBLE
                } else{
                    binding.cryptoProgressBar.visibility=View.GONE
                }
            }
        }

    }


    override fun onItemClick(crypto: Crypto) {
        Toast.makeText(context,"Clicked on: ${crypto.currency}",Toast.LENGTH_LONG).show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}