package top.fallingintodreams.flying.bicycle.backend.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.UUID;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.fallingintodreams.flying.bicycle.backend.common.ApiResponse;
import top.fallingintodreams.flying.bicycle.backend.dto.UserAccountDTO;
import top.fallingintodreams.flying.bicycle.backend.po.User;
import top.fallingintodreams.flying.bicycle.backend.service.UserAccountService;
import top.fallingintodreams.flying.bicycle.backend.service.UserService;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private UserAccountService userAccountService;

    @PostMapping("/uploadAvatar")
    public ApiResponse uploadAvatar(@RequestParam MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + ext;
        File localFile = new File("D:/bishe/flying-bicycle/src/assets/images/" + newFileName);
        try {
            file.transferTo(localFile);
        } catch (IOException e) {
            return ApiResponse.error("上传失败");
        }
        return ApiResponse.success("http://localhost:8002/image/avatar/"+newFileName, "上传成功");
    }

    @GetMapping("getUserInfo")
    public ApiResponse getUserInfo() {
        String username = StpUtil.getLoginIdAsString();
        User userInfo = userService.getUserInfo(username);
        return ApiResponse.success(userInfo);
    }

    @PostMapping("updateUserInfo")
    public ApiResponse updateUserInfo(@RequestBody User user) {
        userService.updateUserInfo(user);
        return ApiResponse.success("更新成功");
    }

    @PostMapping("updatePassword")
    public ApiResponse updatePassword(@RequestBody UserAccountDTO userAccountDTO) {
        userAccountService.updatePassword(userAccountDTO);
        return ApiResponse.success("更新成功");
    }
}
