package me.blazingtide.commands.adapter;

import me.blazingtide.commands.sender.Sender;

import java.util.List;

public interface TypeAdapter<T> {

    T process(String label);

    void onException(Sender sender, String given, Exception exception);

    default List<String> getAutoComplete(String input, Sender sender) {
        return null;
    }

}
