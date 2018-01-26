package cn.fjlcx.application.mapper;

import java.util.List;
import java.util.Map;

import cn.fjlcx.application.bean.Article;
import cn.fjlcx.application.core.Mapper;

public interface ArticleMapper extends Mapper<Article> {
	List<Article> selectArticle();
	List<Article> selectArticleByCondition(Map<String,Object> params);
	Article selectArticlebyId(int id);
}