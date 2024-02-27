package top.fallingintodreams.flying.bicycle.backend.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import top.fallingintodreams.flying.bicycle.backend.common.ApiResponse;
import top.fallingintodreams.flying.bicycle.backend.common.Constans;
import top.fallingintodreams.flying.bicycle.backend.common.EmailService;
import top.fallingintodreams.flying.bicycle.backend.dto.UserAccountDTO;
import top.fallingintodreams.flying.bicycle.backend.handler.enums.impl.LoginEnum;
import top.fallingintodreams.flying.bicycle.backend.handler.exception.LoginException;
import top.fallingintodreams.flying.bicycle.backend.service.UserAccountService;
import top.fallingintodreams.flying.bicycle.backend.service.UserService;
import top.fallingintodreams.flying.bicycle.backend.util.RedisUtil;
import top.fallingintodreams.flying.bicycle.backend.util.RegexUtils;

/**
 * 用户登录注册Controller
 *
 * @author Chih
 * @date 2024/2/25 20:27
 */
@RestController
@Slf4j
public class LoginController {

    @Resource
    private UserService userService;
    @Resource
    private UserAccountService userAccountService;
    @Resource
    private EmailService emailService;
    @Resource
    private RedisUtil redisUtil;



    @PostMapping("/login")
    @Transactional(rollbackFor = RuntimeException.class)
    public ApiResponse registerAndLogin(@RequestBody UserAccountDTO userAccountDTO) {
        Long accountId = null;
        if (userAccountDTO.getType() == 1) {
            accountId = userAccountService.loginWithPassword(userAccountDTO);
        } else {
            accountId = userAccountService.loginWithCaptcha(userAccountDTO);
        }
        StpUtil.login(accountId);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return ApiResponse.success(tokenInfo, "登录成功");
    }

    @GetMapping("/sendCaptcha")
    public ApiResponse senCaptcha(@RequestParam String emailAccount) {
        if (StringUtils.isBlank(emailAccount) || !RegexUtils.matchEmail(emailAccount)) {
            throw new LoginException(LoginEnum.MAIL_FORMAT_ERROR);
        }
        String captcha = RandomStringUtils.randomNumeric(6);
        boolean isSuccess = redisUtil.set(Constans.RedisKey.CAPTCHA_KEY_PREFIX(emailAccount), captcha, 60 * 3L);
        if (!isSuccess) {
            throw new LoginException(LoginEnum.CAPTCHA_SEND_ERROR);
        }
        emailService.sendEmail(emailAccount, "飞驰自行车", String.format("您的验证码为：%s，3分钟内有效。", captcha));
        return ApiResponse.success("验证码发送成功：" + captcha);
    }

    @GetMapping("/isLogin")
    public ApiResponse isLogin() {
        boolean login = StpUtil.isLogin();
        return ApiResponse.success(login ? "已登录" : "未登录");
    }


}
