package com.nowcoder.Service;

import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author ：xingxiangdong
 * @Date :2019/7/921:17
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDao;

    public User getUser(int id){
        return userDao.selectById(id);
    }
}
