package com.brighton.nicemovie.presentation.features.photo_more

import androidx.fragment.app.viewModels
import com.brighton.nicemovie.databinding.FragmentPhotoMoreBinding
import com.brighton.nicemovie.presentation.features.photo_more.PhotoMoreViewModel
import com.syauqi.watcheez.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoMoreFragment : BaseFragment<FragmentPhotoMoreBinding>(
    FragmentPhotoMoreBinding::inflate
) {
    private val viewModel: PhotoMoreViewModel by viewModels()
    override fun observeViewModel() {
        super.observeViewModel()
    }
}