package com.aleksey_bakhtin.coursesapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.aleksey_bakhtin.coursesapp.R
import com.aleksey_bakhtin.coursesapp.adapters.FavouriteAdapter
import com.aleksey_bakhtin.coursesapp.databinding.FragmentFavouritesBinding
import com.aleksey_bakhtin.coursesapp.fragments.viewmodels.FavouritesViewModel
import com.aleksey_bakhtin.coursesapp.utils.getStateScreen
import com.aleksey_bakhtin.domain.Result
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FavouritesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.stateScreen.collect { result ->

                    getStateScreen().setStateScreen(result)

                    when(result) {
                        is Result.Success<*> -> {

                            val pair = result.data as Pair<*,*>

                            when(pair.first as String){
                                FavouritesViewModel.TYPE_GET_FAVOURITES -> {
                                    viewModel.setInstallUi()
                                    installListFavourites()
                                }
                                FavouritesViewModel.TYPE_REMOVE_FAVOURITE -> {}
                            }

                        }
                        is Result.Error -> {}
                        is Result.Loading -> {}
                    }

                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getFavourites()

        installListFavourites()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.setInstallUi()
    }

    private fun installListFavourites() = viewModel.installListFavourites {
        with(binding){
            val mutableListFavourites = viewModel.listFavourites.toMutableList()

            rvFavourites.adapter = FavouriteAdapter(
                mutableListFavourites = mutableListFavourites,
                iconEnable = ContextCompat.getDrawable(requireContext(),R.drawable.ic_bookmark_enable)!!
            ) { course, listFavourite ->
                viewModel.listFavourites = listFavourite
                viewModel.removeFavourite(course = course)

            }
            rvFavourites.layoutManager = LinearLayoutManager(requireContext())
        }
    }

}