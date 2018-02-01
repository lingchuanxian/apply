package cn.fjlcx.application.bean;

import javax.persistence.*;

@Table(name = "oa_dictionary_type")
public class DictionaryType {
    @Id
    @Column(name = "dt_id")
    private Integer dtId;

    @Column(name = "dt_code")
    private String dtCode;

    @Column(name = "dt_name")
    private String dtName;

    @Column(name = "dt_description")
    private String dtDescription;

    /**
     * @return dt_id
     */
    public Integer getDtId() {
        return dtId;
    }

    /**
     * @param dtId
     */
    public void setDtId(Integer dtId) {
        this.dtId = dtId;
    }

    /**
     * @return dt_code
     */
    public String getDtCode() {
        return dtCode;
    }

    /**
     * @param dtCode
     */
    public void setDtCode(String dtCode) {
        this.dtCode = dtCode;
    }

    /**
     * @return dt_name
     */
    public String getDtName() {
        return dtName;
    }

    /**
     * @param dtName
     */
    public void setDtName(String dtName) {
        this.dtName = dtName;
    }

    /**
     * @return dt_description
     */
    public String getDtDescription() {
        return dtDescription;
    }

    /**
     * @param dtDescription
     */
    public void setDtDescription(String dtDescription) {
        this.dtDescription = dtDescription;
    }
}