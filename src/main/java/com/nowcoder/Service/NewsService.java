package com.nowcoder.Service;

import com.nowcoder.dao.NewsDAO;
import com.nowcoder.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ：xingxiangdong
 * @Date :2019/7/921:41
 */
@Service
public class NewsService {
    @Autowired
    private NewsDAO newsDAO;

    public List<News> getLatesNews(int userId,int offset,int limit){
        return newsDAO.selectByUserIdAndOffset(userId,offset,limit);
    }
}
