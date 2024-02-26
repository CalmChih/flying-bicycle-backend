package top.fallingintodreams.flying.bicycle.backend.dao;

import org.apache.ibatis.annotations.Mapper;
import top.fallingintodreams.flying.bicycle.backend.po.User;

/**
 * 用户信息表Dao
 *
 * @author Chih
 * @date 2024/2/25 19:35
 */
@Mapper
public interface IUserDao {
    /**
     * 插入用户信息
     * @param user 用户信息
     * @return 主键id
     */
    void insertUserInfo(User user);

}