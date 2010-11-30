package org.jixiuf.util;

import java.io.File;
import java.io.FileFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
//因为 报表统计阶段会生成一些临时文件，这些文件如果不定时清除会充满服务器硬盘空间，
//此利用quartz 的定时功能 ，每隔15天对临时目录进行清理
public class DeleteTmpWordFileJob implements Job, InitializingBean {
	Logger log = LoggerFactory.getLogger(DeleteTmpWordFileJob.class);


	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		String tmpdir = System.getProperty("java.io.tmpdir");
		File tmpFileDir = new File(tmpdir);
		File[] fs = tmpFileDir.listFiles(new FileFilter() {

			//只清理那些开关是日期格式 且以各区域分销商级别分布.形图\\.doc  为结尾  两天前的目录，的目录
			public boolean accept(File pathname) {
				String fileName = pathname.getName();

				if (fileName.matches("\\d{17}_各区域分销商级别分布.形图\\.doc")) {
					String dateString = fileName.substring(0, 17);
					Calendar targetDate = Calendar.getInstance();
					targetDate.add(Calendar.DAY_OF_MONTH, -2);
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyyMMddhhmmssSSS");
					try {
						Date fileDate = sdf.parse(dateString);
						sdf = null;
						if (fileDate.before(targetDate.getTime())) {// 如果文件名是两天前的文件，return
							return true;
						}
					} catch (ParseException e) {
						e.printStackTrace();
						return false;
					}

				}

				return false;
			}
		});

		for (File f : fs) {
			f.delete();
		}
		log.info("deleting the MS Word files in tmp dir two days before  at"
				+ new Date());

	}

	Scheduler sched = null;

	public void afterPropertiesSet() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();

		sched = sf.getScheduler();
		JobDetail deleteTmpFilesJob = new JobDetail("delete tmp word file ",
				"group1", DeleteTmpWordFileJob.class);

		SimpleTrigger trigger = new SimpleTrigger(
				"trigger for deleting tmp word file ", "group1");
		trigger.setRepeatInterval(1000 * 60 * 60 * 24 * 15);// 15天运行一次
		trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);// 无限次运行
		sched.scheduleJob(deleteTmpFilesJob, trigger);


		sched.start();
	}

	public void destroy() {
		try {
			if (sched != null)
				sched.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
