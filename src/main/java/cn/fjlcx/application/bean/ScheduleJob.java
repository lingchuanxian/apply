package cn.fjlcx.application.bean;

import java.util.Date;
import javax.persistence.*;

@Table(name = "oa_schedule_job")
public class ScheduleJob {
	
	public static final String STATUS_RUNNING = "1";
	public static final String STATUS_NOT_RUNNING = "0";
	public static final String CONCURRENT_IS = "1";
	public static final String CONCURRENT_NOT = "0";
	
    /**
     * 任务主键
     */
    @Id
    @Column(name = "job_id")
    @GeneratedValue(generator = "JDBC")
    private Integer jobId;

    /**
     * 任务创建时间
     */
    @Column(name = "job_create_date")
    private Date jobCreateDate;

    /**
     * 任务更新时间
     */
    @Column(name = "job_update_date")
    private Date jobUpdateDate;

    /**
     * 任务名称
     */
    @Column(name = "job_name")
    private String jobName;

    /**
     * 任务分组
     */
    @Column(name = "job_group")
    private String jobGroup;

    /**
     * 任务分组
     */
    @Column(name = "job_status")
    private String jobStatus;

    /**
     * cron表达式
     */
    @Column(name = "job_cron_expression")
    private String jobCronExpression;

    /**
     * 任务描述
     */
    @Column(name = "job_description")
    private String jobDescription;

    /**
     * cron表达式
     */
    @Column(name = "job_bean_class")
    private String jobBeanClass;

    /**
     * 任务是否有状态
     */
    @Column(name = "job_is_concurrent")
    private String jobIsConcurrent;

    /**
     * spring bean
     */
    @Column(name = "job_spring_id")
    private String jobSpringId;

    /**
     * 任务调用的方法名
     */
    @Column(name = "job_method_name")
    private String jobMethodName;

    /**
     * 预留字段1
     */
    @Column(name = "job_reserved1")
    private String jobReserved1;

    /**
     * 预留字段2
     */
    @Column(name = "job_reserved2")
    private String jobReserved2;

    /**
     * 预留字段3
     */
    @Column(name = "job_reserved3")
    private String jobReserved3;

    /**
     * 获取任务主键
     *
     * @return job_id - 任务主键
     */
    public Integer getJobId() {
        return jobId;
    }

    /**
     * 设置任务主键
     *
     * @param jobId 任务主键
     */
    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    /**
     * 获取任务创建时间
     *
     * @return job_create_date - 任务创建时间
     */
    public Date getJobCreateDate() {
        return jobCreateDate;
    }

    /**
     * 设置任务创建时间
     *
     * @param jobCreateDate 任务创建时间
     */
    public void setJobCreateDate(Date jobCreateDate) {
        this.jobCreateDate = jobCreateDate;
    }

    /**
     * 获取任务更新时间
     *
     * @return job_update_date - 任务更新时间
     */
    public Date getJobUpdateDate() {
        return jobUpdateDate;
    }

    /**
     * 设置任务更新时间
     *
     * @param jobUpdateDate 任务更新时间
     */
    public void setJobUpdateDate(Date jobUpdateDate) {
        this.jobUpdateDate = jobUpdateDate;
    }

    /**
     * 获取任务名称
     *
     * @return job_name - 任务名称
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * 设置任务名称
     *
     * @param jobName 任务名称
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * 获取任务分组
     *
     * @return job_group - 任务分组
     */
    public String getJobGroup() {
        return jobGroup;
    }

    /**
     * 设置任务分组
     *
     * @param jobGroup 任务分组
     */
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    /**
     * 获取任务分组
     *
     * @return job_status - 任务分组
     */
    public String getJobStatus() {
        return jobStatus;
    }

    /**
     * 设置任务分组
     *
     * @param jobStatus 任务分组
     */
    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    /**
     * 获取cron表达式
     *
     * @return job_cron_expression - cron表达式
     */
    public String getJobCronExpression() {
        return jobCronExpression;
    }

