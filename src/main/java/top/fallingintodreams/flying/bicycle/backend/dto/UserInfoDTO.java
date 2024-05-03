package top.fallingintodreams.flying.bicycle.backend.dto;

import lombok.Data;

@Data
public class UserInfoDTO {

    private Long id;
    private String username;
    private String nickname;
    private String gender;
    private String birthday;
    private String mobile;
    private String state;
    private String createTime;

}
