package com.mybatisPlugin;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.JavaFormatter;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.ArrayList;
import java.util.List;

public class ServicePlugin  extends PluginAdapter {

    private List<GeneratedJavaFile> mapperJavaFiles = new ArrayList<GeneratedJavaFile>();

    private String targetProject = "./src/main/java";

    @Override
    public boolean validate(List<String> list) {

        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        interfaze.addAnnotation("@Repository");
        FullyQualifiedJavaType repository = new FullyQualifiedJavaType("org.springframework.stereotype.Repository");
        interfaze.addImportedType(repository);
        Interface serviceInterface = generateServiceFile(interfaze);
        generateServiceImplFile(interfaze,serviceInterface);
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {

        return null;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        return mapperJavaFiles;
    }

    /**
     * 生成Service 文件
     * @param interfaze
     * @return
     */
    public Interface generateServiceFile(Interface interfaze){

        Interface serviceInterface = new Interface(
                interfaze.getType().getPackageName().replace("mapper","service") + "." + interfaze.getType().getShortName().replace("Mapper", "Service"));
        for(Method method:interfaze.getMethods()){
            serviceInterface.addMethod(method);
        }
        serviceInterface.setVisibility(JavaVisibility.PUBLIC);
        JavaFormatter javaFormatter = context.getJavaFormatter();
        GeneratedJavaFile mapperJavafile = new GeneratedJavaFile(serviceInterface, targetProject, javaFormatter);
        mapperJavaFiles.add(mapperJavafile);
        return serviceInterface;
    }

    /**
     * 生成ServiceImpl 文件
     * @param interfaze
     * @param serviceInterface
     */
    public void generateServiceImplFile(Interface interfaze,Interface serviceInterface){
        //service impl 类
        JavaFormatter javaFormatter = context.getJavaFormatter();
        FullyQualifiedJavaType implClassType = new FullyQualifiedJavaType(interfaze.getType().getPackageName().replace("mapper","service.impl") + "." + interfaze.getType().getShortName().replace("Mapper", "ServiceImpl"));
        TopLevelClass implClass = new TopLevelClass(implClassType);
        FullyQualifiedJavaType superInterface = new FullyQualifiedJavaType(serviceInterface.getType().getFullyQualifiedName());
        implClass.addSuperInterface(superInterface);
        implClass.addImportedType("javax.annotation.Resource");
        implClass.setVisibility(JavaVisibility.PUBLIC);

        Field mapperField = new Field();
        String shortName = interfaze.getType().getShortName();
        mapperField.setType(interfaze.getType());
        mapperField.setName(shortName.substring(0,1).toLowerCase() + shortName.substring(1));
        mapperField.addAnnotation("@Resource");
        mapperField.setVisibility(JavaVisibility.PRIVATE);
        implClass.addField(mapperField);
        for(Method method:interfaze.getMethods()){
            Method tmp = new Method();
            List<Parameter> parameters =  method.getParameters();
            String parameStr = "";
            for(Parameter parameter:parameters){
                tmp.addParameter(parameter);
                parameStr += "," + parameter.getName();
            }
            parameStr = parameStr.length()>0?parameStr.substring(1):"";
            tmp.setName(method.getName());
            tmp.setReturnType(method.getReturnType());
            tmp.addBodyLine("return "+ mapperField.getName() +"." + method.getName() + "(" + parameStr +");");
            tmp.setVisibility(method.getVisibility());
            tmp.addAnnotation("@Override");

            implClass.addMethod(tmp);
        }
        GeneratedJavaFile mapperJavafile2 = new GeneratedJavaFile(implClass, targetProject, javaFormatter);
        mapperJavaFiles.add(mapperJavafile2);
    }
}