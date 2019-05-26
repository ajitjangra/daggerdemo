package com.gojek.weather.ui;


import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.gojek.weather.R;

import junit.framework.AssertionFailedError;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNot.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class WeatherActivityWithSuccessOrFailTest {

  @Rule
  public ActivityTestRule<WeatherActivity> mActivityTestRule = new ActivityTestRule<>(WeatherActivity.class);

  @Rule
  public GrantPermissionRule mGrantPermissionRule =
      GrantPermissionRule.grant(
          "android.permission.ACCESS_FINE_LOCATION",
          "android.permission.ACCESS_COARSE_LOCATION");


  @Test
  public void tempCheck(){
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    ViewInteraction vi_tvError = onView(
        allOf(withId(R.id.tvError),
            isDisplayed()));

    try {
      vi_tvError.check(matches(isDisplayed()));
      errorTest();
    } catch (NoMatchingViewException e) {
      currentTempTest();
      forecastTempTest();
    }
  }

  public void currentTempTest(){
    try {
      Thread.sleep(700);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    ViewInteraction vi_tvCurrentTemp = onView(
        allOf(withId(R.id.tvCurrentTemp),
            isDisplayed()));

    vi_tvCurrentTemp.check(matches(isDisplayed()));
    vi_tvCurrentTemp.check(matches(not(withText(""))));

    ViewInteraction vi_tvLocation = onView(
        allOf(withId(R.id.tvLocation),
            isDisplayed()));

    vi_tvLocation.check(matches(isDisplayed()));
    vi_tvLocation.check(matches(not(withText(""))));
  }

  public void forecastTempTest(){
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    ViewInteraction vi_rvForeCast = onView(
        allOf(childAtPosition(
            allOf(withId(R.id.rvForeCast),
                childAtPosition(
                    withId(R.id.weatherView),
                    2)),
            0),
            isDisplayed()));
    vi_rvForeCast.check(matches(isDisplayed()));

    ViewInteraction vi_tvDay = onView(
        allOf(withId(R.id.tvDay),
            childAtPosition(
                childAtPosition(
                    withId(R.id.rvForeCast),
                    0),
                0),
            isDisplayed()));
    vi_tvDay.check(matches(not(withText(""))));

    ViewInteraction vi_tvTemp = onView(
        allOf(withId(R.id.tvTemp),
            childAtPosition(
                childAtPosition(
                    withId(R.id.rvForeCast),
                    0),
                1),
            isDisplayed()));
    vi_tvTemp.check(matches(not(withText(""))));
  }

  private void errorTest() {
    ViewInteraction vi_tvError = onView(
        allOf(withId(R.id.tvError),
            isDisplayed()));

    vi_tvError.check(matches(not(withText(""))));

    ViewInteraction vi_btnRetry = onView(
        allOf(withId(R.id.btnRetry),
            isDisplayed()));

    vi_btnRetry.check(matches(isDisplayed()));
    vi_btnRetry.check(matches(not(withText(""))));

    vi_btnRetry.perform(click());
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
