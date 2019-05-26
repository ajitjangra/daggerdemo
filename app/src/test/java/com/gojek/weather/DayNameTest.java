package com.gojek.weather;

import com.gojek.weather.util.Utility;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(value = Parameterized.class)
public class DayNameTest {
  @Parameterized.Parameter(value = 0)
  public String inputDate;

  @Parameterized.Parameter(value = 1)
  public String outputDay;

  @Parameterized.Parameters(name = "{index}: inputDate({0}) outputDay({1})")
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {"", ""},
        {null, ""},
        {"2019-05-19", "Sunday"},
        {"2019-05-20", "Monday"},
        {"2019-05-21", "Tuesday"},
        {"2019-05-22", "Wednesday"},
        {"2019-05-23", "Thursday"},
        {"2019-05-24", "Friday"},
        {"2019-05-25", "Saturday"},
        {"2019-May-25",""}
    });
  }

  @Test
  public void getDayName() {
    assertThat(Utility.INSTANCE.getDayName(inputDate), is(outputDay));
  }
}
