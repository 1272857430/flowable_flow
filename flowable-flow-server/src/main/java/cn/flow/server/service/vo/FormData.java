package cn.flow.server.service.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class FormData {

    Map<String,Object> values = new HashMap<>();
}
