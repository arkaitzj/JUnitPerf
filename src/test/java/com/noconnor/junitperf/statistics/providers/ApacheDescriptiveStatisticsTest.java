package com.noconnor.junitperf.statistics.providers;

import java.util.concurrent.TimeUnit;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;
import com.noconnor.junitperf.BaseTest;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

public class ApacheDescriptiveStatisticsTest extends BaseTest {

  private ApacheDescriptiveStatistics evaluator;

  @Spy
  private DescriptiveStatistics statsMock;

  @Before
  public void setup() {
    evaluator = new ApacheDescriptiveStatistics(statsMock);
  }

  @Test
  public void whenCallingAddLatencyMeasurement_thenStatsShouldBeUpdated() {
    evaluator.addLatencyMeasurement(20345L);
    verify(statsMock).addValue(20345L);
  }

  @Test
  public void whenCallingGetErrorCount_thenValidErrorCountShouldBeReturned() {
    evaluator.incrementErrorCount();
    evaluator.incrementErrorCount();
    assertThat(evaluator.getErrorCount(), is(2L));
  }

  @Test
  public void whenCallingGetEvaluationCount_thenValidEvaluationCountShouldBeReturned() {
    evaluator.incrementEvaluationCount();
    evaluator.incrementEvaluationCount();
    assertThat(evaluator.getEvaluationCount(), is(2L));
  }

  @Test
  public void whenCallingGetMaxLatency_thenMaxLatencyShouldBeReturned() {
    evaluator.addLatencyMeasurement(10);
    evaluator.addLatencyMeasurement(1000);
    assertThat(evaluator.getMaxLatency(NANOSECONDS), is(1000L));
  }

  @Test
  public void whenCallingGetMinLatency_thenMinLatencyShouldBeReturned() {
    evaluator.addLatencyMeasurement(10);
    evaluator.addLatencyMeasurement(1000);
    assertThat(evaluator.getMinLatency(NANOSECONDS), is(10L));
  }

  @Test
  public void whenCallingGetMeanLatency_thenMeanLatencyShouldBeReturned() {
    evaluator.addLatencyMeasurement(10);
    evaluator.addLatencyMeasurement(1000);
    assertThat(evaluator.getMeanLatency(NANOSECONDS), is(505L));
  }

  @Test
  public void whenCallingGetPercentile_thenValidPercentileShouldBeReturned() {
    evaluator.addLatencyMeasurement(20345L);
    assertThat(evaluator.getLatencyPercentile(99, NANOSECONDS), is(20345L));
  }

}
