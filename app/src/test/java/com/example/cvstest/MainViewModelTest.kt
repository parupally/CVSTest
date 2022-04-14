package com.example.cvstest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.cvstest.data.RetrofitService
import com.example.cvstest.model.ImageResponse
import com.example.cvstest.model.Item
import com.example.cvstest.model.Media
import com.example.cvstest.viewmodel.MainViewModel
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    lateinit var mainViewModel: MainViewModel

    @Mock
    private lateinit var retrofitService: RetrofitService

    private var testCoroutineScope = TestCoroutineScope()


    @Mock
    private lateinit var imageResponseObserver: Observer<ImageResponse>


    @Before
    fun doSomeSetup() {
        mainViewModel = MainViewModel()
        // do something if required
    }

//    @Test
//    fun `Loading state works`() = runBlocking {
//        var imageResponse: ImageResponse
//        `when`(retrofitService.getAllImages()).thenReturn(imageResponse)
//    }

    @Test
    fun testEmptyResponse_Notnull() {
        testCoroutineScope.runBlockingTest {
            doReturn(emptyList<ImageResponse>())
                .`when`(retrofitService)
                .getAllImages()
            mainViewModel.imageList.observeForever(imageResponseObserver)
            assertNotNull(mainViewModel.imageList)
        }
    }

    @Test
    fun testEmptyResponse_ShowNullItem() {
        testCoroutineScope.runBlockingTest {
            doReturn(emptyList<ImageResponse>())
                .`when`(retrofitService)
                .getAllImages()
            mainViewModel.imageList.observeForever(imageResponseObserver)
            assertNull(mainViewModel.imageList.value)
        }
    }

    @Test
    fun testResponse_ShowNotNullItem() {

        var media = Media("TestMedia")
        var item = Item(
            "Test", "Test", media, "Test", "TestDecription",
            "Published", "Author", "AuthordID", "Tags"
        )
        var imageResponse = ImageResponse(
            "Title", "link", "description", "modified",
            "gener", listOf(item)
        )
        testCoroutineScope.runBlockingTest {
            doReturn(imageResponse)
                .`when`(retrofitService)
                .getAllImages()
            mainViewModel.imageList.observeForever(imageResponseObserver)
            assertNotNull(mainViewModel.imageList)
        }
    }

    @Test
    fun testFetchResponseData() = testCoroutineScope.runBlockingTest {
        assertNotNull(mainViewModel.imageList)
    }
}