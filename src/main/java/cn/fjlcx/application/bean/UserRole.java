package cn.fjlcx.application.bean;

import javax.persistence.*;

@Table(name = "oa_user_role")
public class UserRole {
    @Id
    @Column(name = "ur_id")
    private Integer urId;

    @Column(name = "ur_uid")
    private Integer urUid;

    @Column(name = "ur_rid")
    private Integer urRid;
    
    @Column(name = "ur_isdel")
    private Integer urIsdel;
    
    @Transient
    private Role urRole;
    
    @Transient
    private User urUser;
    
	public Integer getUrIsdel() {
		return urIsdel;
	}

	public void setUrIsdel(Integer urIsdel) {
		this.urIsdel = urIsdel;
	}

	public User getUrUser() {
		return urUser;
	}

	public void setUrUser(User urUser) {
		this.urUser = urUser;
	}

	public Role getUrRole() {
		return urRole;
	}

	public void setUrRole(Role urRole) {
		this.urRole = urRole;
	}

	/**
     * @return ur_id
     */
    public Integer getUrId() {
        return urId;
    }

    /**
     * @param urId
     */
    public void setUrId(Integer urId) {
        this.urId = urId;
    }

    /**
     * @return ur_uid
     */
    public Integer getUrUid() {
        return urUid;
    }

    /**
     * @param urUid
     */
    public void setUrUid(Integer urUid) {
        this.urUid = urUid;
    }

    /**
     * @return ur_rid
     */
    public Integer getUrRid() {
        return urRid;
    }

    /**
     * @param urRid
     */
    public void setUrRid(Integer urRid) {
        this.urRid = urRid;
    }
}