package com.aptoide_app

import com.aptoide_app.data.local.FullDetailAppDao
import com.aptoide_app.data.local.FullDetailAppEntity
import com.aptoide_app.data.mapper.toObject
import com.aptoide_app.data.remote.AptoideApi
import com.aptoide_app.data.remote.dto.AptitudeRetrieve
import com.aptoide_app.domain.AppsRepository
import com.aptoide_app.domain.AppsRepositoryImpl
import com.aptoide_app.domain.FullDetailApp
import com.aptoide_app.domain.TestApp
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import kotlinx.coroutines.runBlocking
import org.junit.Test
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    private val app1 = TestApp(
        name = "app1"
    )

    private val app2 = TestApp(
        name = "app2"
    )

    private val aptoideApi: AptoideApi = mockk()
    private val fullDetailAppDao: FullDetailAppDao = mockk()
    private val appsRepository = AppsRepositoryImpl(aptoideApi, fullDetailAppDao, Dispatchers.Unconfined)

    @Test
    fun `test getAppsFromDataBase when data available`() = runTest {
        val fullDetailAppDao = mockk<FullDetailAppDao>()
        val api = mockk<AptoideApi>()
        val expectedData = listOf(
            FullDetailAppEntity(
                added = "added",
                apkTags = listOf("tag1", "tag2"),
                downloads = 1000,
                graphic = "graphic",
                icon = "icon",
                id = 1,
                md5sum = "md5sum",
                modified = "modified",
                name = "name",
                packageX = "packageX",
                pdownloads = 2000,
                rating = 4.5,
                size = 5000L,
                storeId = 1,
                storeName = "storeName",
                updated = "updated",
                uptype = "uptype",
                vercode = 1,
                vername = "vername"
            )
        )
        coEvery { fullDetailAppDao.getFullDetailApp() } returns expectedData
        val repository = AppsRepositoryImpl(api, fullDetailAppDao, Dispatchers.Unconfined)
        val result = repository.getAppsFromDataBase().toList().flatten()
        coVerify { fullDetailAppDao.getFullDetailApp() }
        assertEquals(expectedData.toObject(), result)
    }

    @Test
    fun `get Full Details Apps Returns Empty List When Api Call Fails`() = runTest {
        val exception = Exception("API call failed")
        coEvery { aptoideApi.getListings() } throws exception

        val result = appsRepository.getFullDetailsApps()

        val actualData = result.getOrNull()?.toList()?.flatten()
        val expectedData = emptyList<FullDetailApp>()

        assertEquals(expectedData, actualData)
    }

    /*@Test
    fun `getApps Returns List When Api Call Succeeds`() = runTest() {
        val expectedData = listOf(
            FullDetailApp(
                added = "added",
                apkTags = listOf("tag1", "tag2"),
                downloads = 1000,
                graphic = "graphic",
                icon = "icon",
                id = 1,
                md5sum = "md5sum",
                modified = "modified",
                name = "name",
                packageX = "packageX",
                pdownloads = 2000,
                rating = 4.5,
                size = 5000L,
                storeId = 1,
                storeName = "storeName",
                updated = "updated",
                uptype = "uptype",
                vercode = 1,
                vername = "vername"
            )
        )

        // Create a mock Call object
        val call: Call<AptitudeRetrieve> = mockk()

        // Set up the mock Call to return a Response containing your expected data
        every { call.execute() } returns Response.success(AptitudeRetrieve(expectedData))

        // Set up aptoideApi.getListings() to return the mock Call
        every { aptoideApi.getListings() } returns call

        val result = appsRepository.getApps().toList().flatten()
        assertEquals(expectedData, result)
    }*/



    @Test
    fun `getApps Returns Empty List When Data Transformation Fails`() = runTest {
        val malformedData = "malformed data"

        // Create a mock AptitudeRetrieve object
        val aptitudeRetrieve: AptitudeRetrieve = mockk(relaxed = true)

        // Set up the mock AptitudeRetrieve to return your malformed data
        every { aptitudeRetrieve.toString() } returns malformedData

        // Create a mock Call object
        val call: Call<AptitudeRetrieve> = mockk()

        // Set up the mock Call to return a Response containing your mock AptitudeRetrieve
        every { call.execute() } returns Response.success(aptitudeRetrieve)

        // Set up aptoideApi.getListings() to return the mock Call
        every { aptoideApi.getListings() } returns call

        val result = appsRepository.getApps().toList().flatten()
        assertTrue(result.isEmpty())
    }
}