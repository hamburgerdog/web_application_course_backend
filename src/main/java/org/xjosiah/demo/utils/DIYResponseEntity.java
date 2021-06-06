package org.xjosiah.demo.utils;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DIYResponseEntity {
    private String code;
    private Object data;
    private String msg;

    public static String DIYResponse(String code, Object data, String message) {
        return JSONUtil.toJsonStr(new DIYResponseEntity(code, data, message));
    }
}
