package com.payconiq.assignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.payconiq.assignment.R
import com.payconiq.assignment.adapters.UserItemAdapter
import com.payconiq.assignment.databinding.FragmentFindUserBinding
import com.payconiq.assignment.network.ResultWrapper
import com.payconiq.assignment.network.model.FindUserResponse
import com.payconiq.assignment.utils.afterTextChanged
import com.payconiq.assignment.utils.hideKeyboard
import com.payconiq.assignment.utils.toast
import com.payconiq.assignment.viewmodel.UserViewModel

class FindUserFragment : Fragment() {

    private lateinit var findUserBinding: FragmentFindUserBinding
    private lateinit var userViewModel: UserViewModel
    private var userItemAdapter: UserItemAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        setLiveDataObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        findUserBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_find_user, container, false)
        return findUserBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userItemAdapter = UserItemAdapter {
        }
        findUserBinding.rvUsers.adapter = userItemAdapter

        findUserBinding.searchField.setEndIconOnClickListener {

            findUserBinding.edtSearch.hideKeyboard()
            findUserBinding.edtSearch.text = null
        }

        findUserBinding.edtSearch.afterTextChanged {

            userViewModel.callFindUserApi(it)
        }
    }

    private fun setLiveDataObservers() {

        userViewModel.searchResponse.observe(this, Observer { state ->

            if (state == null) {
                return@Observer
            }

            when (state) {

                is ResultWrapper.Loading -> {
                }
                is ResultWrapper.GenericError -> {

                    if (state.code == 422) {
                        userItemAdapter?.clear()
                    }
                }
                is ResultWrapper.Success<FindUserResponse?> -> {

                    findUserBinding.edtSearch.hideKeyboard()

                    val response = state.response as FindUserResponse
                    userItemAdapter?.addItems(response.users)
                }
                else -> requireActivity().toast("Error")
            }
        })
    }
}