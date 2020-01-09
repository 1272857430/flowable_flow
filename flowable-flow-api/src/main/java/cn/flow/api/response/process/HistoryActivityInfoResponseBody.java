package cn.flow.api.response.process;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Data
public class HistoryActivityInfoResponseBody implements Comparable{
    String id;
    /** The unique identifier of the activity in the process */
    String activityId;
    /** The display name for the activity */
    String activityName;
    String activityNodeName;
    /** The XML tag of the activity as in the process file */
    String activityType;
    /** Process definition reference */
    String processDefinitionId;
    /** Process instance reference */
    String processInstanceId;
    /** Execution reference */
    String executionId;
    /** The corresponding task in case of task activity */
    String taskId;
    /** The called process instance in case of call activity */
    String calledProcessInstanceId;
    /** Assignee in case of user task activity */
    String assignee;
    /** Time when the activity instance started */
    Date startTime;
    /** Time when the activity instance ended */
    Date endTime;
    Long durationInMillis;
    /** Returns the delete reason for this activity, if any was set (if completed normally, no delete reason is set) */
    String deleteReason;

    String processScopeId;

    Boolean hasDetails;

    List<GroupModel> currentGroupInfoList;

    @Override
    public int compareTo(Object o) {
       if (o == null)
           return 1;
        HistoryActivityInfoResponseBody historyActivityInfoResponseBody = (HistoryActivityInfoResponseBody) o;
       if (this.endTime.after(historyActivityInfoResponseBody.endTime))
           return 1;
        if (this.endTime.before(historyActivityInfoResponseBody.endTime))
            return 0;
       return -1;
    }
}
