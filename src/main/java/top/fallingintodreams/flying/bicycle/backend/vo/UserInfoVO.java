package top.fallingintodreams.flying.bicycle.backend.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfoVO {

    private Long id;
    private String username;
    private String nickname;
    private Integer gender;
    private Date birthday;
    private String mobile;
    private Integer state;
    private Date createTime;

}
