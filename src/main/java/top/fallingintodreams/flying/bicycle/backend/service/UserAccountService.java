package top.fallingintodreams.flying.bicycle.backend.service;

import top.fallingintodreams.flying.bicycle.backend.dto.UserAccountDTO;

/**
 * 用户账户 服务接口类
 *
 * @author Chih
 * @date 2024/2/25 20:24
 */
public interface UserAccountService {

    /**
     * 是否存在账户
     *
     * @param userAccountDTO 用户账户信息
     * @return
     */
    Long loginWithPassword(UserAccountDTO userAccountDTO);

    /**
     * 验证码登录
     *
     * @param userAccountDTO
     * @return
     */
    Long loginWithCaptcha(UserAccountDTO userAccountDTO);

}
