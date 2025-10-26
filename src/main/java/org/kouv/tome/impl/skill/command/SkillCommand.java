package org.kouv.tome.impl.skill.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillResponse;
import org.kouv.tome.api.skill.registry.SkillRegistries;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public final class SkillCommand {
    private static final DynamicCommandExceptionType ENTITY_FAILED_EXCEPTION = new DynamicCommandExceptionType(
            name -> Text.stringifiedTranslatable("commands.skill.failed.entity", name)
    );
    private static final DynamicCommandExceptionType NOT_FOUND_EXCEPTION = new DynamicCommandExceptionType(
            id -> Text.stringifiedTranslatable("skill.notFound", id)
    );
    private static final SimpleCommandExceptionType ADD_FAILED_EXCEPTION = new SimpleCommandExceptionType(
            Text.translatable("commands.skill.add.failed")
    );
    private static final SimpleCommandExceptionType REMOVE_FAILED_EXCEPTION = new SimpleCommandExceptionType(
            Text.translatable("commands.skill.remove.failed")
    );

    private SkillCommand() {
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(argumentSkill());
    }

    private static LiteralArgumentBuilder<ServerCommandSource> argumentSkill() {
        return CommandManager.literal("skill")
                .requires(source -> source.hasPermissionLevel(2))
                .then(argumentAdd())
                .then(argumentRemove())
                .then(argumentTest())
                .then(argumentCast());
    }

    private static LiteralArgumentBuilder<ServerCommandSource> argumentAdd() {
        return CommandManager.literal("add")
                .then(
                        CommandManager.argument("skill", IdentifierArgumentType.identifier())
                                .suggests((context, builder) ->
                                        CommandSource.suggestIdentifiers(SkillRegistries.SKILL.getIds(), builder)
                                )
                                .executes(context ->
                                        executeAdd(
                                                context.getSource(),
                                                List.of(context.getSource().getEntityOrThrow()),
                                                getSkill(IdentifierArgumentType.getIdentifier(context, "skill"))
                                        )
                                )
                                .then(
                                        CommandManager.argument("targets", EntityArgumentType.entities())
                                                .executes(context ->
                                                        executeAdd(
                                                                context.getSource(),
                                                                EntityArgumentType.getEntities(context, "targets"),
                                                                getSkill(IdentifierArgumentType.getIdentifier(context, "skill"))
                                                        )
                                                )
                                )
                );
    }

    private static LiteralArgumentBuilder<ServerCommandSource> argumentRemove() {
        return CommandManager.literal("remove")
                .then(
                        CommandManager.argument("skill", IdentifierArgumentType.identifier())
                                .suggests((context, builder) ->
                                        CommandSource.suggestIdentifiers(SkillRegistries.SKILL.getIds(), builder)
                                )
                                .executes(context ->
                                        executeRemove(
                                                context.getSource(),
                                                List.of(context.getSource().getEntityOrThrow()),
                                                getSkill(IdentifierArgumentType.getIdentifier(context, "skill"))
                                        )
                                )
                                .then(
                                        CommandManager.argument("targets", EntityArgumentType.entities())
                                                .executes(context ->
                                                        executeRemove(
                                                                context.getSource(),
                                                                EntityArgumentType.getEntities(context, "targets"),
                                                                getSkill(IdentifierArgumentType.getIdentifier(context, "skill"))
                                                        )
                                                )
                                )
                );
    }

    private static LiteralArgumentBuilder<ServerCommandSource> argumentTest() {
        return CommandManager.literal("test")
                .then(
                        CommandManager.argument("skill", IdentifierArgumentType.identifier())
                                .suggests((context, builder) ->
                                        CommandSource.suggestIdentifiers(SkillRegistries.SKILL.getIds(), builder)
                                )
                                .executes(context ->
                                        executeTest(
                                                context.getSource(),
                                                context.getSource().getEntityOrThrow(),
                                                getSkill(IdentifierArgumentType.getIdentifier(context, "skill"))
                                        )
                                )
                                .then(
                                        CommandManager.argument("target", EntityArgumentType.entity())
                                                .executes(context ->
                                                        executeTest(
                                                                context.getSource(),
                                                                EntityArgumentType.getEntity(context, "target"),
                                                                getSkill(IdentifierArgumentType.getIdentifier(context, "skill"))
                                                        )
                                                )
                                )
                );
    }

    private static LiteralArgumentBuilder<ServerCommandSource> argumentCast() {
        return CommandManager.literal("cast")
                .then(
                        CommandManager.argument("skill", IdentifierArgumentType.identifier())
                                .suggests((context, builder) ->
                                        CommandSource.suggestIdentifiers(SkillRegistries.SKILL.getIds(), builder)
                                )
                                .executes(context ->
                                        executeCast(
                                                context.getSource(),
                                                context.getSource().getEntityOrThrow(),
                                                getSkill(IdentifierArgumentType.getIdentifier(context, "skill"))
                                        )
                                )
                                .then(
                                        CommandManager.argument("target", EntityArgumentType.entity())
                                                .executes(context ->
                                                        executeCast(
                                                                context.getSource(),
                                                                EntityArgumentType.getEntity(context, "target"),
                                                                getSkill(IdentifierArgumentType.getIdentifier(context, "skill"))
                                                        )
                                                )
                                )
                );
    }

    private static int executeAdd(
            ServerCommandSource source,
            Collection<? extends Entity> targets,
            RegistryEntry<? extends Skill> skill
    ) throws CommandSyntaxException {
        List<? extends LivingEntity> entities =
                filterLivingEntities(targets, entity -> entity.getSkillContainer().addSkill(skill));

        if (entities.isEmpty()) {
            throw ADD_FAILED_EXCEPTION.create();
        }

        if (entities.size() == 1) {
            source.sendFeedback(() -> Text.translatable("commands.skill.add.success.single", skill.value().getName(), entities.getFirst().getName()), true);
        } else {
            source.sendFeedback(() -> Text.translatable("commands.skill.add.success.multiple", skill.value().getName(), entities.size()), true);
        }

        return entities.size();
    }

    private static int executeRemove(
            ServerCommandSource source,
            Collection<? extends Entity> targets,
            RegistryEntry<? extends Skill> skill
    ) throws CommandSyntaxException {
        List<? extends LivingEntity> entities =
                filterLivingEntities(targets, entity -> entity.getSkillContainer().removeSkill(skill));

        if (entities.isEmpty()) {
            throw REMOVE_FAILED_EXCEPTION.create();
        }

        if (entities.size() == 1) {
            source.sendFeedback(() -> Text.translatable("commands.skill.remove.success.single", skill.value().getName(), entities.getFirst().getName()), true);
        } else {
            source.sendFeedback(() -> Text.translatable("commands.skill.remove.success.multiple", skill.value().getName(), entities.size()), true);
        }

        return entities.size();
    }

    private static int executeTest(
            ServerCommandSource source,
            Entity target,
            RegistryEntry<? extends Skill> skill
    ) throws CommandSyntaxException {
        switch (
                getLivingEntity(target).getSkillManager().testSkill(skill)
        ) {
            case SkillResponse.Success ignored ->
                    source.sendFeedback(() -> Text.translatable("commands.skill.test.success", skill.value().getName(), target.getName()), true);
            case SkillResponse.Failure(Text reason) ->
                    source.sendFeedback(() -> Text.translatable("commands.skill.test.failure", skill.value().getName(), target.getName(), reason), true);
        }

        return 1;
    }

    private static int executeCast(
            ServerCommandSource source,
            Entity target,
            RegistryEntry<? extends Skill> skill
    ) throws CommandSyntaxException {
        switch (
                getLivingEntity(target).getSkillManager().castSkill(skill)
        ) {
            case SkillResponse.Success ignored ->
                    source.sendFeedback(() -> Text.translatable("commands.skill.cast.success", skill.value().getName(), target.getName()), true);
            case SkillResponse.Failure(Text reason) ->
                    source.sendFeedback(() -> Text.translatable("commands.skill.cast.failure", skill.value().getName(), target.getName(), reason), true);
        }

        return 1;
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

    private static RegistryEntry<? extends Skill> getSkill(Identifier id) throws CommandSyntaxException {
        return SkillRegistries.SKILL.getEntry(id)
                .orElseThrow(() -> NOT_FOUND_EXCEPTION.create(id));
    }
}
