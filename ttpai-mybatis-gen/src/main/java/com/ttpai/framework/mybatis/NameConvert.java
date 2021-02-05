package com.ttpai.framework.mybatis;

import com.baomidou.mybatisplus.generator.config.INameConvert;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;

public class NameConvert implements INameConvert {

    @Override
    public String entityNameConvert(TableInfo tableInfo) {
        return tableInfo.getName() + "VO";
    }

    @Override
    public String propertyNameConvert(TableField field) {
        return field.getPropertyName();
    }
}
