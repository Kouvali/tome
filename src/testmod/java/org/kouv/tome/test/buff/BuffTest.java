package org.kouv.tome.test.buff;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.serialization.Codec;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.kouv.tome.api.buff.Buff;
import org.kouv.tome.api.buff.registry.BuffRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BuffTest implements ModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuffTest.class);

    private final DataComponentType<Double> testComponent = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(
                    "tome-testmod",
                    "buff_test"
            ),
            DataComponentType.<Double>builder()
                    .persistent(Codec.DOUBLE)
                    .build()
    );

    private final Holder<Buff<TestParams>> testBuff = Registry.registerForHolder(
            BuffRegistries.BUFF,
            Identifier.fromNamespaceAndPath(
                    "tome-testmod",
                    "buff_test"
            ),
            Buff.<TestParams>builder()
                    .setComponents(
                            DataComponentMap.builder()
                                    .set(testComponent, 0.5)
                                    .build()
                    )
                    .setApplyBehavior(instance -> {
                        instance.getAttributeModifierTracker().applyModifier(
                                Attributes.SCALE,
                                (instance.getParams().level() + 1) *
                                        instance.getBuff().value().getComponents().getOrDefault(testComponent, 0.5),
                                AttributeModifier.Operation.ADD_VALUE
                        );

                        LOGGER.info(
                                "BuffApplyBehavior called: instance={}",
                                instance
                        );
                    })
                    .setEndBehavior(instance ->
                            LOGGER.info(
                                    "BuffEndBehavior called: instance={}",
                                    instance
                            )
                    )
                    .setExpireBehavior(instance ->
                            LOGGER.info(
                                    "BuffExpireBehavior called: instance={}",
                                    instance
                            )
                    )
                    .setRemoveBehavior(instance ->
                            LOGGER.info(
                                    "BuffRemoveBehavior called: instance={}",
                                    instance
                            )
                    )
                    .setTickBehavior(instance ->
                            LOGGER.info(
                                    "BuffTickBehavior called: instance={}",
                                    instance
                            )
                    )
                    .setUpdateBehavior(instance ->
                            LOGGER.info(
                                    "BuffUpdateBehavior called: instance={}",
                                    instance
                            )
                    )
                    .build()
    );

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, _, _) ->
                dispatcher.register(argumentTest())
        );
    }

    private LiteralArgumentBuilder<CommandSourceStack> argumentTest() {
        return Commands.literal("buff_test")
                .then(argumentApply())
                .then(argumentUpdate());
    }

    private LiteralArgumentBuilder<CommandSourceStack> argumentApply() {
        return Commands.literal("apply")
                .executes(context ->
                        executeApply(
                                context.getSource(),
                                context.getSource().getPlayerOrException()
                        )
                );
    }

    private LiteralArgumentBuilder<CommandSourceStack> argumentUpdate() {
        return Commands.literal("update")
                .executes(context ->
                        executeUpdate(
                                context.getSource(),
                                context.getSource().getPlayerOrException()
                        )
                );
    }

    private int executeApply(CommandSourceStack source, ServerPlayer target) {
        TestParams params = new TestParams(target.experienceLevel);

        if (target.getBuffManager().applyBuff(testBuff, params, 100)) {
            source.sendSuccess(() -> Component.literal("Applied buff"), false);
        } else {
            source.sendSuccess(() -> Component.literal("Failed to apply buff"), false);
        }

        return 1;
    }

    private int executeUpdate(CommandSourceStack source, ServerPlayer target) {
        if (target.getBuffManager().updateBuff(testBuff, params -> params, duration -> duration + 100)) {
            source.sendSuccess(() -> Component.literal("Updated buff"), false);
        } else {
            source.sendSuccess(() -> Component.literal("Failed to update buff"), false);
        }

        return 1;
    }

    private record TestParams(int level) {
    }
}
