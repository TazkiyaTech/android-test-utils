import android.view.View
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

/**
 * Asserts that a view exists in the view hierarchy and is matched by all of the given view matchers.
 *
 * This function makes Espresso tests easier to read and write by combining a series of calls into one.
 *
 * @param matchers the array of [Matcher]s to match.
 */
fun ViewInteraction.checkMatches(vararg matchers: Matcher<View>): ViewInteraction {
    return check(matches(allOf(*matchers)))
}
