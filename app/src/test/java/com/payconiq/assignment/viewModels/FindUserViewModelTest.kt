package com.payconiq.assignment.viewModels

import com.payconiq.assignment.network.ResultWrapper
import com.payconiq.assignment.network.model.FindUserResponse
import com.payconiq.assignment.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FindUserViewModelTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testFindUsersApiSuccess() {
        runBlocking {
            val actualResult = ResultWrapper.Success(FindUserResponse(arrayListOf()))
            doReturn(actualResult)
                .`when`(userRepository)
                .findUsers("")
            val result = userRepository.findUsers("")
            Assert.assertEquals(actualResult, result)
            Assert.assertNotNull(result)
        }
    }
}