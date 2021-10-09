package com.len.freemarker;

import freemarker.cache.TemplateLoader;
import freemarker.core.Environment;
import freemarker.core._MiscTemplateException;
import freemarker.template.*;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * LenInclude 标签 引入文件并对需要引入文件中的变量进行赋值操作
 * 引入add.ftl文件
 * demo:<@lenIncloud path="system/common/add.ftl" hasPermission = "user:select" name="新增" />
 * <p>
 * add.ftl 内容：
 * <@shiro.hasPermission name="${hasPermission}">
 * <button class="layui-btn layui-btn-normal  layui-btn-sm" data-type="add">
 * <i class="layui-icon">&#xe608;</i>${name}
 * </button>
 * </@shiro.hasPermission>
 * <p>
 * 必填变量是 path：引入文件路径
 * 可自定义变量信息 如以上demo所示  hasPermission、name 为自定义变量
 */
public class LenInclude implements TemplateDirectiveModel {

    private static final String PATH = "path";

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        TemplateLoader templateLoader = environment.getConfiguration().getTemplateLoader();
        String templatePath = templatePath(environment, map, PATH);
        DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_29);
        if (templateLoader.findTemplateSource(templatePath) == null) {
            throw new _MiscTemplateException(String.format("未找到模板文件(Template file not found)[%s]", templatePath));
        }
        HashMap paramMap = new HashMap<String, Object>(map);
        paramMap.remove(PATH);
        for (Object key : paramMap.keySet()) {
            TemplateModel templateModel = builder.build().wrap(map.get(key));
            environment.setVariable(key.toString(), templateModel);
        }
        Template templateForInclusion = environment.getTemplateForInclusion(templatePath, null, true);
        environment.include(templateForInclusion);

        if (templateDirectiveBody != null) {
            templateDirectiveBody.render(environment.getOut());
        }
    }

    private String templatePath(Environment environment, Map params, String templatePath)
            throws MalformedTemplateNameException {
        if (!params.containsKey(templatePath)) {
            throw new MalformedTemplateNameException("missing required parameter '" + templatePath, "'");
        }

        String currentTemplateName = environment.getMainTemplate().getName();
        final String baseName = FilenameUtils.getPath(currentTemplateName);

        final String targetName = params.get(templatePath).toString();

        return environment.toFullTemplateName(baseName, targetName);
    }
}
