package mybatis.service;


import mybatis.domain.UserInfo;

import java.util.List;

public interface UserService {

    /**
     * findAll
     *
     * @return
     */
    List<UserInfo> findAll();

    UserInfo findOne(int id);
}
