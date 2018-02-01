package cn.fjlcx.application.bean;

import javax.persistence.*;

@Table(name = "oa_dictionary")
public class Dictionary {
    @Id
    @Column(name = "dict_id")
    private Integer dictId;

    @Column(name = "dict_type_id")
    private Integer dictTypeId;

    @Column(name = "dict_code")
    private String dictCode;
    
    @Column(name = "dict_name")
    private String dictName;

    @Column(name = "dict_order")
    private Integer dictOrder;

    @Column(name = "dict_description")
    private String dictDescription;

    @Transient
    private DictionaryType dictType;
    
    public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	
	public DictionaryType getDictType() {
		return dictType;
	}

	public void setDictType(DictionaryType dictType) {
		this.dictType = dictType;
	}

	/**
     * @return dict_id
     */
    public Integer getDictId() {
        return dictId;
    }

    /**
     * @param dictId
     */
    public void setDictId(Integer dictId) {
        this.dictId = dictId;
    }

    /**
     * @return dict_type_id
     */
    public Integer getDictTypeId() {
        return dictTypeId;
    }

    /**
     * @param dictTypeId
     */
    public void setDictTypeId(Integer dictTypeId) {
        this.dictTypeId = dictTypeId;
    }

    /**
     * @return dict_code
     */
    public String getDictCode() {
        return dictCode;
    }

    /**
     * @param dictCode
     */
    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    /**
     * @return dict_order
     */
    public Integer getDictOrder() {
        return dictOrder;
    }

    /**
     * @param dictOrder
     */
    public void setDictOrder(Integer dictOrder) {
        this.dictOrder = dictOrder;
    }

    /**
     * @return dict_description
     */
    public String getDictDescription() {
        return dictDescription;
    }

    /**
     * @param dictDescription
     */
    public void setDictDescription(String dictDescription) {
        this.dictDescription = dictDescription;
    }
}