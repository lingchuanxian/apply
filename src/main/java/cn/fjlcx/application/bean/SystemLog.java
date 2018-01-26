package cn.fjlcx.application.bean;

import java.util.Date;
import javax.persistence.*;

@Table(name = "oa_system_log")
public class SystemLog {
    @Id
    @Column(name = "lg_id")
    private Integer lgId;

    @Column(name = "lg_method")
    private String lgMethod;

    @Column(name = "lg_type")
    private String lgType;

    @Column(name = "lg_request_ip")
    private String lgRequestIp;

    @Column(name = "lg_exception_code")
    private String lgExceptionCode;

    @Column(name = "lg_params")
    private String lgParams;

    @Column(name = "lg_create_user")
    private Integer lgCreateUser;

    @Column(name = "lg_create_date")
    private Date lgCreateDate;

    @Column(name = "lg_description")
    private String lgDescription;

    @Column(name = "lg_exception_detail")
    private String lgExceptionDetail;

    @Transient
    private User user;
    
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
     * @return lg_id
     */
    public Integer getLgId() {
        return lgId;
    }

    /**
     * @param lgId
     */
    public void setLgId(Integer lgId) {
        this.lgId = lgId;
    }

    /**
     * @return lg_method
     */
    public String getLgMethod() {
        return lgMethod;
    }

    /**
     * @param lgMethod
     */
    public void setLgMethod(String lgMethod) {
        this.lgMethod = lgMethod;
    }

    /**
     * @return lg_type
     */
    public String getLgType() {
        return lgType;
    }

    /**
     * @param lgType
     */
    public void setLgType(String lgType) {
        this.lgType = lgType;
    }

    /**
     * @return lg_request_ip
     */
    public String getLgRequestIp() {
        return lgRequestIp;
    }

    /**
     * @param lgRequestIp
     */
    public void setLgRequestIp(String lgRequestIp) {
        this.lgRequestIp = lgRequestIp;
    }

    /**
     * @return lg_exception_code
     */
    public String getLgExceptionCode() {
        return lgExceptionCode;
    }

    /**
     * @param lgExceptionCode
     */
    public void setLgExceptionCode(String lgExceptionCode) {
        this.lgExceptionCode = lgExceptionCode;
    }

    /**
     * @return lg_params
     */
    public String getLgParams() {
        return lgParams;
    }

    /**
     * @param lgParams
     */
    public void setLgParams(String lgParams) {
        this.lgParams = lgParams;
    }

    /**
     * @return lg_create_user
     */
    public Integer getLgCreateUser() {
        return lgCreateUser;
    }

    /**
     * @param lgCreateUser
     */
    public void setLgCreateUser(Integer lgCreateUser) {
        this.lgCreateUser = lgCreateUser;
    }

    /**
     * @return lg_create_date
     */
    public Date getLgCreateDate() {
        return lgCreateDate;
    }

    /**
     * @param lgCreateDate
     */
    public void setLgCreateDate(Date lgCreateDate) {
        this.lgCreateDate = lgCreateDate;
    }

    /**
     * @return lg_description
     */
    public String getLgDescription() {
        return lgDescription;
    }

    /**
     * @param lgDescription
     */
    public void setLgDescription(String lgDescription) {
        this.lgDescription = lgDescription;
    }

    /**
     * @return lg_exception_detail
     */
    public String getLgExceptionDetail() {
        return lgExceptionDetail;
    }

    /**
     * @param lgExceptionDetail
     */
    public void setLgExceptionDetail(String lgExceptionDetail) {
        this.lgExceptionDetail = lgExceptionDetail;
    }
}