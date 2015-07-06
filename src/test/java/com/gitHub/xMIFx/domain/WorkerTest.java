package com.gitHub.xMIFx.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Vlad on 06.07.2015.
 */
public class WorkerTest {
    @Test
    public void countSalaryPositiveTestBothParametersMoreThenZero() {
        Worker worker = new Worker();
        int workingTime = 10;
        int payment = 100;
        int expected = 1000;

        int result = worker.calculateSalary(workingTime, payment);

        Assert.assertEquals(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void countSalaryNegativeTestWorkingTimeLessThenZero() {
        Worker worker = new Worker();
        int workingTime = -10;
        int payment = 100;

        int result = worker.calculateSalary(workingTime, payment);

        Assert.fail("expected illegalArgumentException");
    }

    @Test(expected = IllegalArgumentException.class)
    public void countSalaryNegativeTestPaymentIsZero() {
        Worker worker = new Worker();
        int workingTime = 10;
        int payment = 0;

        int result = worker.calculateSalary(workingTime, payment);

        Assert.fail("expected illegalArgumentException");
    }


}
