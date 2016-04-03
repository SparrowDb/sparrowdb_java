package org.sparrow.db.compaction;

import org.quartz.JobDetail;
import org.quartz.Trigger;

/**
 * Created by mauricio on 13/03/16.
 */
public class CompactionJob
{
    private String jobName;
    private Trigger trigger;
    private JobDetail jobDetail;
    private Class jobClass;

    public CompactionJob()
    {
    }

    public CompactionJob(String jobName, Trigger trigger, JobDetail jobDetail, Class jobClass)
    {
        this.jobName = jobName;
        this.trigger = trigger;
        this.jobDetail = jobDetail;
        this.jobClass = jobClass;
    }

    public String getJobName()
    {
        return jobName;
    }

    public void setJobName(String jobName)
    {
        this.jobName = jobName;
    }

    public Trigger getTrigger()
    {
        return trigger;
    }

    public void setTrigger(Trigger trigger)
    {
        this.trigger = trigger;
    }

    public JobDetail getJobDetail()
    {
        return jobDetail;
    }

    public void setJobDetail(JobDetail jobDetail)
    {
        this.jobDetail = jobDetail;
    }

    public Class getJobClass()
    {
        return jobClass;
    }

    public void setJobClass(Class jobClass)
    {
        this.jobClass = jobClass;
    }
}

