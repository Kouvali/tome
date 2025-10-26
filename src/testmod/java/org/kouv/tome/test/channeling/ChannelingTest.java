package org.kouv.tome.test.channeling;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.math.Vec3d;
import org.kouv.tome.api.channeling.Channeling;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ChannelingTest implements ModInitializer {
    private final Logger LOGGER = LoggerFactory.getLogger(ChannelingTest.class);

    private final Channeling<TestState> testChanneling = Channeling.<TestState>builder()
            .setStartBehavior(context ->
                    LOGGER.info(
                            "channeling start behavior called: state={}",
                            context.getState()
                    )
            )
            .setTickBehavior(context -> {
                if (context.getSource().isSneaking()) {
                    context.getSource().getChannelingManager().completeChanneling();
                }

                LOGGER.info(
                        "channeling tick behavior called: state={}",
                        context.getState()
                );
            })
            .setCompleteBehavior(context -> {
                context.getSource().setVelocity(context.getState().velocity());
                context.getSource().velocityModified = true;
                LOGGER.info(
                        "channeling complete behavior called: state={}",
                        context.getState()
                );
            })
            .setCancelBehavior(context ->
                    LOGGER.info(
                            "channeling cancel behavior called: state={}",
                            context.getState()
                    )
            )
            .setInterruptBehavior(context ->
                    LOGGER.info(
                            "channeling interrupt behavior called: state={}",
                            context.getState()
                    )
            )
            .setEndBehavior(context ->
                    LOGGER.info(
                            "channeling end behavior called: state={}",
                            context.getState()
                    )
            )
            .setCancelCondition(context -> {
                boolean cancelled = !context.getSource().isOnGround();
                LOGGER.info(
                        "channeling cancel condition called: state={}, cancelled={}",
                        context.getState(),
                        cancelled
                );
                return cancelled;
            })
            .setInterruptCondition(context -> {
                boolean interrupted = !context.getSource().isOnGround();
                LOGGER.info(
                        "channeling interrupt condition called: state={}, interrupted={}",
                        context.getState(),
                        interrupted
                );
                return interrupted;
            })
            .build();

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(
                        CommandManager.literal("channeling_test")
                                .then(
                                        CommandManager.literal("start")
                                                .executes(context -> {
                                                    context.getSource()
                                                            .getPlayerOrThrow()
                                                            .getChannelingManager()
                                                            .startChanneling(testChanneling, new TestState(new Vec3d(0.0, 1.0, 0.0)));
                                                    return 1;
                                                })
                                )
                                .then(
                                        CommandManager.literal("complete")
                                                .executes(context -> {
                                                    context.getSource().getPlayerOrThrow().getChannelingManager().completeChanneling();
                                                    return 1;
                                                })
                                )
                                .then(
                                        CommandManager.literal("cancel")
                                                .executes(context -> {
                                                    context.getSource().getPlayerOrThrow().getChannelingManager().cancelChanneling();
                                                    return 1;
                                                })
                                )
                                .then(
                                        CommandManager.literal("interrupt")
                                                .executes(context -> {
                                                    context.getSource().getPlayerOrThrow().getChannelingManager().interruptChanneling();
                                                    return 1;
                                                })
                                )
                )
        );
    }

    private record TestState(Vec3d velocity) {
    }
}
