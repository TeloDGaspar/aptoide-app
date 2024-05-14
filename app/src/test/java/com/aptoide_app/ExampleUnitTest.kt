package com.aptoide_app

import com.aptoide_app.data.local.FullDetailAppDao
import com.aptoide_app.data.local.FullDetailAppEntity
import com.aptoide_app.data.remote.AptoideApi
import com.aptoide_app.domain.AppsRepositoryImpl
import com.aptoide_app.domain.ConnectivityObserver
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ExampleUnitTest {


    private val aptoideApi: AptoideApi = mockk()
    private val fullDetailAppDao: FullDetailAppDao = mockk()
    private val connectivityObserver: ConnectivityObserver = mockk(relaxed = true)
    val myScheduler = TestCoroutineScheduler()
    private val dispatcher = UnconfinedTestDispatcher()
    private lateinit var appsRepository: AppsRepositoryImpl

    @Before
    fun setup() {

        appsRepository =
            AppsRepositoryImpl(aptoideApi, fullDetailAppDao, connectivityObserver, dispatcher)
    }


    @Test
    fun `getApps returns list of apps when data available`() = runTest(dispatcher) {
        val expectedApps = listOf(
            FullDetailAppEntity(
                added = "2022-01-01",
                apkTags = listOf("tag1", "tag2"),
                downloads = 1000,
                graphic = "graphic_url",
                icon = "icon_url",
                id = 1,
                md5sum = "md5sum",
                modified = "2022-01-02",
                name = "App Name",
                packageX = "com.example.app",
                pdownloads = 2000,
                rating = 4.5,
                size = 5000L,
                storeId = 1,
                storeName = "Store Name",
                updated = "2022-01-03",
                uptype = "uptype",
                vercode = 1,
                vername = "1.0.0"
            )
        )

        every { connectivityObserver.observe } returns MutableStateFlow(ConnectivityObserver.Status.Lost)
        coEvery { fullDetailAppDao.getFullDetailApp() } returns expectedApps

        val result = appsRepository.getApps().toList().flatten()

        assertEquals(expectedApps, result)
    }

    @Test
    fun `getApps returns empty list when no data available`() = runTest() {
        coEvery { fullDetailAppDao.getFullDetailApp() } returns emptyList()

        val result = appsRepository.getApps().toList().flatten()

        assertTrue(result.isEmpty())
    }
}



class AptoideApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var aptoideApi: AptoideApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        aptoideApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/api/"))
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(AptoideApi::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get listings from API`()  {
        // Arrange
        val expectedResponse = """
            {
                "responses": {
                    "listApps": {
                        "datasets": {
                            "all": {
                                "data": {
                                    "list": [
                                        {
                                            "id": 1,
                                            "name": "App1",
                                            "version": "1.0"
                                        },
                                        {
                                            "id": 2,
                                            "name": "App2",
                                            "version": "1.1"
                                        }
                                    ]
                                }
                            }
                        }
                    }
                }
            }
        """.trimIndent()
        mockWebServer.enqueue(MockResponse().setBody(expectedResponse))

        // Act
        val response = aptoideApi.getListings().execute()

        // Assert
        val actualResponse = response.body()
        assertEquals(2, actualResponse?.responses?.listApps?.datasets?.all?.data?.list?.size)
        assertEquals("App1", actualResponse?.responses?.listApps?.datasets?.all?.data?.list?.get(0)?.name)
        assertEquals("App2", actualResponse?.responses?.listApps?.datasets?.all?.data?.list?.get(1)?.name)
    }
}
