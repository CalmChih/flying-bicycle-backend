package top.fallingintodreams.flying.bicycle.backend.service.impl;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import top.fallingintodreams.flying.bicycle.backend.dao.IManagerDao;
import top.fallingintodreams.flying.bicycle.backend.handler.enums.impl.LoginEnum;
import top.fallingintodreams.flying.bicycle.backend.handler.exception.LoginException;
import top.fallingintodreams.flying.bicycle.backend.po.Manager;
import top.fallingintodreams.flying.bicycle.backend.service.ManagerService;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Resource
    private IManagerDao managerDao;

    @Override
    public void adminLogin(Manager manager) {
        if (StringUtils.isBlank(manager.getUsername()) || StringUtils.isBlank(manager.getPassword())) {
            throw new LoginException(LoginEnum.ACCOUNT_OR_PASSWORD_IS_BLANK);
        }
        String password = managerDao.getPassword(manager.getUsername());
        if (StringUtils.isBlank(password)) {
            throw new LoginException(LoginEnum.ADMIN_ACCOUNT_NOT_EXITS);
        }
        boolean flag = BCrypt.checkpw(manager.getPassword(), password);
        if (!flag) {
            throw new LoginException(LoginEnum.WRONG_ACCOUNT_NUMBER_OR_PASSWORD);
        }
    }
}
