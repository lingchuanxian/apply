package cn.fjlcx.application.bean;

import javax.persistence.*;

@Table(name = "oa_article_type")
public class ArticleType {
    @Id
    @Column(name = "at_id")
    private Integer atId;

    @Column(name = "at_name")
    private String atName;

    /**
     * @return at_id
     */
    public Integer getAtId() {
        return atId;
    }

    /**
     * @param atId
     */
    public void setAtId(Integer atId) {
        this.atId = atId;
    }

    /**
     * @return at_name
     */
    public String getAtName() {
        return atName;
    }

    /**
     * @param atName
     */
    public void setAtName(String atName) {
        this.atName = atName;
    }
}