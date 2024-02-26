package top.fallingintodreams.flying.bicycle.backend.service.impl;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import top.fallingintodreams.flying.bicycle.backend.common.Constans;
import top.fallingintodreams.flying.bicycle.backend.dao.IUserAccountDao;
import top.fallingintodreams.flying.bicycle.backend.dto.UserAccountDTO;
import top.fallingintodreams.flying.bicycle.backend.service.UserAccountService;
import top.fallingintodreams.flying.bicycle.backend.service.UserService;
import top.fallingintodreams.flying.bicycle.backend.util.RedisUtil;

/**
 * 用户账户 服务实现类
 *
 * @author Chih
 * @date 2024/2/25 20:26
 */
@Repository
public class UserAccountImpl implements UserAccountService {

    @Resource
    private IUserAccountDao userAccountDao;
    @Resource
    private UserService userService;
    @Resource
    private RedisUtil redisUtil;


    @Override
    public boolean loginWithPassword(UserAccountDTO userAccountDTO) {
        if (StringUtils.isNotBlank(userAccountDTO.getPassword())) {
            Long accountId = userAccountDao.getUserAccountByPassword(userAccountDTO);
            return accountId != null;
        }
        return false;
    }

    @Override
    public boolean loginWithCaptcha(UserAccountDTO userAccountDTO) {
        String captcha = (String) redisUtil.get(Constans.RedisKey.CAPTCHA_KEY_PREFIX(userAccountDTO.getUsername()));
        if (userAccountDTO.getCaptcha().equals(captcha)) {
            Long accountId = userAccountDao.getUserAccount(userAccountDTO);
            if (accountId == null) {
                userService.registerAccount(userAccountDTO);
            }
            return true;
        }
        return false;
    }
}
