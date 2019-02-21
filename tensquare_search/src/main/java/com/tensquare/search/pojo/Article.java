package com.tensquare.search.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * 文章实体
 */

@Data
@Document(indexName = "tensquare_article", type = "article")
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    private String id;

    /**
     * 标题
     */
    @Field(index = true,searchAnalyzer = "id_max_word",analyzer = "ik_max_word")
    private String title;


    /**
     * 内容
     */
    @Field(index = true,searchAnalyzer = "id_max_word",analyzer = "ik_max_word")
    private String content;

    /**
     * 审核状态
     */
    private String status;
}
