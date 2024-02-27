package top.fallingintodreams.flying.bicycle.backend.service.impl;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;
import top.fallingintodreams.flying.bicycle.backend.common.Constans;
import top.fallingintodreams.flying.bicycle.backend.dao.IUserAccountDao;
import top.fallingintodreams.flying.bicycle.backend.dto.UserAccountDTO;
import top.fallingintodreams.flying.bicycle.backend.handler.enums.impl.LoginEnum;
import top.fallingintodreams.flying.bicycle.backend.handler.exception.LoginException;
import top.fallingintodreams.flying.bicycle.backend.po.UserAccount;
import top.fallingintodreams.flying.bicycle.backend.service.UserAccountService;
import top.fallingintodreams.flying.bicycle.backend.service.UserService;
import top.fallingintodreams.flying.bicycle.backend.util.RedisUtil;
import top.fallingintodreams.flying.bicycle.backend.util.RegexUtils;

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
    public Long loginWithPassword(UserAccountDTO userAccountDTO) {
        // 账号或密码为空
        if (StringUtils.isBlank(userAccountDTO.getUsername()) || StringUtils.isBlank(userAccountDTO.getPassword())) {
            throw new LoginException(LoginEnum.WRONG_ACCOUNT_NUMBER_OR_PASSWORD);
        }
        UserAccount userAccount = userAccountDao.getAccountIdAndPassword(userAccountDTO);
        if (StringUtils.isBlank(userAccount.getPassword())) {
            throw new LoginException(LoginEnum.ACCOUNT_DOES_NOT_EXIST);
        }
        boolean flag = BCrypt.checkpw(userAccountDTO.getPassword(), userAccount.getPassword());
        if (!flag) {
            throw new LoginException(LoginEnum.WRONG_ACCOUNT_NUMBER_OR_PASSWORD);
        }
        return userAccount.getId();
    }

    @Override
    public Long loginWithCaptcha(UserAccountDTO userAccountDTO) {
        // 验证码格式错误
        if (StringUtils.isNotBlank(userAccountDTO.getCaptcha())
                && !RegexUtils.matchCaptcha(userAccountDTO.getCaptcha())) {
            throw new LoginException(LoginEnum.CAPTCHA_ERROR);
        }
        String captcha = (String) redisUtil.get(Constans.RedisKey.CAPTCHA_KEY_PREFIX(userAccountDTO.getUsername()));
        // 验证码不匹配
        if (!userAccountDTO.getCaptcha().equals(captcha)) {
            throw new LoginException(LoginEnum.CAPTCHA_ERROR);
        }
        Long accountId = userAccountDao.getUserAccount(userAccountDTO);
        if (accountId == null) {
            accountId = userService.registerAccount(userAccountDTO);
        }
        redisUtil.del(Constans.RedisKey.CAPTCHA_KEY_PREFIX(userAccountDTO.getUsername()));
        return accountId;
    }
}
