package top.fallingintodreams.flying.bicycle.backend.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * 管理员Dao
 */
@Mapper
public interface IManagerDao {

    /**
     * 获取密码
     * @param username 账号
     */
    String getPassword(String username);

}
