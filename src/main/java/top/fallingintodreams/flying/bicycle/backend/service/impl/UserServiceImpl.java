package top.fallingintodreams.flying.bicycle.backend.service.impl;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import top.fallingintodreams.flying.bicycle.backend.common.Constans;
import top.fallingintodreams.flying.bicycle.backend.dao.IUserAccountDao;
import top.fallingintodreams.flying.bicycle.backend.dao.IUserDao;
import top.fallingintodreams.flying.bicycle.backend.dto.UserAccountDTO;
import top.fallingintodreams.flying.bicycle.backend.po.User;
import top.fallingintodreams.flying.bicycle.backend.po.UserAccount;
import top.fallingintodreams.flying.bicycle.backend.service.UserService;

/**
 * 用户信息 服务实现类
 *
 * @author Chih
 * @date 2024/2/25 19:56
 */
@Repository
public class UserServiceImpl implements UserService {

    @Resource
    private IUserDao userDao;
    @Resource
    private IUserAccountDao userAccountDao;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Long registerAccount(UserAccountDTO userAccountDTO) {
        User user = new User();
        user.setNickname(RandomStringUtils.randomAlphanumeric(8));
        user.setState(Constans.UserState.NORMAL);
        userDao.insertUserInfo(user);
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(userAccountDTO.getUsername());
        userAccount.setPassword(BCrypt.hashpw(RandomStringUtils.randomAlphanumeric(12), BCrypt.gensalt()));
        userAccount.setUserId(user.getId());
        userAccount.setState(Constans.UserState.NORMAL);
        userAccountDao.insertUserAccount(userAccount);
        return user.getId();
    }
}
