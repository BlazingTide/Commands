package me.blazingtide.commands.adapter.defaults;

import me.blazingtide.commands.adapter.TypeAdapter;
import me.blazingtide.commands.label.Label;
import me.blazingtide.commands.sender.Sender;

public class StringTypeAdapter implements TypeAdapter<String> {

    @Override
    public String process(Label label) {
        return label.getValue();
    }

    @Override
    public void onException(Sender sender, String given, Exception exception) {

    }

}
