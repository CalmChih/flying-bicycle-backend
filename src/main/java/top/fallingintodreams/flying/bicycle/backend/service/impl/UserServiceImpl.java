package top.fallingintodreams.flying.bicycle.backend.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import top.fallingintodreams.flying.bicycle.backend.common.Constant;
import top.fallingintodreams.flying.bicycle.backend.dao.IUserAccountDao;
import top.fallingintodreams.flying.bicycle.backend.dao.IUserDao;
import top.fallingintodreams.flying.bicycle.backend.dto.UserAccountDTO;
import top.fallingintodreams.flying.bicycle.backend.dto.UserInfoDTO;
import top.fallingintodreams.flying.bicycle.backend.po.User;
import top.fallingintodreams.flying.bicycle.backend.po.UserAccount;
import top.fallingintodreams.flying.bicycle.backend.service.UserService;
import top.fallingintodreams.flying.bicycle.backend.util.DateConvertUtils;
import top.fallingintodreams.flying.bicycle.backend.vo.UserInfoVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public void registerAccount(UserAccountDTO userAccountDTO) {
        User user = new User();
        user.setNickname(RandomStringUtils.randomAlphanumeric(8));
        userDao.insertUserInfo(user);
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(userAccountDTO.getUsername());
        userAccount.setPassword(BCrypt.hashpw(RandomStringUtils.randomAlphanumeric(12), BCrypt.gensalt()));
        userAccount.setUserId(user.getId());
        userAccountDao.insertUserAccount(userAccount);
    }

    @Override
    public User getUserInfo(String username) {
        return userDao.getUserInfo(username);
    }

    @Override
    public void updateUserInfo(User user) {
        userDao.updateUserInfo(user);
    }

    @Override
    public Map<String, Object> getUserList(Map<String, Object> searchMap) {
        if (searchMap.get("currentPage") != null && searchMap.get("pageSize") != null) {
            int currentPage = (int) searchMap.get("currentPage");
            int pageSize = (int) searchMap.get("pageSize");
            PageHelper.startPage(currentPage, pageSize);
        }
        List<UserInfoVO> userList = userDao.getUserList(searchMap);
        List<UserInfoDTO> userDTOList = userList.stream().map(this::vo2Dto).collect(Collectors.toList());
        PageInfo<UserInfoVO> pageInfo = new PageInfo<>(userList);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", userDTOList);
        resultMap.put("total", pageInfo.getTotal());
        return resultMap;
    }

    private UserInfoDTO vo2Dto(UserInfoVO userInfoVO) {
        if (Objects.isNull(userInfoVO)) {
            return null;
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setId(userInfoVO.getId());
        userInfoDTO.setUsername(userInfoVO.getUsername());
        userInfoDTO.setNickname(userInfoVO.getNickname());
        userInfoDTO.setGender(Constant.GenderEnum.convert(userInfoVO.getGender()));
        userInfoDTO.setBirthday(DateConvertUtils.dateFormatToString(userInfoVO.getBirthday()));
        userInfoDTO.setMobile(userInfoVO.getMobile());
        userInfoDTO.setState(Constant.UserStateEnum.convert(userInfoVO.getState()));
        userInfoDTO.setCreateTime(DateConvertUtils.dateFormatToString(userInfoVO.getCreateTime()));
        return userInfoDTO;
    }

    @Override
    @Transactional
    public void updateUserState(Map<String, Object> searchMap) {
        userDao.updateUserState(searchMap);
        userAccountDao.updateAccountState(searchMap);
    }
}
