package fr.cedriccreusot.presentation.detail.viewmodels

import com.google.common.truth.Truth.assertThat
import fr.cedriccreusot.domain.entities.Track
import org.junit.jupiter.api.Test

class TrackViewModelTest {

    @Test
    fun testTitle() {
        val trackViewModel = TrackViewModel(
            Track(
                "1",
                "title",
                "full",
                "thumbnail"
            )
        )

        assertThat(trackViewModel.title).isEqualTo("title")
    }

    @Test
    internal fun testThumbnail() {
        val trackViewModel = TrackViewModel(
            Track(
                "1",
                "title",
                "full",
                "thumbnail"
            )
        )

        assertThat(trackViewModel.thumbnail).isEqualTo("thumbnail")
    }
}