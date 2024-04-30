package top.fallingintodreams.flying.bicycle.backend.po;

import lombok.Data;

import java.util.Date;

@Data
public class Manager {

    private Integer id;
    private String username;
    private String password;
    private Date createTime;
    private Date updateTime;

}
