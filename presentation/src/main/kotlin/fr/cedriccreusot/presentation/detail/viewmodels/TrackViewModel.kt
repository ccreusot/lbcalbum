package fr.cedriccreusot.presentation.detail.viewmodels

import fr.cedriccreusot.domain.entities.Track

data class TrackViewModel(private val track: Track) {
    val title = track.title
    val thumbnail = track.thumbnail
}