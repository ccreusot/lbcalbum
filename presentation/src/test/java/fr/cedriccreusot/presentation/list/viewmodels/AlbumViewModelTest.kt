package fr.cedriccreusot.presentation.list.viewmodels

import com.google.common.truth.Truth.assertThat
import fr.cedriccreusot.domain.entities.Album
import fr.cedriccreusot.domain.entities.Track
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class AlbumViewModelTest {

    @Test
    @DisplayName(
        """
        Given an album view model
        When we're asking for the first track thumbnail and is not present
        Then first thumbnail should be empty and it does not has a first thumbnail
    """
    )
    internal fun firstThumbnailNoTrack() {
        val albumViewModel = AlbumViewModel(
            Album(
                "1",
                listOf(
                )
            )
        )

        assertThat(albumViewModel.hasFirstThumbnail).isFalse()
        assertThat(albumViewModel.firstThumbnail).isEmpty()
    }

    @Test
    @DisplayName(
        """
        Given an album view model
        When we're asking for the first track thumbnail and is present
        Then first thumbnail should not be empty and it does has a first thumbnail
    """
    )
    internal fun firstThumbnailIfItExist() {
        val thumbnail1 = "thumbnail"
        val albumViewModel = AlbumViewModel(
            Album(
                "1",
                listOf(
                    Track("1", "title", "url", thumbnail1)
                )
            )
        )

        assertThat(albumViewModel.hasFirstThumbnail).isTrue()
        assertThat(albumViewModel.firstThumbnail).isNotEmpty()
        assertThat(albumViewModel.firstThumbnail).isEqualTo(thumbnail1)
    }

    @Test
    @DisplayName(
        """
        Given an album view model
        When we're asking for the second track thumbnail and is not present
        Then second thumbnail should be empty and it does not has a second thumbnail
    """
    )
    internal fun secondThumbnailNoTrack() {
        val thumbnail1 = "thumbnail"
        val albumViewModel = AlbumViewModel(
            Album(
                "1",
                listOf(
                    Track("1", "title", "url", thumbnail1)
                )
            )
        )

        assertThat(albumViewModel.hasSecondThumbnail).isFalse()
        assertThat(albumViewModel.secondThumbnail).isEmpty()
    }

    @Test
    @DisplayName(
        """
        Given an album view model
        When we're asking for the second track thumbnail and is present
        Then second thumbnail should not be empty and it does has a second thumbnail
    """
    )
    internal fun secondThumbnailIfItExist() {
        val thumbnail1 = "thumbnail"
        val thumbnail2 = "thumbnail2"
        val albumViewModel = AlbumViewModel(
            Album(
                "1",
                listOf(
                    Track("1", "title", "url", thumbnail1),
                    Track("1", "title", "url", thumbnail2)

                )
            )
        )

        assertThat(albumViewModel.hasSecondThumbnail).isTrue()
        assertThat(albumViewModel.secondThumbnail).isNotEmpty()
        assertThat(albumViewModel.secondThumbnail).isEqualTo(thumbnail2)
    }

    @Test
    @DisplayName(
        """
        Given an album view model
        When we're asking for the third track thumbnail and is not present
        Then third thumbnail should be empty and it does not has a third thumbnail
    """
    )
    internal fun thirdThumbnailNoTrack() {
        val thumbnail1 = "thumbnail"
        val thumbnail2 = "thumbnail2"
        val albumViewModel = AlbumViewModel(
            Album(
                "1",
                listOf(
                    Track("1", "title", "url", thumbnail1),
                    Track("1", "title", "url", thumbnail2)
                )
            )
        )

        assertThat(albumViewModel.hasThirdThumbnail).isFalse()
        assertThat(albumViewModel.thirdThumbnail).isEmpty()
    }

    @Test
    @DisplayName(
        """
        Given an album view model
        When we're asking for the third track thumbnail and is present
        Then third thumbnail should not be empty and it does has a third thumbnail
    """
    )
    internal fun thirdThumbnailIfItExist() {
        val thumbnail1 = "thumbnail"
        val thumbnail2 = "thumbnail2"
        val thumbnail3 = "thumbnail3"
        val albumViewModel = AlbumViewModel(
            Album(
                "1",
                listOf(
                    Track("1", "title", "url", thumbnail1),
                    Track("1", "title", "url", thumbnail2),
                    Track("1", "title", "url", thumbnail3)

                )
            )
        )

        assertThat(albumViewModel.hasThirdThumbnail).isTrue()
        assertThat(albumViewModel.thirdThumbnail).isNotEmpty()
        assertThat(albumViewModel.thirdThumbnail).isEqualTo(thumbnail3)
    }

    @Test
    @DisplayName(
        """
        Given an album view model
        When we're asking for the count of tracks after the first 3 tracks 
        And the album have only three tracks
        Then we should not show this number
    """
    )
    internal fun trackCountWhenOnlyThreeItems() {
        val thumbnail1 = "thumbnail"
        val thumbnail2 = "thumbnail2"
        val thumbnail3 = "thumbnail3"
        val albumViewModel = AlbumViewModel(
            Album(
                "1",
                listOf(
                    Track("1", "title", "url", thumbnail1),
                    Track("1", "title", "url", thumbnail2),
                    Track("1", "title", "url", thumbnail3)

                )
            )
        )

        assertThat(albumViewModel.hasTrackCount).isFalse()
        assertThat(albumViewModel.trackCount).isEmpty()
    }

    @Test
    @DisplayName(
        """
        Given an album view model
        When we're asking for the count of tracks after the first 3 tracks 
        And the album have more than three tracks
        Then we should show +X
    """
    )
    internal fun trackCountWhenThereIsMoreThanThreeItems() {
        val thumbnail1 = "thumbnail"
        val thumbnail2 = "thumbnail2"
        val thumbnail3 = "thumbnail3"
        val albumViewModel = AlbumViewModel(
            Album(
                "1",
                listOf(
                    Track("1", "title", "url", thumbnail1),
                    Track("1", "title", "url", thumbnail2),
                    Track("1", "title", "url", thumbnail3),
                    Track("1", "title", "url", "thumbnail")

                )
            )
        )

        assertThat(albumViewModel.hasTrackCount).isTrue()
        assertThat(albumViewModel.trackCount).isEqualTo("+1")
    }
}