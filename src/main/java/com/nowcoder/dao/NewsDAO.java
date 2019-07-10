package com.nowcoder.dao;

import com.nowcoder.model.News;
import com.nowcoder.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author ：xingxiangdong
 * @Date :2019/7/921:20
 */
@Mapper
public interface NewsDAO {
    String TABLE_NAME="news";
    String INSERT_FIELDS ="title,link,image,like_count,comment_count,created_date,user_id";
    String SELECT_FIELDS="id "+INSERT_FIELDS;

    //    @Insert({"insert into user(name,password,slat,head_url) values()"})
    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,
            ") values (#{title},#{link},#{image},#{likeCount},#{commentCount},#{createdDate},#{userId})"})
    int addNews(News news);


    List<News> selectByUserIdAndOffset(@Param("userId")int userId,@Param("offset")int offset,
                                       @Param("limit")int limit);

    @Update({"update ",TABLE_NAME," set password=#{password} where id = #{id}" })
    void upadatePassword(News news);

    @Delete({"delete from ",TABLE_NAME," where id=#{id}"})
    void deleteById(int id);
}
