package cn.fjlcx.application.bean;

import java.util.Date;
import javax.persistence.*;

@Table(name = "oa_article")
public class Article {
    @Id
    @Column(name = "art_id")
    private Integer artId;

    @Column(name = "art_type")
    private Integer artType;
    
    @Column(name = "art_title")
    private String artTitle;

    @Column(name = "art_date")
    private Date artDate;

    @Column(name = "art_user")
    private Integer artUser;

    @Column(name = "art_cover")
    private String artCover;

    @Column(name = "art_top")
    private Integer artTop;

    @Column(name = "art_times")
    private Integer artTimes;

    @Column(name = "art_enclosure")
    private String artEnclosure;

    @Column(name = "art_content")
    private String artContent;
    
    @Transient
    private ArticleType articleType;
    
    @Transient
    private User user;
    
    public ArticleType getArticleType() {
		return articleType;
	}

	public void setArticleType(ArticleType articleType) {
		this.articleType = articleType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
     * @return art_id
     */
    public Integer getArtId() {
        return artId;
    }

    /**
     * @param artId
     */
    public void setArtId(Integer artId) {
        this.artId = artId;
    }

    /**
     * @return art_title
     */
    public String getArtTitle() {
        return artTitle;
    }

    /**
     * @param artTitle
     */
    public void setArtTitle(String artTitle) {
        this.artTitle = artTitle;
    }

    /**
     * @return art_type
     */
    public Integer getArtType() {
		return artType;
	}
    
    /**
     * @param art_type
     */
	public void setArtType(Integer artType) {
		this.artType = artType;
	}

	/**
     * @return art_date
     */
    public Date getArtDate() {
        return artDate;
    }

    /**
     * @param artDate
     */
    public void setArtDate(Date artDate) {
        this.artDate = artDate;
    }

    /**
     * @return art_user
     */
    public Integer getArtUser() {
        return artUser;
    }

    /**
     * @param artUser
     */
    public void setArtUser(Integer artUser) {
        this.artUser = artUser;
    }

    /**
     * @return art_cover
     */
    public String getArtCover() {
        return artCover;
    }

    /**
     * @param artCover
     */
    public void setArtCover(String artCover) {
        this.artCover = artCover;
    }

    /**
     * @return art_top
     */
    public Integer getArtTop() {
        return artTop;
    }

    /**
     * @param artTop
     */
    public void setArtTop(Integer artTop) {
        this.artTop = artTop;
    }

    /**
     * @return art_times
     */
    public Integer getArtTimes() {
        return artTimes;
    }

    /**
     * @param artTimes
     */
    public void setArtTimes(Integer artTimes) {
        this.artTimes = artTimes;
    }

    /**
     * @return art_enclosure
     */
    public String getArtEnclosure() {
        return artEnclosure;
    }

    /**
     * @param artEnclosure
     */
    public void setArtEnclosure(String artEnclosure) {
        this.artEnclosure = artEnclosure;
    }

    /**
     * @return art_content
     */
    public String getArtContent() {
        return artContent;
    }

    /**
     * @param artContent
     */
    public void setArtContent(String artContent) {
        this.artContent = artContent;
    }

	@Override
	public String toString() {
		return "Article [artId=" + artId + ", artType=" + artType + ", artTitle=" + artTitle + ", artDate=" + artDate
				+ ", artUser=" + artUser + ", artCover=" + artCover + ", artTop=" + artTop + ", artTimes=" + artTimes
				+ ", artEnclosure=" + artEnclosure + ", artContent=" + artContent + ", articleType=" + articleType
				+ ", user=" + user + "]";
	}
}