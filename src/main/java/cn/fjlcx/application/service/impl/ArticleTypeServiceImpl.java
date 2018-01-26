package cn.fjlcx.application.service.impl;

import cn.fjlcx.application.mapper.ArticleTypeMapper;
import cn.fjlcx.application.bean.ArticleType;
import cn.fjlcx.application.service.ArticleTypeService;
import cn.fjlcx.application.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 *  @author ling_cx 
 *  @date   2017/11/30.
 */
@Service
@Transactional
public class ArticleTypeServiceImpl extends AbstractService<ArticleType> implements ArticleTypeService {
    @Resource
    private ArticleTypeMapper oaArticleTypeMapper;

}
