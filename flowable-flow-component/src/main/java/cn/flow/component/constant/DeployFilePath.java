package cn.flow.component.constant;

import java.io.File;

public class DeployFilePath {

    // 部署所有表单路径
    private static final String ALL_FORM_FILE_PATH = "src/test/resources/forms";
    public static final String allFormFileAbsolutePath;

    // 流程form表单路径
    private static final String FORM_FILE_PATH = "flowable-flow-server/src/test/resources/forms";
    public static final String formAbsolutePath;
    // 流程xml文件路径
    private static final String XML_FILE_PATH = "flowable-flow-server/src/test/resources/processes";
    public static final String xmlAbsolutePath;

    // 自动生成测试用例模版路径
    private static final String TEMPLATE_FILE__PATH = "flowable-flow-server/src/test/java/cn/sayyoo/workflow/ftl";
    public static final String templateFileAbsolutePath;
    // 自动测试用例java文件路径
    private static final String CLASS_FILE_PATH = "flowable-flow-server/src/test/java/cn/sayyoo/workflow/testCase";
    public static final String classFileAbsolutePath;

    static {
        File allFormDir = new File(ALL_FORM_FILE_PATH);
        if (!allFormDir.exists()) {
            System.out.println("文件夹不存在：" + ALL_FORM_FILE_PATH) ;
        }
        allFormFileAbsolutePath = allFormDir.getAbsolutePath();
        System.out.println("allFormFileAbsolutePath: " + allFormFileAbsolutePath);

        File formDir = new File(FORM_FILE_PATH);
        if (!formDir.exists()) {
            System.out.println("文件夹不存在：" + FORM_FILE_PATH) ;
        }
        formAbsolutePath = formDir.getAbsolutePath();
        System.out.println("formAbsolutePath: " + formAbsolutePath);

        File xmlDir = new File(XML_FILE_PATH);
        if (!xmlDir.exists()) {
            System.out.println("文件夹不存在：" + XML_FILE_PATH) ;
        }
        xmlAbsolutePath = xmlDir.getAbsolutePath();
        System.out.println("xmlAbsolutePath: " + xmlAbsolutePath);


        File modellDir = new File(TEMPLATE_FILE__PATH);
        if (!modellDir.exists()) {
            System.out.println("文件夹不存在：" + TEMPLATE_FILE__PATH) ;
        }
        templateFileAbsolutePath = modellDir.getAbsolutePath();
        System.out.println("templateFileAbsolutePath: " + templateFileAbsolutePath);

        File classDir = new File(CLASS_FILE_PATH);
        if (!classDir.exists()) {
            System.out.println("文件夹不存在：" + CLASS_FILE_PATH) ;
        }
        classFileAbsolutePath = classDir.getAbsolutePath();
        System.out.println("classFileAbsolutPath: " + classFileAbsolutePath);

    }
}
