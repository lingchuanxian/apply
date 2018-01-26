package cn.fjlcx.application.bean;

import javax.persistence.*;

@Table(name = "oa_department")
public class Department {
    @Id
    @Column(name = "dep_id")
    private Integer depId;

    @Column(name = "dep_name")
    private String depName;

    @Column(name = "dep_orgid")
    private Integer depOrgid;

    @Column(name = "dep_pid")
    private Integer depPid;

    @Column(name = "dep_order")
    private Integer depOrder;

    @Column(name = "dep_detail")
    private String depDetail;
    
    @Column(name = "dep_isdel")
    private String depIsDel;
    
    @Transient
    private Organization organization;
    
    public String getDepIsDel() {
		return depIsDel;
	}

	public void setDepIsDel(String depIsDel) {
		this.depIsDel = depIsDel;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	/**
     * @return dep_id
     */
    public Integer getDepId() {
        return depId;
    }

    /**
     * @param depId
     */
    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    /**
     * @return dep_name
     */
    public String getDepName() {
        return depName;
    }

    /**
     * @param depName
     */
    public void setDepName(String depName) {
        this.depName = depName;
    }

    /**
     * @return dep_orgid
     */
    public Integer getDepOrgid() {
        return depOrgid;
    }

    /**
     * @param depOrgid
     */
    public void setDepOrgid(Integer depOrgid) {
        this.depOrgid = depOrgid;
    }

    /**
     * @return dep_pid
     */
    public Integer getDepPid() {
        return depPid;
    }

    /**
     * @param depPid
     */
    public void setDepPid(Integer depPid) {
        this.depPid = depPid;
    }

    /**
     * @return dep_order
     */
    public Integer getDepOrder() {
        return depOrder;
    }

    /**
     * @param depOrder
     */
    public void setDepOrder(Integer depOrder) {
        this.depOrder = depOrder;
    }

    /**
     * @return dep_detail
     */
    public String getDepDetail() {
        return depDetail;
    }

    /**
     * @param depDetail
     */
    public void setDepDetail(String depDetail) {
        this.depDetail = depDetail;
    }

	@Override
	public String toString() {
		return "Department [depId=" + depId + ", depName=" + depName + ", depOrgid=" + depOrgid + ", depPid=" + depPid
				+ ", depOrder=" + depOrder + ", depDetail=" + depDetail + ", organization=" + organization + "]";
	}
}