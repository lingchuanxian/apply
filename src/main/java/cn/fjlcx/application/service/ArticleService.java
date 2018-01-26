package cn.fjlcx.application.service;
import java.util.List;
import java.util.Map;

import cn.fjlcx.application.bean.Article;
import cn.fjlcx.application.core.Service;

/**
 *  @author ling_cx 
 *  @date   2017/11/30.
 */
public interface ArticleService extends Service<Article> {
	/**
	 * 查询全部文章
	 * @return
	 */
	List<Article> selectArticle();
	/**
	 * 根据条件查询文章
	 * @param params
	 * @return
	 */
	List<Article> selectArticleByCondition(Map<String,Object> params);
	/**
	 * 根据id查询文章
	 * @param id
	 * @return
	 */
	Article selectArticlebyId(int id);
}
