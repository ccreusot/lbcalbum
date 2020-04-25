package fr.cedriccreusot.presentation.detail.viewmodels

import com.google.common.truth.Truth.assertThat
import fr.cedriccreusot.domain.entities.Track
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class TrackViewModelTest {

    private lateinit var viewModel: TrackViewModel

    @BeforeEach
    internal fun setUp() {
        viewModel = TrackViewModel(
            Track(
                "1",
                "title",
                "full",
                "thumbnail"
            )
        )
    }

    @Test
    @DisplayName("""
        Given we want the title of the track
        When we're retrieving the title from the view model
        Then should return the title from the track
    """)
    fun testTitle() {
        assertThat(viewModel.title).isEqualTo("title")
    }

    @Test
    @DisplayName("""
        Given we want the thumbnail of the track
        When we're retrieving the thumbnail from the view model
        Then should return the thumbnail from the track
    """)
    internal fun testThumbnail() {
        assertThat(viewModel.thumbnail).isEqualTo("thumbnail")
    }
}