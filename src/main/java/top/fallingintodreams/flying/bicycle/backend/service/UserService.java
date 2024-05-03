package top.fallingintodreams.flying.bicycle.backend.service;

import top.fallingintodreams.flying.bicycle.backend.dto.UserAccountDTO;
import top.fallingintodreams.flying.bicycle.backend.po.User;

import java.util.Map;

/**
 *
 * 用户信息 服务接口类
 *
 * @author Chih
 * @date 2024/2/25 19:55
 */
public interface UserService {
    /**
     * 插入必要的用户信息，注册时使用
     *
     * @param userAccountDTO 用户账户信息
     */
    void registerAccount(UserAccountDTO userAccountDTO);

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    User getUserInfo(String username);

    void updateUserInfo(User user);

    Map<String, Object> getUserList(Map<String, Object> searchMap);

    void updateUserState(Map<String, Object> searchMap);
}
