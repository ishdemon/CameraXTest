package com.ishdemon.camerascannertest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ishdemon.camerascannertest.common.DataState
import com.ishdemon.camerascannertest.data.repository.PhotosRepository
import com.ishdemon.camerascannertest.testUtils.MainDispatcherRule
import com.ishdemon.camerascannertest.testUtils.RandomDataUtil
import com.ishdemon.camerascannertest.testUtils.TestAlbumDataUtil
import com.ishdemon.camerascannertest.testUtils.TestImageDataUtil
import com.ishdemon.camerascannertest.testUtils.returnOrCrash
import com.ishdemon.camerascannertest.testUtils.withoutCrash
import com.ishdemon.camerascannertest.ui.viewmodel.PhotosViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PhotosViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @MockK
    private lateinit var repository: PhotosRepository

    private lateinit var subject: PhotosViewModel


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        subject = PhotosViewModel(
            repository,
            TestCoroutineDispatcher()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `album flow emits Success when getAlbums succeeds`() = runTest {
        val data = listOf(TestAlbumDataUtil.random().toEntity())
        coEvery { repository.getAlbums() } returnOrCrash flowOf(data).withoutCrash

        subject.getAlbums()
        advanceUntilIdle()

        val albumState = subject.albumState.value
        Assert.assertTrue(albumState is DataState.Success)


        val actualValue = (albumState as DataState.Success).data
        val actualEntity = actualValue.map { it.toEntity() }
        Assert.assertEquals(data, actualEntity)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `images flow emits Success when getImages succeeds`() = runTest {
        val albumId = RandomDataUtil.randomString()

        val data = listOf(TestImageDataUtil.random().toEntity())

        coEvery { repository.getImages(albumId) } returnOrCrash flowOf(data).withoutCrash

        subject.getImages(albumId)
        advanceUntilIdle()

        val imagesState = subject.imagesState.value
        Assert.assertTrue(imagesState is DataState.Success)

        val actualValue = (imagesState as DataState.Success).data
        val actualEntity = actualValue.map { it.toEntity() }
        Assert.assertEquals(data, actualEntity)

    }





}