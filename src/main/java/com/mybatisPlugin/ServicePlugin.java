package com.mybatisPlugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

public class ServicePlugin  extends PluginAdapter {

    public boolean validate(List<String> list) {
        System.out.print("aaaaas");
        return false;
    }

    public boolean clientSelectAllMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {

        interfaze.addAnnotation("ccccc");

        return true;
    }
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        System.out.print("kkkkkkllllll");
        return true;
    }
}