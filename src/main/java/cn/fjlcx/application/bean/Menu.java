package cn.fjlcx.application.bean;

import javax.persistence.*;

@Table(name = "oa_menu")
public class Menu {
    @Id
    @Column(name = "mu_id")
    private Integer muId;

    @Column(name = "mu_text")
    private String muText;

    @Column(name = "mu_code")
    private String muCode;
    
    @Column(name = "mu_state")
    private String muState;

    @Column(name = "mu_iconCls")
    private String muIconcls;

    @Column(name = "mu_url")
    private String muUrl;

    @Column(name = "mu_pid")
    private Integer muPid;
    
    @Column(name = "mu_checked")
    private Boolean muChecked;
    
    @Column(name = "mu_request_url")
    private String muRequestUrl;
    
    @Column(name = "mu_order")
    private Integer muOrder;
    
    @Column(name = "mu_isdel")
    private Integer muIsdel;
    
    @Column(name = "mu_type")
    private Integer muType;
    
    public Integer getMuType() {
		return muType;
	}

	public void setMuType(Integer muType) {
		this.muType = muType;
	}

	public Integer getMuIsdel() {
		return muIsdel;
	}

	public void setMuIsdel(Integer muIsdel) {
		this.muIsdel = muIsdel;
	}

	public String getMuCode() {
		return muCode;
	}

	public void setMuCode(String muCode) {
		this.muCode = muCode;
	}

	public Boolean getMuChecked() {
		return muChecked;
	}

	public void setMuChecked(Boolean muChecked) {
		this.muChecked = muChecked;
	}

	public Integer getMuOrder() {
		return muOrder;
	}

	public void setMuOrder(Integer muOrder) {
		this.muOrder = muOrder;
	}

	/**
     * @return mu_id
     */
    public Integer getMuId() {
        return muId;
    }

    /**
     * @param muId
     */
    public void setMuId(Integer muId) {
        this.muId = muId;
    }

    /**
     * @return mu_text
     */
    public String getMuText() {
        return muText;
    }

    /**
     * @param muText
     */
    public void setMuText(String muText) {
        this.muText = muText;
    }

    /**
     * @return mu_state
     */
    public String getMuState() {
        return muState;
    }

    /**
     * @param muState
     */
    public void setMuState(String muState) {
        this.muState = muState;
    }

    /**
     * @return mu_iconCls
     */
    public String getMuIconcls() {
        return muIconcls;
    }

    /**
     * @param muIconcls
     */
    public void setMuIconcls(String muIconcls) {
        this.muIconcls = muIconcls;
    }

    /**
     * @return mu_url
     */
    public String getMuUrl() {
        return muUrl;
    }

    /**
     * @param muUrl
     */
    public void setMuUrl(String muUrl) {
        this.muUrl = muUrl;
    }

    /**
     * @return mu_pid
     */
    public Integer getMuPid() {
        return muPid;
    }

    /**
     * @param muPid
     */
    public void setMuPid(Integer muPid) {
        this.muPid = muPid;
    }
    
    /**
     * @return mu_request_url
     */
	public String getMuRequestUrl() {
		return muRequestUrl;
	}
	
	/**
     * @param muRequestUrl
     */
	public void setMuRequestUrl(String muRequestUrl) {
		this.muRequestUrl = muRequestUrl;
	}

	@Override
	public String toString() {
		return "Menu [muId=" + muId + ", muText=" + muText + ", muCode=" + muCode + ", muState=" + muState
				+ ", muIconcls=" + muIconcls + ", muUrl=" + muUrl + ", muPid=" + muPid + ", muChecked=" + muChecked
				+ ", muRequestUrl=" + muRequestUrl + ", muOrder=" + muOrder + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((muChecked == null) ? 0 : muChecked.hashCode());
		result = prime * result + ((muCode == null) ? 0 : muCode.hashCode());
		result = prime * result + ((muIconcls == null) ? 0 : muIconcls.hashCode());
		result = prime * result + ((muId == null) ? 0 : muId.hashCode());
		result = prime * result + ((muOrder == null) ? 0 : muOrder.hashCode());
		result = prime * result + ((muPid == null) ? 0 : muPid.hashCode());
		result = prime * result + ((muRequestUrl == null) ? 0 : muRequestUrl.hashCode());
		result = prime * result + ((muState == null) ? 0 : muState.hashCode());
		result = prime * result + ((muText == null) ? 0 : muText.hashCode());
		result = prime * result + ((muUrl == null) ? 0 : muUrl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (muChecked == null) {
			if (other.muChecked != null)
				return false;
		} else if (!muChecked.equals(other.muChecked))
			return false;
		if (muCode == null) {
			if (other.muCode != null)
				return false;
		} else if (!muCode.equals(other.muCode))
			return false;
		if (muIconcls == null) {
			if (other.muIconcls != null)
				return false;
		} else if (!muIconcls.equals(other.muIconcls))
			return false;
		if (muId == null) {
			if (other.muId != null)
				return false;
		} else if (!muId.equals(other.muId))
			return false;
		if (muOrder == null) {
			if (other.muOrder != null)
				return false;
		} else if (!muOrder.equals(other.muOrder))
			return false;
		if (muPid == null) {
			if (other.muPid != null)
				return false;
		} else if (!muPid.equals(other.muPid))
			return false;
		if (muRequestUrl == null) {
			if (other.muRequestUrl != null)
				return false;
		} else if (!muRequestUrl.equals(other.muRequestUrl))
			return false;
		if (muState == null) {
			if (other.muState != null)
				return false;
		} else if (!muState.equals(other.muState))
			return false;
		if (muText == null) {
			if (other.muText != null)
				return false;
		} else if (!muText.equals(other.muText))
			return false;
		if (muUrl == null) {
			if (other.muUrl != null)
				return false;
		} else if (!muUrl.equals(other.muUrl))
			return false;
		return true;
	}

	
}