package com.craftmend.storm.parser.types.objects;

public abstract class StormTypeAdapter<T> {

    public abstract T fromSql(Object sqlValue);
    public abstract Object toSql(T value);
    public abstract String getSqlBaseType();
    public abstract boolean escapeAsString();

}
