package top.fallingintodreams.flying.bicycle.backend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderTrendVO {

    private String date;
    private Integer totalCount;

}