    /**
     * 设置cron表达式
     *
     * @param jobCronExpression cron表达式
     */
    public void setJobCronExpression(String jobCronExpression) {
        this.jobCronExpression = jobCronExpression;
    }

    /**
     * 获取任务描述
     *
     * @return job_description - 任务描述
     */
    public String getJobDescription() {
        return jobDescription;
    }

    /**
     * 设置任务描述
     *
     * @param jobDescription 任务描述
     */
    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    /**
     * 获取cron表达式
     *
     * @return job_bean_class - cron表达式
     */
    public String getJobBeanClass() {
        return jobBeanClass;
    }

    /**
     * 设置cron表达式
     *
     * @param jobBeanClass cron表达式
     */
    public void setJobBeanClass(String jobBeanClass) {
        this.jobBeanClass = jobBeanClass;
    }

    /**
     * 获取任务是否有状态
     *
     * @return job_is_concurrent - 任务是否有状态
     */
    public String getJobIsConcurrent() {
        return jobIsConcurrent;
    }

    /**
     * 设置任务是否有状态
     *
     * @param jobIsConcurrent 任务是否有状态
     */
    public void setJobIsConcurrent(String jobIsConcurrent) {
        this.jobIsConcurrent = jobIsConcurrent;
    }

    /**
     * 获取spring bean
     *
     * @return job_spring_id - spring bean
     */
    public String getJobSpringId() {
        return jobSpringId;
    }

    /**
     * 设置spring bean
     *
     * @param jobSpringId spring bean
     */
    public void setJobSpringId(String jobSpringId) {
        this.jobSpringId = jobSpringId;
    }

    /**
     * 获取任务调用的方法名
     *
     * @return job_method_name - 任务调用的方法名
     */
    public String getJobMethodName() {
        return jobMethodName;
    }

    /**
     * 设置任务调用的方法名
     *
     * @param jobMethodName 任务调用的方法名
     */
    public void setJobMethodName(String jobMethodName) {
        this.jobMethodName = jobMethodName;
    }

    /**
     * 获取预留字段1
     *
     * @return job_reserved1 - 预留字段1
     */
    public String getJobReserved1() {
        return jobReserved1;
    }

    /**
     * 设置预留字段1
     *
     * @param jobReserved1 预留字段1
     */
    public void setJobReserved1(String jobReserved1) {
        this.jobReserved1 = jobReserved1;
    }

    /**
     * 获取预留字段2
     *
     * @return job_reserved2 - 预留字段2
     */
    public String getJobReserved2() {
        return jobReserved2;
    }

    /**
     * 设置预留字段2
     *
     * @param jobReserved2 预留字段2
     */
    public void setJobReserved2(String jobReserved2) {
        this.jobReserved2 = jobReserved2;
    }

    /**
     * 获取预留字段3
     *
     * @return job_reserved3 - 预留字段3
     */
    public String getJobReserved3() {
        return jobReserved3;
    }

    /**
     * 设置预留字段3
     *
     * @param jobReserved3 预留字段3
     */
    public void setJobReserved3(String jobReserved3) {
        this.jobReserved3 = jobReserved3;
    }

	@Override
	public String toString() {
		return "ScheduleJob [jobId=" + jobId + ", jobCreateDate=" + jobCreateDate + ", jobUpdateDate=" + jobUpdateDate
				+ ", jobName=" + jobName + ", jobGroup=" + jobGroup + ", jobStatus=" + jobStatus
				+ ", jobCronExpression=" + jobCronExpression + ", jobDescription=" + jobDescription + ", jobBeanClass="
				+ jobBeanClass + ", jobIsConcurrent=" + jobIsConcurrent + ", jobSpringId=" + jobSpringId
				+ ", jobMethodName=" + jobMethodName + ", jobReserved1=" + jobReserved1 + ", jobReserved2="
				+ jobReserved2 + ", jobReserved3=" + jobReserved3 + "]";
	}
    
}