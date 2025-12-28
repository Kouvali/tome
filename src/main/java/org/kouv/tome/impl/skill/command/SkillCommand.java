package org.kouv.tome.impl.skill.command;

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
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillResponse;
import org.kouv.tome.api.skill.registry.SkillRegistries;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public final class SkillCommand {
    private static final DynamicCommandExceptionType ENTITY_FAILED_EXCEPTION = new DynamicCommandExceptionType(
            name -> Component.translatableEscape("commands.skill.failed.entity", name)
    );
    private static final DynamicCommandExceptionType NOT_FOUND_EXCEPTION = new DynamicCommandExceptionType(
            id -> Component.translatableEscape("skill.notFound", id)
    );
    private static final SimpleCommandExceptionType ADD_FAILED_EXCEPTION = new SimpleCommandExceptionType(
            Component.translatable("commands.skill.add.failed")
    );
    private static final SimpleCommandExceptionType REMOVE_FAILED_EXCEPTION = new SimpleCommandExceptionType(
            Component.translatable("commands.skill.remove.failed")
    );
    private static final SimpleCommandExceptionType CANCEL_FAILED_EXCEPTION = new SimpleCommandExceptionType(
            Component.translatable("commands.skill.cancel.failed")
    );
    private static final SimpleCommandExceptionType INTERRUPT_FAILED_EXCEPTION = new SimpleCommandExceptionType(
            Component.translatable("commands.skill.interrupt.failed")
    );
    private static final SimpleCommandExceptionType TERMINATE_FAILED_EXCEPTION = new SimpleCommandExceptionType(
            Component.translatable("commands.skill.terminate.failed")
    );

    private SkillCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(argumentSkill());
    }

    private static LiteralArgumentBuilder<CommandSourceStack> argumentSkill() {
        return Commands.literal("skill")
                .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(argumentAdd())
                .then(argumentRemove())
                .then(argumentTest())
                .then(argumentCast())
                .then(argumentCancel())
                .then(argumentInterrupt())
                .then(argumentTerminate());
    }

    private static LiteralArgumentBuilder<CommandSourceStack> argumentAdd() {
        return Commands.literal("add")
                .then(
                        Commands.argument("skill", IdentifierArgument.id())
                                .suggests((context, builder) ->
                                        SharedSuggestionProvider.suggestResource(SkillRegistries.SKILL.keySet(), builder)
                                )
                                .executes(context ->
                                        executeAdd(
                                                context.getSource(),
                                                List.of(context.getSource().getEntityOrException()),
                                                getSkill(IdentifierArgument.getId(context, "skill"))
                                        )
                                )
                                .then(
                                        Commands.argument("targets", EntityArgument.entities())
                                                .executes(context ->
                                                        executeAdd(
                                                                context.getSource(),
                                                                EntityArgument.getEntities(context, "targets"),
                                                                getSkill(IdentifierArgument.getId(context, "skill"))
                                                        )
                                                )
                                )
                );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> argumentRemove() {
        return Commands.literal("remove")
                .then(
                        Commands.argument("skill", IdentifierArgument.id())
                                .suggests((context, builder) ->
                                        SharedSuggestionProvider.suggestResource(SkillRegistries.SKILL.keySet(), builder)
                                )
                                .executes(context ->
                                        executeRemove(
                                                context.getSource(),
                                                List.of(context.getSource().getEntityOrException()),
                                                getSkill(IdentifierArgument.getId(context, "skill"))
                                        )
                                )
                                .then(
                                        Commands.argument("targets", EntityArgument.entities())
                                                .executes(context ->
                                                        executeRemove(
                                                                context.getSource(),
                                                                EntityArgument.getEntities(context, "targets"),
                                                                getSkill(IdentifierArgument.getId(context, "skill"))
                                                        )
                                                )
                                )
                );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> argumentTest() {
        return Commands.literal("test")
                .then(
                        Commands.argument("skill", IdentifierArgument.id())
                                .suggests((context, builder) ->
                                        SharedSuggestionProvider.suggestResource(SkillRegistries.SKILL.keySet(), builder)
                                )
                                .executes(context ->
                                        executeTest(
                                                context.getSource(),
                                                context.getSource().getEntityOrException(),
                                                getSkill(IdentifierArgument.getId(context, "skill"))
                                        )
                                )
                                .then(
                                        Commands.argument("target", EntityArgument.entity())
                                                .executes(context ->
                                                        executeTest(
                                                                context.getSource(),
                                                                EntityArgument.getEntity(context, "target"),
                                                                getSkill(IdentifierArgument.getId(context, "skill"))
                                                        )
                                                )
                                )
                );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> argumentCast() {
        return Commands.literal("cast")
                .then(
                        Commands.argument("skill", IdentifierArgument.id())
                                .suggests((context, builder) ->
                                        SharedSuggestionProvider.suggestResource(SkillRegistries.SKILL.keySet(), builder)
                                )
                                .executes(context ->
                                        executeCast(
                                                context.getSource(),
                                                context.getSource().getEntityOrException(),
                                                getSkill(IdentifierArgument.getId(context, "skill"))
                                        )
                                )
                                .then(
                                        Commands.argument("target", EntityArgument.entity())
                                                .executes(context ->
                                                        executeCast(
                                                                context.getSource(),
                                                                EntityArgument.getEntity(context, "target"),
                                                                getSkill(IdentifierArgument.getId(context, "skill"))
                                                        )
                                                )
                                )
                );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> argumentCancel() {
        return Commands.literal("cancel")
                .executes(context ->
                        executeCancel(
                                context.getSource(),
                                List.of(context.getSource().getEntityOrException())
                        )
                )
                .then(
                        Commands.argument("targets", EntityArgument.entities())
                                .executes(context ->
                                        executeCancel(
                                                context.getSource(),
                                                EntityArgument.getEntities(context, "targets")
                                        )
                                )
                );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> argumentInterrupt() {
        return Commands.literal("interrupt")
                .executes(context ->
                        executeInterrupt(
                                context.getSource(),
                                List.of(context.getSource().getEntityOrException())
                        )
                )
                .then(
                        Commands.argument("targets", EntityArgument.entities())
                                .executes(context ->
                                        executeInterrupt(
                                                context.getSource(),
                                                EntityArgument.getEntities(context, "targets")
                                        )
                                )
                );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> argumentTerminate() {
        return Commands.literal("terminate")
                .executes(context ->
                        executeTerminate(
                                context.getSource(),
                                List.of(context.getSource().getEntityOrException())
                        )
                )
                .then(
                        Commands.argument("targets", EntityArgument.entities())
                                .executes(context ->
                                        executeTerminate(
                                                context.getSource(),
                                                EntityArgument.getEntities(context, "targets")
                                        )
                                )
                );
    }

    private static int executeAdd(
            CommandSourceStack source,
            Collection<? extends Entity> targets,
            Holder<? extends Skill<?>> skill
    ) throws CommandSyntaxException {
        List<? extends LivingEntity> entities =
                filterLivingEntities(targets, entity -> entity.getSkillContainer().addSkill(skill));

        if (entities.isEmpty()) {
            throw ADD_FAILED_EXCEPTION.create();
        }

        if (entities.size() == 1) {
            source.sendSuccess(() -> Component.translatable("commands.skill.add.success.single", skill.value().getName(), entities.getFirst().getName()), true);
        } else {
            source.sendSuccess(() -> Component.translatable("commands.skill.add.success.multiple", skill.value().getName(), entities.size()), true);
        }

        return entities.size();
    }

    private static int executeRemove(
            CommandSourceStack source,
            Collection<? extends Entity> targets,
            Holder<? extends Skill<?>> skill
    ) throws CommandSyntaxException {
        List<? extends LivingEntity> entities =
                filterLivingEntities(targets, entity -> entity.getSkillContainer().removeSkill(skill));

        if (entities.isEmpty()) {
            throw REMOVE_FAILED_EXCEPTION.create();
        }

        if (entities.size() == 1) {
            source.sendSuccess(() -> Component.translatable("commands.skill.remove.success.single", skill.value().getName(), entities.getFirst().getName()), true);
        } else {
            source.sendSuccess(() -> Component.translatable("commands.skill.remove.success.multiple", skill.value().getName(), entities.size()), true);
        }

        return entities.size();
    }

    private static int executeTest(
            CommandSourceStack source,
            Entity target,
            Holder<? extends Skill<?>> skill
    ) throws CommandSyntaxException {
        switch (
                getLivingEntity(target).getSkillManager().testSkill(skill)
        ) {
            case SkillResponse.Success ignored ->
                    source.sendSuccess(() -> Component.translatable("commands.skill.test.success", skill.value().getName(), target.getName()), true);
            case SkillResponse.Failure(Component reason) ->
                    source.sendSuccess(() -> Component.translatable("commands.skill.test.failure", skill.value().getName(), target.getName(), reason), true);
        }

        return 1;
    }

    private static int executeCast(
            CommandSourceStack source,
            Entity target,
            Holder<? extends Skill<?>> skill
    ) throws CommandSyntaxException {
        switch (
                getLivingEntity(target).getSkillManager().castSkill(skill)
        ) {
            case SkillResponse.Success ignored ->
                    source.sendSuccess(() -> Component.translatable("commands.skill.cast.success", skill.value().getName(), target.getName()), true);
            case SkillResponse.Failure(Component reason) ->
                    source.sendSuccess(() -> Component.translatable("commands.skill.cast.failure", skill.value().getName(), target.getName(), reason), true);
        }

        return 1;
    }

    private static int executeCancel(
            CommandSourceStack source,
            Collection<? extends Entity> targets
    ) throws CommandSyntaxException {
        List<? extends LivingEntity> entities =
                filterLivingEntities(targets, entity -> entity.getSkillManager().cancelCasting());

        if (entities.isEmpty()) {
            throw CANCEL_FAILED_EXCEPTION.create();
        }

        if (entities.size() == 1) {
            source.sendSuccess(() -> Component.translatable("commands.skill.cancel.success.single", entities.getFirst().getName()), true);
        } else {
            source.sendSuccess(() -> Component.translatable("commands.skill.cancel.success.multiple", entities.size()), true);
        }

        return entities.size();
    }

    private static int executeInterrupt(
            CommandSourceStack source,
            Collection<? extends Entity> targets
    ) throws CommandSyntaxException {
        List<? extends LivingEntity> entities =
                filterLivingEntities(targets, entity -> entity.getSkillManager().interruptCasting());

        if (entities.isEmpty()) {
            throw INTERRUPT_FAILED_EXCEPTION.create();
        }

        if (entities.size() == 1) {
            source.sendSuccess(() -> Component.translatable("commands.skill.interrupt.success.single", entities.getFirst().getName()), true);
        } else {
            source.sendSuccess(() -> Component.translatable("commands.skill.interrupt.success.multiple", entities.size()), true);
        }

        return entities.size();
    }

    private static int executeTerminate(
            CommandSourceStack source,
            Collection<? extends Entity> targets
    ) throws CommandSyntaxException {
        List<? extends LivingEntity> entities =
                filterLivingEntities(targets, entity -> entity.getSkillManager().terminateCasting());

        if (entities.isEmpty()) {
            throw TERMINATE_FAILED_EXCEPTION.create();
        }

        if (entities.size() == 1) {
            source.sendSuccess(() -> Component.translatable("commands.skill.terminate.success.single", entities.getFirst().getName()), true);
        } else {
            source.sendSuccess(() -> Component.translatable("commands.skill.terminate.success.multiple", entities.size()), true);
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

    private static LivingEntity getLivingEntity(Entity entity) throws CommandSyntaxException {
        if (!(entity instanceof LivingEntity livingEntity)) {
            throw ENTITY_FAILED_EXCEPTION.create(entity.getName());
        }

        return livingEntity;
    }

    private static Holder<? extends Skill<?>> getSkill(Identifier id) throws CommandSyntaxException {
        return SkillRegistries.SKILL.get(id)
                .orElseThrow(() -> NOT_FOUND_EXCEPTION.create(id));
    }
}
