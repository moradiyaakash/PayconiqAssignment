package com.payconiq.assignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.payconiq.assignment.R
import com.payconiq.assignment.databinding.FragmentUserProfileBinding
import com.payconiq.assignment.network.ResultWrapper
import com.payconiq.assignment.network.model.UserDetailsResponse
import com.payconiq.assignment.utils.show
import com.payconiq.assignment.utils.showSnackBar
import com.payconiq.assignment.viewModels.UserProfileViewModel

class UserProfileFragment : Fragment() {

    private lateinit var userProfileBinding: FragmentUserProfileBinding
    private lateinit var userProfileViewModel: UserProfileViewModel
    private val args: UserProfileFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userProfileViewModel = ViewModelProvider(this)[UserProfileViewModel::class.java]
        setLiveDataObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false)
        return userProfileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userProfileBinding.btnBack.setOnClickListener {

            requireActivity().onBackPressed()
        }

        userProfileViewModel.userName = args.userName
        userProfileViewModel.callGetUserDetailsApi()
    }

    private fun setLiveDataObservers() {
        userProfileViewModel.userDetails.observe(this, Observer { state ->

            if (state == null) {
                return@Observer
            }

            when (state) {

                is ResultWrapper.Loading -> {

                    userProfileBinding.progress.show(true)
                }
                is ResultWrapper.GenericError -> {

                    userProfileBinding.progress.show(false)

                    state.message?.let {
                        if (it.isNotEmpty()) {
                            showSnackBar(it)
                        }
                    }
                }
                is ResultWrapper.Success<UserDetailsResponse?> -> {

                    userProfileBinding.progress.show(false)

                    val response = state.response as UserDetailsResponse
                    userProfileBinding.user = response
                    userProfileBinding.executePendingBindings()
                    userProfileBinding.viewGroups.show(true)
                }
                is ResultWrapper.NetworkError -> {

                    userProfileBinding.progress.show(false)
                    showSnackBar(getString(R.string.warning_no_internet_connection))
                }
            }
        })
    }
}