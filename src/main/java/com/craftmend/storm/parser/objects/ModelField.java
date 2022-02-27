package com.craftmend.storm.parser.objects;

import com.craftmend.storm.api.StormModel;
import com.craftmend.storm.api.enums.KeyType;
import com.craftmend.storm.parser.types.TypeRegistry;
import com.craftmend.storm.parser.types.objects.StormTypeAdapter;
import com.craftmend.storm.utils.Reflection;
import lombok.Getter;

import java.lang.reflect.Field;

public class ModelField {

    @Getter private Class type;
    @Getter private String javaFieldName;
    @Getter private String columnName;
    @Getter private StormTypeAdapter<?> adapter;
    @Getter private Class<? extends StormModel> model;
    @Getter private int max;
    @Getter private KeyType keyType;
    @Getter private boolean unique;
    @Getter private boolean autoIncrement;
    @Getter private boolean notNull;

    public ModelField(Class<? extends StormModel> modelClass, Field field) {
        this.model = modelClass;
        this.type = field.getType();
        this.javaFieldName = field.getName();
        this.columnName = Reflection.getAnnotatedFieldName(field);
        this.adapter = TypeRegistry.getAdapterFor(this.type);
        this.max = Reflection.getAnnotatedFieldMax(field);
        this.keyType = Reflection.getAnnotatedKeyType(field);
        this.unique = Reflection.getAnnotatedUnique(field);
        this.autoIncrement = Reflection.getAnnotatedAutoIncrement(field);
        this.notNull = Reflection.getAnnotatedNotNull(field);
    }

    public String buildSqlType() {
        // build value
        String sqlTypeDeclaration = this.adapter.getSqlBaseType();
        sqlTypeDeclaration = sqlTypeDeclaration.replace("%max", this.max + "");
        return this.columnName + " " + sqlTypeDeclaration +
                (this.notNull ? " NOT NULL" : "") +
                (this.autoIncrement ? " AUTO_INCREMENT" : "") +
                (this.unique ? " UNIQUE" : "");
    }

}