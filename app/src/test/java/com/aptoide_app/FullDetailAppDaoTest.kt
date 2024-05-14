package com.aptoide_app

import com.aptoide_app.data.local.FullDetailAppDao
import com.aptoide_app.data.local.FullDetailAppEntity
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FullDetailAppDaoTest {

    private lateinit var fullDetailAppDao: FullDetailAppDao

    @Before
    fun setup() {
        fullDetailAppDao = mockk(relaxed = true)
    }

    @Test
    fun `insertAppDetails inserts app details into database`() = runTest {
        val appDetails = FullDetailAppEntity(
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

        coEvery { fullDetailAppDao.insertAppDetails(appDetails) } just Runs
        coEvery { fullDetailAppDao.getFullDetailApp() } returns listOf(appDetails)

        fullDetailAppDao.insertAppDetails(appDetails)

        val result = fullDetailAppDao.getFullDetailApp()

        assertEquals(listOf(appDetails), result)
    }

    @Test
    fun `getFullDetailApp fetches all app details from database`() = runTest {
        val appDetails = listOf(
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
                packageX = "com.example1.app",
                pdownloads = 2000,
                rating = 4.5,
                size = 5000L,
                storeId = 1,
                storeName = "Store Name",
                updated = "2022-01-03",
                uptype = "uptype",
                vercode = 1,
                vername = "1.0.0"
            ),
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
                packageX = "com.example2.app",
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

        coEvery { fullDetailAppDao.getFullDetailApp() } returns appDetails

        val result = fullDetailAppDao.getFullDetailApp()

        assertEquals(appDetails, result)
    }

    @Test
    fun `clearCompanyListings clears all app details from database`() = runTest {
        coEvery { fullDetailAppDao.clearCompanyListings() } just Runs
        coEvery { fullDetailAppDao.getFullDetailApp() } returns emptyList()

        fullDetailAppDao.clearCompanyListings()

        val result = fullDetailAppDao.getFullDetailApp()

        assertTrue(result.isEmpty())
    }
}

