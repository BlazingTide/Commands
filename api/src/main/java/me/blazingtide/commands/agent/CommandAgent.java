package me.blazingtide.commands.agent;

public interface CommandAgent {

    /**
     * Runs the given runnable in another thread determined
     * by the agent.
     *
     * @param runnable runnable that's ran async
     */
    void runAsync(Runnable runnable);

}
