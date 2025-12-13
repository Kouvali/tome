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
                            "skill cancel behavior called: state={}, elapsedTime={}",
                            instance.getState(),
                            instance.getElapsedTime()
                    )
            )
            .setCompleteBehavior(instance -> {
                instance.getSkillCooldownManager().setCooldown(instance.getSkill(), 50);
                instance.getSource().setVelocity(instance.getState().rotation());
                instance.getSource().velocityModified = true;
                LOGGER.info(
                        "skill complete behavior called: state={}, elapsedTime={}",
                        instance.getState(),
                        instance.getElapsedTime()
                );
            })
            .setEndBehavior(instance ->
                    LOGGER.info(
                            "skill end behavior called: state={}, elapsedTime={}",
                            instance.getState(),
                            instance.getElapsedTime()
                    )
            )
            .setInterruptBehavior(instance ->
                    LOGGER.info(
                            "skill interrupt behavior called: state={}, elapsedTime={}",
                            instance.getState(),
                            instance.getElapsedTime()
                    )
            )
            .setStartBehavior(instance ->
                    LOGGER.info(
                            "skill start behavior called: state={}, elapsedTime={}",
                            instance.getState(),
                            instance.getElapsedTime()
                    )
            )
            .setTickBehavior(instance ->
                    LOGGER.info(
                            "skill tick behavior called: state={}, elapsedTime={}",
                            instance.getState(),
                            instance.getElapsedTime()
                    )
            )
            .setAddCallback(context ->
                    LOGGER.info(
                            "skill add behavior called: skill={}, source={}",
                            context.getSkill(),
                            context.getSource()
                    )
            )
            .setCooldownEndCallback(context ->
                    LOGGER.info(
                            "skill cooldown end behavior called: skill={}, source={}",
                            context.getSkill(),
                            context.getSource()
                    )
            )
            .setCooldownStartCallback(context ->
                    LOGGER.info(
                            "skill cooldown start behavior called: skill={}, source={}",
                            context.getSkill(),
                            context.getSource()
                    )
            )
            .setEntityLoadCallback(context ->
                    LOGGER.info(
                            "skill entity load behavior called: skill={}, source={}",
                            context.getSkill(),
                            context.getSource()
                    )
            )
            .setEntityUnloadCallback(context ->
                    LOGGER.info(
                            "skill entity unload behavior called: skill={}, source={}",
                            context.getSkill(),
                            context.getSource()
                    )
            )
            .setRemoveCallback(context ->
                    LOGGER.info(
                            "skill remove behavior called: skill={}, source={}",
                            context.getSkill(),
                            context.getSource()
                    )
            )
            .setCondition(context -> {
                SkillResponse response = SkillCondition.defaultConditions().test(context);
                LOGGER.info(
                        "skill condition called: skill={}, source={}, response={}",
                        context.getSkill(),
                        context.getSource(),
                        response
                );
                return response;
            })
            .setCancelPredicate(instance -> {
                boolean cancelled = !instance.getSource().isOnGround();
                LOGGER.info(
                        "skill cancel condition called: state={}, elapsedTime={}, cancelled={}",
                        instance.getState(),
                        instance.getElapsedTime(),
                        cancelled
                );
                return cancelled;
            })
            .setInterruptPredicate(instance -> {
                boolean interrupted = !instance.getSource().isOnGround();
                LOGGER.info(
                        "skill interrupt condition called: state={}, elapsedTime={}, interrupted={}",
                        instance.getState(),
                        instance.getElapsedTime(),
                        interrupted
                );
                return interrupted;
            })
            .setStateFactory(context -> {
                SkillStateCreationResult<TestState> creationResult =
                        SkillStateCreationResult.ok(new TestState(context.getSource().getRotationVector()));
                LOGGER.info(
                        "skill state factory called: skill={}, source={}, creationResult={}",
                        context.getSkill(),
                        context.getSource(),
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
