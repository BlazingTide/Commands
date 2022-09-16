package me.blazingtide.commands.sender.dispatcher;

import java.util.HashMap;
import java.util.Map;

public class DispatcherServiceImpl implements DispatcherService {

    private final Map<Class<?>, DispatcherProvider<?>> dispatcherProviders = new HashMap<>();


    @Override
    public Map<Class<?>, DispatcherProvider<?>> getDispatcherProviders() {
        return dispatcherProviders;
    }

    @Override
    public void registerDispatcherProvider(Class<?> clazz, DispatcherProvider<?> provider) {
        dispatcherProviders.put(clazz, provider);
    }
}
