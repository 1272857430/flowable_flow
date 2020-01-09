package cn.flow.api.request;

import lombok.Data;

@Data
public class PageRequestBody {
    private Integer pageNo;
    private Integer pageSize;
}
