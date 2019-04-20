package com.flong.commons.utils.date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/***
 * @Author:liangjilong
 * @Date:2016年3月20日下午8:44:05
 * @Email:jilongliang@sina.com
 * @Version:1.0
 * @CopyRight(c)Flong Intergrity Ltd.
 * @Desct:........
 */

@Component("taskJob")
public class TaskJob {

	@Scheduled(cron = "0 0/1 * * * ?")
	public void job1() {
		System.out.println("任务进行中。。。");
	}
}
