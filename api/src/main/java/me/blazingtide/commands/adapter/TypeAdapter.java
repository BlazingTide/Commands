package me.blazingtide.commands.adapter;

public interface TypeAdapter<T> {

    T process(String str);

    void onException(Object sender, String given, Exception exception);

}
