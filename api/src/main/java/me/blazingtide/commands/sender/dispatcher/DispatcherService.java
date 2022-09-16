package me.blazingtide.commands.sender.dispatcher;

import java.util.List;
import java.util.Map;

public interface DispatcherService {

    Map<Class<?>, DispatcherProvider<?>> getDispatcherProviders();

    void registerDispatcherProvider(Class<?> clazz,DispatcherProvider<?> provider);

}
