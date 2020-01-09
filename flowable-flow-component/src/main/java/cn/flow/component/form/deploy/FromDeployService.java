package cn.flow.component.form.deploy;

import cn.flow.component.constant.DeployFilePath;
import cn.flow.component.form.TranslateFieldAnnotation;
import cn.flow.component.utils.JsonFormater;
import com.alibaba.fastjson.JSON;
import org.flowable.form.api.FormRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class FromDeployService {
    private static final Logger logger = LoggerFactory.getLogger(FromDeployService.class);

    @Autowired
    private FormRepositoryService formRepositoryService;

    public void deployFrom(Class formClass) {
        if (formClass == null)
            return;

        logger.info("============开始部署表单============");

        Map<String, Object> formData = TranslateFieldAnnotation.translateFormData(formClass);

        deployFrom(formData);
    }

    private void deployFrom(Map<String, Object> formData) {

        String fromKey = String.valueOf(formData.get("key"));

        String resourceName = DeployFilePath.formAbsolutePath + "/" + fromKey + ".form";

        createDeployFile(formData);

        formRepositoryService.createDeployment()
                .name(resourceName)
                .addFormBytes(resourceName, JSON.toJSONBytes(formData))
                .deploy();
    }

    private void createDeployFile(Map<String, Object> formData){
        try {
            String fileName = new String((DeployFilePath.formAbsolutePath + "/" + formData.get("key") + ".json").getBytes(), StandardCharsets.UTF_8);
            String content = JsonFormater.format(JSON.toJSONString(formData));
            File file = new File(fileName);

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

            assert content != null;
            bufferedWriter.write(content);
            bufferedWriter.newLine();

            bufferedWriter.close();
            fileOutputStream.close();

        } catch (Exception ex) {
            logger.error("生成form文件出错！");
        }
    }
}
