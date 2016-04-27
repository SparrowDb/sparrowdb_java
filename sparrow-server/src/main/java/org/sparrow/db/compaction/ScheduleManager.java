package org.sparrow.db.compaction;

import com.google.common.collect.Sets;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Set;

/**
 * Created by mauricio on 10/03/16.
 */
public class ScheduleManager
{
    public static final ScheduleManager instance = new ScheduleManager();
    private Set<ScheduleJob> jobs = Sets.newConcurrentHashSet();
    private Scheduler scheduler;

    private ScheduleManager()
    {
        try
        {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
        }
        catch (SchedulerException e)
        {
            e.printStackTrace();
        }
    }

    private Trigger createTrigger(String triggerName, String groupName, String cronExpression)
    {
        return  TriggerBuilder.newTrigger()
                .withIdentity(triggerName, groupName)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
    }

    private JobDetail createJob(String jobName, String groupName)
    {
        return JobBuilder.newJob(DataHolderCompact.class)
                .withIdentity(jobName, groupName).build();
    }

    public void addJob(String jobName, String cron, Class jobClass)
    {
        String groupName = jobName+"Group";
        jobs.add(new ScheduleJob(
                jobName,
                createTrigger(jobName+"Trigger", groupName, cron),
                createJob(jobName+"Job", groupName),
                jobClass));
    }

    public void startScheduler()
    {
        jobs.forEach(x -> {
            try
            {
                scheduler.scheduleJob(x.getJobDetail(), x.getTrigger());
            }
            catch (SchedulerException e)
            {
                e.printStackTrace();
            }
        });
    }
}
