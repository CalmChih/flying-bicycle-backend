package top.fallingintodreams.flying.bicycle.backend.dao;

import org.apache.ibatis.annotations.Mapper;
import top.fallingintodreams.flying.bicycle.backend.po.User;
import top.fallingintodreams.flying.bicycle.backend.vo.UserInfoVO;

import java.util.List;
import java.util.Map;

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

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    User getUserInfo(String username);

    void updateUserInfo(User user);

    List<UserInfoVO> getUserList(Map<String, Object> searchMap);

    void updateUserState(Map<String, Object> searchMap);

}