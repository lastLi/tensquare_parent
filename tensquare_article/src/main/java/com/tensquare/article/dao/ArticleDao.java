package com.tensquare.article.dao;

import com.tensquare.article.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ArticleDao extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {

    /**
     * <p>文章审核</P>
     * <p>
     * 审核状态:1代表已经审核,0代表未审核
     *
     * @param id 审核对象的编号
     */
    @Modifying
    @Query(value = "UPDATE tensquare_article.tb_article SET state=1 WHERE id=#{id}", nativeQuery = true)
    void updateState(String id);

    /**
     * <p>文章点赞</p>
     * <p>
     * 每点赞一次+1
     *
     * @param id 该文章的编号
     */
    @Modifying
    @Query(value = "UPDATE tensquare_article.tb_article SET thumbup=IFNULL(thumbup,0)+1 WHERE id=#{id}",
            nativeQuery = true)
    void addThumbup(String id);
}
