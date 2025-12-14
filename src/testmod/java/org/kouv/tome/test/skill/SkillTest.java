package org.kouv.tome.test.skill;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.Vec3d;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillResponse;
import org.kouv.tome.api.skill.condition.SkillCondition;
import org.kouv.tome.api.skill.registry.SkillRegistries;
import org.kouv.tome.api.skill.state.SkillStateCreationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SkillTest implements ModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SkillTest.class);

    private final Skill<TestState> testSkill = Skill.<TestState>builder()
            .setCancelBehavior(instance ->
                    LOGGER.info(
                            "SkillCancelBehavior called: instance={}",
                            instance
                    )
            )
            .setCompleteBehavior(instance -> {
                instance.getSkillCooldownManager().setCooldown(instance.getSkill(), 50);
                instance.getSource().setVelocity(instance.getState().rotation());
                instance.getSource().velocityModified = true;
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
            .setTickBehavior(instance ->
                    LOGGER.info(
                            "SkillTickBehavior called: instance={}",
                            instance
                    )
            )
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
                boolean cancelled = !instance.getSource().isOnGround();
                LOGGER.info(
                        "SkillCancelPredicate called: instance={}, cancelled={}",
                        instance,
                        cancelled
                );
                return cancelled;
            })
            .setInterruptPredicate(instance -> {
                boolean interrupted = !instance.getSource().isOnGround();
                LOGGER.info(
                        "SkillInterruptPredicate called: instance={}, interrupted={}",
                        instance,
                        interrupted
                );
                return interrupted;
            })
            .setStateFactory(context -> {
                SkillStateCreationResult<TestState> creationResult =
                        SkillStateCreationResult.ok(new TestState(context.getSource().getRotationVector()));
                LOGGER.info(
                        "SkillStateFactory called: context={}, creationResult={}",
                        context,
                        creationResult
                );
                return creationResult;
            })
            .setTotalDuration(50)
            .build();

    @Override
    public void onInitialize() {
        Registry.register(SkillRegistries.SKILL, "tome-testmod:test", testSkill);
    }

    private record TestState(Vec3d rotation) {
    }
}
