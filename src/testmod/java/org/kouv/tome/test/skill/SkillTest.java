package org.kouv.tome.test.skill;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import org.kouv.tome.api.entity.attribute.AttributeModifierSet;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillResponse;
import org.kouv.tome.api.skill.condition.SkillCondition;
import org.kouv.tome.api.skill.registry.SkillRegistries;
import org.kouv.tome.api.skill.state.SkillStateCreationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SkillTest implements ModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SkillTest.class);

    private static final Identifier ATTRIBUTE_MODIFIER_ID = Identifier.fromNamespaceAndPath("tome-testmod", "test_attribute_modifier");

    private final Skill<TestState> testSkill = Skill.<TestState>builder()
            .setAttributeModifierSet(
                    AttributeModifierSet.builder()
                            .addModifier(
                                    Attributes.SCALE,
                                    0.5,
                                    AttributeModifier.Operation.ADD_VALUE
                            )
                            .build()
            )
            .setCancelBehavior(instance ->
                    LOGGER.info(
                            "SkillCancelBehavior called: instance={}",
                            instance
                    )
            )
            .setCompleteBehavior(instance -> {
                instance.getSkillCooldownManager().setCooldown(instance.getSkill(), 50);
                instance.getSource().setDeltaMovement(instance.getState().rotation());
                instance.getSource().hurtMarked = true;
                LOGGER.info(
                        "SkillCompleteBehavior called: instance={}",
                        instance
                );
            })
            .setEndBehavior(instance ->
                    LOGGER.info(
                            "SkillEndBehavior called: instance={}",
                            instance
                    )
            )
            .setInterruptBehavior(instance ->
                    LOGGER.info(
                            "SkillInterruptBehavior called: instance={}",
                            instance
                    )
            )
            .setStartBehavior(instance ->
                    LOGGER.info(
                            "SkillStartBehavior called: instance={}",
                            instance
                    )
            )
            .setTickBehavior(instance -> {
                instance.getAttributeModifierTracker()
                        .applyModifier(
                                Attributes.MOVEMENT_SPEED,
                                ATTRIBUTE_MODIFIER_ID,
                                -instance.getProgress(),
                                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                        );

                LOGGER.info(
                        "SkillTickBehavior called: instance={}",
                        instance
                );
            })
            .setAddedCallback(context ->
                    LOGGER.info(
                            "SkillAddedCallback called: context={}",
                            context
                    )
            )
            .setCooldownEndedCallback(context ->
                    LOGGER.info(
                            "SkillCooldownEndedCallback called: context={}",
                            context
                    )
            )
            .setCooldownStartedCallback(context ->
                    LOGGER.info(
                            "SkillCooldownStartedCallback called: context={}",
                            context
                    )
            )
            .setLoadedCallback(context ->
                    LOGGER.info(
                            "SkillLoadedCallback called: context={}",
                            context.getSkill()
                    )
            )
            .setRemovedCallback(context ->
                    LOGGER.info(
                            "SkillRemovedCallback called: context={}",
                            context
                    )
            )
            .setCondition(context -> {
                SkillResponse response = SkillCondition.defaultConditions().test(context);
                LOGGER.info(
                        "SkillCondition called: context={}, response={}",
                        context,
                        response
                );
                return response;
            })
            .setCancelPredicate(instance -> {
                boolean cancelled = !instance.getSource().onGround();
                LOGGER.info(
                        "SkillCancelPredicate called: instance={}, cancelled={}",
                        instance,
                        cancelled
                );
                return cancelled;
            })
            .setInterruptPredicate(instance -> {
                boolean interrupted = !instance.getSource().onGround();
                LOGGER.info(
                        "SkillInterruptPredicate called: instance={}, interrupted={}",
                        instance,
                        interrupted
                );
                return interrupted;
            })
            .setStateFactory(context -> {
                SkillStateCreationResult<TestState> creationResult =
                        SkillStateCreationResult.ok(new TestState(context.getSource().getForward()));
                LOGGER.info(
                        "SkillStateFactory called: context={}, creationResult={}",
                        context,
                        creationResult
                );
                return creationResult;
            })
            .setDurationProvider(context -> {
                int duration = context.getSource().getRandom().nextInt(40, 80);
                LOGGER.info(
                        "SkillDurationProvider called: context={}, duration={}",
                        context,
                        duration
                );
                return duration;
            })
            .build();

    @Override
    public void onInitialize() {
        Registry.register(SkillRegistries.SKILL, "tome-testmod:test", testSkill);
    }

    private record TestState(Vec3 rotation) {
    }
}
