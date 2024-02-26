package top.fallingintodreams.flying.bicycle.backend.dao;

import org.apache.ibatis.annotations.Mapper;
import top.fallingintodreams.flying.bicycle.backend.dto.UserAccountDTO;
import top.fallingintodreams.flying.bicycle.backend.po.UserAccount;

/**
 * 用户账户表Dao
 *
 * @author Chih
 * @date 2024/2/25 19:36
 */
@Mapper
public interface IUserAccountDao {
    /**
     * 插入用户账户信息
     * @param userAccount 用户账户信息
     */
    void insertUserAccount(UserAccount userAccount);

    /**
     * 获取账户id
     * @param userAccountDTO 登录信息
     * @return 账户id
     */
    Long getUserAccountByPassword(UserAccountDTO userAccountDTO);

    /**
     * 是否存在账户
     * @param userAccountDTO 登录信息
     * @return
     */
    Long getUserAccount(UserAccountDTO userAccountDTO);
}
