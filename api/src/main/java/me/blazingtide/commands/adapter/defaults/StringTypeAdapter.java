package me.blazingtide.commands.adapter.defaults;

import me.blazingtide.commands.adapter.TypeAdapter;
import me.blazingtide.commands.sender.Sender;

import java.util.List;

public class StringTypeAdapter implements TypeAdapter<String> {

    @Override
    public String process(String label) {
        return label;
    }

    @Override
    public void onException(Sender sender, String given, Exception exception) {

    }

    @Override
    public List<String> getAutoComplete(String input, Sender sender) {
        return List.of("123", "1234", "12345");
    }
}
