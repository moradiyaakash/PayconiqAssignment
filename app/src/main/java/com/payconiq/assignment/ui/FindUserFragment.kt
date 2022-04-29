package com.payconiq.assignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.payconiq.assignment.R
import com.payconiq.assignment.adapters.UserItemAdapter
import com.payconiq.assignment.databinding.FragmentFindUserBinding
import com.payconiq.assignment.network.ResultWrapper
import com.payconiq.assignment.network.model.FindUserResponse
import com.payconiq.assignment.utils.afterTextChanged
import com.payconiq.assignment.utils.hideKeyboard
import com.payconiq.assignment.utils.show
import com.payconiq.assignment.utils.showSnackBar
import com.payconiq.assignment.viewModels.FindUserViewModel

class FindUserFragment : Fragment() {

    private lateinit var findUserBinding: FragmentFindUserBinding
    private lateinit var findUserViewModel: FindUserViewModel
    private var userItemAdapter: UserItemAdapter? = null
    private var currentView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findUserViewModel = ViewModelProvider(this)[FindUserViewModel::class.java]
        setLiveDataObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (currentView == null) {
            findUserBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_find_user, container, false)
            initializeScreenVariables()
            currentView = findUserBinding.root
        }
        return currentView
    }

    private fun initializeScreenVariables() {

        userItemAdapter = UserItemAdapter { user ->

            val action = FindUserFragmentDirections.actionFindUserToUserProfile(user.login)
            findNavController().navigate(action)
        }
        findUserBinding.rvUsers.adapter = userItemAdapter

        findUserBinding.edtSearch.afterTextChanged { text ->

            if (text.isNotEmpty()) {
                findUserViewModel.callFindUserApi(text)
            } else {
                resetSearch(false)
            }
        }

        findUserBinding.searchField.setEndIconOnClickListener {

            resetSearch(true)
        }
    }

    private fun setLiveDataObservers() {

        findUserViewModel.searchResults.observe(this, Observer { state ->

            if (state == null) {
                return@Observer
            }

            when (state) {

                is ResultWrapper.Loading -> {

                    findUserBinding.progress.show(true)
                }
                is ResultWrapper.GenericError -> {

                    findUserBinding.progress.show(false)

                    if (state.code == 422) {
                        userItemAdapter?.clear()
                    }

                    state.message?.let {
                        if (it.isNotEmpty()) {
                            showSnackBar(it)
                        }
                    }
                }
                is ResultWrapper.Success<FindUserResponse?> -> {

                    findUserBinding.progress.show(false)

                    findUserBinding.edtSearch.hideKeyboard()

                    val response = state.response as FindUserResponse
                    userItemAdapter?.addItems(response.users)
                }
                is ResultWrapper.NetworkError -> {

                    findUserBinding.progress.show(false)
                    showSnackBar(getString(R.string.warning_no_internet_connection))
                }
            }
        })
    }

    private fun resetSearch(clear: Boolean) {
        findUserBinding.edtSearch.run {
            hideKeyboard()
            if (clear) {
                text = null
            }
        }
        findUserViewModel.cancelJob()
        userItemAdapter?.clear()
    }
}