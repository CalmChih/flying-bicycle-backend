package top.fallingintodreams.flying.bicycle.backend.service.impl;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;
import top.fallingintodreams.flying.bicycle.backend.common.Constant;
import top.fallingintodreams.flying.bicycle.backend.dao.IUserAccountDao;
import top.fallingintodreams.flying.bicycle.backend.dto.UserAccountDTO;
import top.fallingintodreams.flying.bicycle.backend.handler.enums.impl.LoginEnum;
import top.fallingintodreams.flying.bicycle.backend.handler.exception.LoginException;
import top.fallingintodreams.flying.bicycle.backend.po.UserAccount;
import top.fallingintodreams.flying.bicycle.backend.service.UserAccountService;
import top.fallingintodreams.flying.bicycle.backend.service.UserService;
import top.fallingintodreams.flying.bicycle.backend.util.RedisUtil;
import top.fallingintodreams.flying.bicycle.backend.util.RegexUtil;

import java.util.Objects;

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
    public void loginWithPassword(UserAccountDTO userAccountDTO) {
        // 账号或密码为空
        if (StringUtils.isBlank(userAccountDTO.getUsername()) || StringUtils.isBlank(userAccountDTO.getPassword())) {
            throw new LoginException(LoginEnum.WRONG_ACCOUNT_NUMBER_OR_PASSWORD);
        }
        UserAccount userAccount = userAccountDao.getPassword(userAccountDTO);
        if (Objects.isNull(userAccount)) {
            throw new LoginException(LoginEnum.USER_ACCOUNT_NOT_EXIST);
        }
        if (2 == userAccount.getState()) {
            throw new LoginException(LoginEnum.ACCOUNT_HAS_BENN_BINNED);
        }
        boolean flag = BCrypt.checkpw(userAccountDTO.getPassword(), userAccount.getPassword());
        if (!flag) {
            throw new LoginException(LoginEnum.WRONG_ACCOUNT_NUMBER_OR_PASSWORD);
        }
    }

    @Override
    public void loginWithCaptcha(UserAccountDTO userAccountDTO) {
        checkCaptcha(userAccountDTO);
        UserAccount userAccount = userAccountDao.getPassword(userAccountDTO);
        if (Objects.isNull(userAccount)) {
            userService.registerAccount(userAccountDTO);
        }
        redisUtil.del(Constant.RedisKey.CAPTCHA_KEY_PREFIX(userAccountDTO.getUsername()));
    }

    @Override
    public void updatePassword(UserAccountDTO userAccountDTO) {
        checkCaptcha(userAccountDTO);
        userAccountDTO.setPassword(BCrypt.hashpw(userAccountDTO.getPassword(), BCrypt.gensalt()));
        userAccountDao.updatePassword(userAccountDTO);
        redisUtil.del(Constant.RedisKey.CAPTCHA_KEY_PREFIX(userAccountDTO.getUsername()));
    }

    private void checkCaptcha(UserAccountDTO userAccountDTO) {
        // 验证码格式错误
        if (StringUtils.isNotBlank(userAccountDTO.getCaptcha())
                && !RegexUtil.matchCaptcha(userAccountDTO.getCaptcha())) {
            throw new LoginException(LoginEnum.CAPTCHA_ERROR);
        }
        String captcha = (String) redisUtil.get(Constant.RedisKey.CAPTCHA_KEY_PREFIX(userAccountDTO.getUsername()));
        // 验证码不匹配
        if (!userAccountDTO.getCaptcha().equals(captcha)) {
            throw new LoginException(LoginEnum.CAPTCHA_ERROR);
        }
    }
}
