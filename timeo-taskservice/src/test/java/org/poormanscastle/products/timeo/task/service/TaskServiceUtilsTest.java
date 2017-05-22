package org.poormanscastle.products.timeo.task.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by georg on 11/05/2017.
 */
public class TaskServiceUtilsTest {

    TaskServiceUtils utils;

    @Before
    public void setup() {
        utils = new TaskServiceUtils();
    }

    @Test
    public void testParseDurationString1() throws Exception {
        int result = utils.parseDurationString("4s 10s 6s");
        assertEquals(20, result);
    }

    @Test
    public void testParseDurationString2() throws Exception {
        assertEquals(200, utils.parseDurationString("3m 20s"));
    }

    @Test
    public void testParseDurationString3() throws Exception {
        assertEquals(36600, utils.parseDurationString("10h10m"));
    }

    @Test
    public void testParseDurationString4() throws Exception {
        assertEquals(123048, utils.parseDurationString("4d2h10m48s"));
    }

    @Test
    public void testParseDurationString5() throws Exception {
        // TODO this input shall fail because of missing unit
        assertEquals(0, utils.parseDurationString("48"));
    }

    @Test
    public void testParseDurationString6() throws Exception {
        // TODO this input shall fail, utter gibberish
        assertEquals(0, utils.parseDurationString("Poorman's Castle rules!"));
    }

    @Test
    public void testDurationString1() throws Exception {
        assertEquals("59s", utils.createDurationStringFromSeconds(59));
    }

    @Test
    public void testDurationString2() throws Exception {
        assertEquals("1m1s", utils.createDurationStringFromSeconds(61));
    }

    @Test
    public void testDurationString3() throws Exception {
        assertEquals("1h1m1s", utils.createDurationStringFromSeconds(3661));
    }

    @Test
    public void testDurationString4() throws Exception {
        assertEquals("1d", utils.createDurationStringFromSeconds(28800));
    }

    @Test
    public void testDurationString5() throws Exception {
        assertEquals("1h1s", utils.createDurationStringFromSeconds(3601));
    }

    @Test
    public void testGetCalendarWeek1() throws Exception {
        List<DateTime> calWeek2017_21 = TaskServiceUtils.getCalendarWeek(2017, 21);
        assertNotNull(calWeek2017_21);
        assertEquals(7, calWeek2017_21.size());
        assertEquals(1, calWeek2017_21.get(0).getDayOfWeek());
        assertEquals(7, calWeek2017_21.get(6).getDayOfWeek());
        assertEquals(22, calWeek2017_21.get(0).getDayOfMonth());
        assertEquals(5, calWeek2017_21.get(0).getMonthOfYear());
    }

}