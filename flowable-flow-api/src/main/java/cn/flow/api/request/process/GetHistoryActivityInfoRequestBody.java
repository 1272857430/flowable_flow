package cn.flow.api.request.process;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class GetHistoryActivityInfoRequestBody {

    private String processInstanceId;
}
