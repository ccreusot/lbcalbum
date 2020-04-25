package fr.cedriccreusot.lbcalbum

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import com.schibsted.spain.barista.interaction.BaristaSleepInteractions.sleep
import com.schibsted.spain.barista.rule.cleardata.ClearFilesRule
import fr.cedriccreusot.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class AlbumScreenBehavior {

    @get:Rule var activityScenarioRule = activityScenarioRule<MainActivity>()

    @get:Rule var clearFileRule = ClearFilesRule()

    @Test
    fun album_loading_list() {
        assertDisplayed(R.id.albumProgressBar)
        sleep(500)
        assertDisplayed(R.id.albumRecyclerView)
    }

    @Test
    fun album_loading_and_click_item_in_list_to_see_detail_screen() {
        assertDisplayed(R.id.albumProgressBar)
        sleep(500)
        assertDisplayed(R.id.albumRecyclerView)
        assertListNotEmpty(R.id.albumRecyclerView)
        clickListItem(R.id.albumRecyclerView, 2)
        sleep(500)
        assertDisplayed(R.id.trackRecyclerView)
        assertListNotEmpty(R.id.trackRecyclerView)
    }
}