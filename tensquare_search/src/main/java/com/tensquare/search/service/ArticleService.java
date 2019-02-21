package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleDao;
import com.tensquare.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import util.IdWorker;
import util.MyPageQuery;

import javax.annotation.Resource;

@Service
public class ArticleService {
    @Resource
    private ArticleDao articleDao;

    @Resource
    private IdWorker idWorker;

    @Resource
    private MyPageQuery myPageQuery;

    /**
     * 添加一个文章索引
     *
     * @param article 文章
     */
    public void saveArticle(Article article) {
        article.setId(idWorker.nextId() + "");
        articleDao.save(article);
    }

    /**
     *  根据索引查询并且分页
     * @param key 索引key
     * @param page 开始页
     * @param size 每页的总记录
     * @return 分页结果
     */
    public Page<Article> findByKey(String key, int page, int size) {
        Pageable pageable = myPageQuery.pageQuery(page, size);
        return articleDao.findByTitleOrContentLike(key, key, pageable);

    }
}
