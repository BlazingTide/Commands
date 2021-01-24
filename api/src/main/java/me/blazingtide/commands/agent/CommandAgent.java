package me.blazingtide.commands.agent;

/**
 * Command agents are handlers that register the newly created commands.
 *
 * <p>
 * By creating an implementation of the command agent, you can have unlimited ways
 * to register and run commands.
 * </p>
 */
public interface CommandAgent {

    /**
     * Runs the given runnable in another thread determined
     * by the agent.
     *
     * @param runnable runnable that's ran async
     */
    void runAsync(Runnable runnable);

    /**
     * Called whenever a command is executed.
     * The method will find the correct command and then
     * handle that command.
     *
     * @param command full command label
     */
    void onCommand(String command);

}
