package cn.fjlcx.application.bean;

public class SubmitParams {
	private String operateType;
	private String operateParams;
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getOperateParams() {
		return operateParams;
	}
	public void setOperateParams(String operateParams) {
		this.operateParams = operateParams;
	}
	
	public SubmitParams() {
		super();
	}
	
	public SubmitParams(String operateType, String operateParams) {
		super();
		this.operateType = operateType;
		this.operateParams = operateParams;
	}
	@Override
	public String toString() {
		return "SubmitParams [operateType=" + operateType + ", operateParams=" + operateParams + "]";
	}
	
}
