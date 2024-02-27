package top.fallingintodreams.flying.bicycle.backend.service;

import top.fallingintodreams.flying.bicycle.backend.dto.UserAccountDTO;

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
     * @return
     */
    Long registerAccount(UserAccountDTO userAccountDTO);
}
