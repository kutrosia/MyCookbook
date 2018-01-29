package com.pwr.mycookbook.ui.main;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.pwr.mycookbook.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddEditCategoryTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addEditCategoryTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Dodaj kategorię"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.categories_icon_spinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction relativeLayout = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(7);
        relativeLayout.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.category_name_editText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.category_name_TextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Ciasta"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.create_button), withText("Utwórz"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton.perform(click());

        DataInteraction relativeLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.list_view_item),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)))
                .atPosition(0);
        relativeLayout2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3544429);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_edit_category), withContentDescription("Edytuj"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        3),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.categories_icon_spinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        DataInteraction relativeLayout3 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(7);
        relativeLayout3.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.category_name_editText), withText("Ciasta"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.category_name_TextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("Desery"));

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.category_name_editText), withText("Desery"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.category_name_TextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.create_button), withText("Utwórz"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton2.perform(click());

        pressBack();

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
