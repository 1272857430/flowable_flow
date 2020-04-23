package cn.flow.server.service_api;

import cn.flow.api.api.FormApi;
import cn.flow.api.request.form.GetFormDataRequestBody;
import cn.flow.api.request.form.GetRenderedStartFormRequestBody;
import cn.flow.api.request.form.GetRenderedTaskFormRequestBody;
import cn.flow.api.request.form.GetStartFormModelRequestBody;
import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class FormApiController implements FormApi {

    @Autowired
    private FormApiService formApiService;

    @Override
    public Result<FormModelResponseBody> getStartFormModel(GetStartFormModelRequestBody requestBody) {
        return null;
    }

    @Override
    public Result<FormModelResponseBody> getRenderedStartForm(GetRenderedStartFormRequestBody requestBody) {
        return null;
    }

    @Override
    public Result<FormModelResponseBody> getRenderedTaskForm(GetRenderedTaskFormRequestBody requestBody) {
        return null;
    }

    @Override
    @RequestMapping(value = "/getFormModelByKey/{formKey}",method = RequestMethod.GET)
    public Result<FormModelResponseBody> getFormModelByKey(@PathVariable(value = "formKey") String formKey) {
        return formApiService.getFormModelByKey(formKey);
    }

    @Override
    @RequestMapping(value = "/getTaskFormData",method = RequestMethod.POST)
    public Result getTaskFormData(@RequestBody GetFormDataRequestBody requestBody) {
        return formApiService.getTaskFormData(requestBody);
    }
}
