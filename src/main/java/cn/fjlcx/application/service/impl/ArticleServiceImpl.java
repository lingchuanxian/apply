package cn.fjlcx.application.service.impl;

import cn.fjlcx.application.mapper.ArticleMapper;
import cn.fjlcx.application.bean.Article;
import cn.fjlcx.application.service.ArticleService;
import cn.fjlcx.application.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 *  @author ling_cx 
 *  @date   2017/11/30.
 */
@Service
@Transactional
public class ArticleServiceImpl extends AbstractService<Article> implements ArticleService {
    @Resource
    private ArticleMapper oaArticleMapper;

	@Override
	public List<Article> selectArticle() {
		return oaArticleMapper.selectArticle();
	}

	@Override
	public List<Article> selectArticleByCondition(Map<String, Object> params) {
		return oaArticleMapper.selectArticleByCondition(params);
	}

	@Override
	public Article selectArticlebyId(int id) {
		return oaArticleMapper.selectArticlebyId(id);
	}

}
