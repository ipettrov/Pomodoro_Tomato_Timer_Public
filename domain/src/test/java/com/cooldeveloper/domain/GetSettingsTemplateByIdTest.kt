package com.cooldeveloper.domain

import com.cooldeveloper.domain.exception.SettingsError
import com.cooldeveloper.domain.repository.ISettingsTemplateRepository
import com.cooldeveloper.domain.usecases.GetSettingsTemplate
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

class GetSettingsTemplateByIdTest {

    val repo: ISettingsTemplateRepository = mockk()

    val useCase = GetSettingsTemplate(repo)

    /**
     *  When the user opens the application receives previous stored settings with id 0
     *  1. Request settingsTemplate with given id form repository.
     */
    @Test
    fun `On Get Settings Template By Id Success`() = runBlockingTest {
        val TEST_SETTIGNS_TEMPLATE = getSettingsTemplate()
        val TEST_ID = getSettingsTemplate().id

        coEvery { repo.getSettingsTemplateById(TEST_ID) } returns ResultWrapper.build { TEST_SETTIGNS_TEMPLATE }

        useCase.execute(TEST_ID)
        coVerify(exactly = 1) { repo.getSettingsTemplateById(TEST_ID) }
    }

    /**
     * Error case
     */
    @Test
    fun `On Get Settings Template By Id Error`() = runBlockingTest {
        val TEST_ID = getSettingsTemplate().id

        coEvery { repo.getSettingsTemplateById(TEST_ID) } returns ResultWrapper.build { throw SettingsError.SettingsTemplateException }

        val result = useCase.execute(TEST_ID)
        assert(result is ResultWrapper.Error)
        coVerify(exactly = 1) { repo.getSettingsTemplateById(TEST_ID) }
    }


}