package org.kouv.tome.impl.buff.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.IdentifierArgument;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.kouv.tome.api.buff.Buff;
import org.kouv.tome.api.buff.registry.BuffRegistries;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public final class BuffCommand {
    private static final DynamicCommandExceptionType NOT_FOUND_EXCEPTION = new DynamicCommandExceptionType(
            id -> Component.translatableEscape("buff.notFound", id)
    );
    private static final SimpleCommandExceptionType REMOVE_FAILED_EXCEPTION = new SimpleCommandExceptionType(
            Component.translatable("commands.buff.remove.failed")
    );
    private static final SimpleCommandExceptionType CLEAR_FAILED_EXCEPTION = new SimpleCommandExceptionType(
            Component.translatable("commands.buff.clear.failed")
    );

    private BuffCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(argumentBuff());
    }

    private static LiteralArgumentBuilder<CommandSourceStack> argumentBuff() {
        return Commands.literal("buff")
                .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(argumentRemove())
                .then(argumentClear());
    }

    private static LiteralArgumentBuilder<CommandSourceStack> argumentRemove() {
        return Commands.literal("remove")
                .then(
                        Commands.argument("buff", IdentifierArgument.id())
                                .suggests((context, builder) ->
                                        SharedSuggestionProvider.suggestResource(BuffRegistries.BUFF.keySet(), builder)
                                )
                                .executes(context ->
                                        executeRemove(
                                                context.getSource(),
                                                List.of(context.getSource().getEntityOrException()),
                                                getBuff(IdentifierArgument.getId(context, "buff"))
                                        )
                                )
                                .then(
                                        Commands.argument("targets", EntityArgument.entities())
                                                .executes(context ->
                                                        executeRemove(
                                                                context.getSource(),
                                                                EntityArgument.getEntities(context, "targets"),
                                                                getBuff(IdentifierArgument.getId(context, "buff"))
                                                        )
                                                )
                                )
                );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> argumentClear() {
        return Commands.literal("clear")
                .executes(context ->
                        executeClear(
                                context.getSource(),
                                List.of(context.getSource().getEntityOrException())
                        )
                )
                .then(
                        Commands.argument("targets", EntityArgument.entities())
                                .executes(context ->
                                        executeClear(
                                                context.getSource(),
                                                EntityArgument.getEntities(context, "targets")
                                        )
                                )
                );
    }

    private static int executeRemove(
            CommandSourceStack source,
            Collection<? extends Entity> targets,
            Holder<? extends Buff<?>> buff
    ) throws CommandSyntaxException {
        List<? extends LivingEntity> entities =
                filterLivingEntities(targets, entity -> entity.getBuffManager().removeBuff(buff));

        if (entities.isEmpty()) {
            throw REMOVE_FAILED_EXCEPTION.create();
        }

        if (entities.size() == 1) {
            source.sendSuccess(() -> Component.translatable("commands.buff.remove.success.single", buff.value().getName(), entities.getFirst().getName()), true);
        } else {
            source.sendSuccess(() -> Component.translatable("commands.buff.remove.success.multiple", buff.value().getName(), entities.size()), true);
        }

        return entities.size();
    }

    private static int executeClear(
            CommandSourceStack source,
            Collection<? extends Entity> targets
    ) throws CommandSyntaxException {
        List<? extends LivingEntity> entities =
                filterLivingEntities(targets, entity -> {
                    entity.getBuffManager().clearBuffs();
                    return true;
                });

        if (entities.isEmpty()) {
            throw CLEAR_FAILED_EXCEPTION.create();
        }

        if (entities.size() == 1) {
            source.sendSuccess(() -> Component.translatable("commands.buff.clear.success.single", entities.getFirst().getName()), true);
        } else {
            source.sendSuccess(() -> Component.translatable("commands.buff.clear.success.multiple", entities.size()), true);
        }

        return entities.size();
    }

    private static List<? extends LivingEntity> filterLivingEntities(
            Collection<? extends Entity> entities,
            Predicate<? super LivingEntity> predicate
    ) {
        return entities.stream()
                .filter(LivingEntity.class::isInstance)
                .map(LivingEntity.class::cast)
                .filter(predicate)
                .toList();
    }

    private static Holder<? extends Buff<?>> getBuff(Identifier id) throws CommandSyntaxException {
        return BuffRegistries.BUFF.get(id)
                .orElseThrow(() -> NOT_FOUND_EXCEPTION.create(id));
    }
}
