package top.fallingintodreams.flying.bicycle.backend.controller;

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
        boolean contains = false;
        try {
            if (userAccountDTO.getType() == 1) {
                contains = userAccountService.loginWithPassword(userAccountDTO);
            } else {
                if (StringUtils.isNotBlank(userAccountDTO.getCaptcha())
                        && !RegexUtils.matchCaptcha(userAccountDTO.getCaptcha())) {
                    return ApiResponse.error("验证码格式错误");
                }
                contains = userAccountService.loginWithCaptcha(userAccountDTO);
            }
            return contains ? ApiResponse.success("登录成功") : ApiResponse.error("登录失败");
        } catch (Exception e) {
            log.error("登录失败", e);
            return ApiResponse.error("登录失败，系统异常");
        }
    }

    @GetMapping("/sendCaptcha")
    public ApiResponse senCaptcha(@RequestParam String emailAccount) {
        if (!RegexUtils.matchEmail(emailAccount)) {
            return ApiResponse.error("邮箱格式错误！");
        }
        String captcha = RandomStringUtils.randomNumeric(6);
        try {
            boolean isSuccess = redisUtil.set(Constans.RedisKey.CAPTCHA_KEY_PREFIX(emailAccount), captcha, 60 * 3L);
            if (!isSuccess) {
                return ApiResponse.error("服务器异常，请稍后再试！");
            }
            emailService.sendEmail(emailAccount, "飞驰自行车", String.format("您的验证码为：%s，3分钟内有效。", captcha));
        } catch (Exception e) {
            log.error("验证码发送失败", e);
            return ApiResponse.error("验证码发送失败");
        }
        return ApiResponse.success();
    }


}
