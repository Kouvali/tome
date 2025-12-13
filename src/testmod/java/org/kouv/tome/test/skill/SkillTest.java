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
            .setAddBehavior(context ->
                    LOGGER.info(
                            "skill add behavior called: skill={}, source={}",
                            context.getSkill(),
                            context.getSource()
                    )
            )
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
            .setCooldownEndBehavior(context ->
                    LOGGER.info(
                            "skill cooldown end behavior called: skill={}, source={}",
                            context.getSkill(),
                            context.getSource()
                    )
            )
            .setCooldownStartBehavior(context ->
                    LOGGER.info(
                            "skill cooldown start behavior called: skill={}, source={}",
                            context.getSkill(),
                            context.getSource()
                    )
            )
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
            .setEntityLoadBehavior(context ->
                    LOGGER.info(
                            "skill entity load behavior called: skill={}, source={}",
                            context.getSkill(),
                            context.getSource()
                    )
            )
            .setEntityUnloadBehavior(context ->
                    LOGGER.info(
                            "skill entity unload behavior called: skill={}, source={}",
                            context.getSkill(),
                            context.getSource()
                    )
            )
            .setRemoveBehavior(context ->
                    LOGGER.info(
                            "skill remove behavior called: skill={}, source={}",
                            context.getSkill(),
                            context.getSource()
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
            .setCancelCondition(instance -> {
                boolean cancelled = !instance.getSource().isOnGround();
                LOGGER.info(
                        "skill cancel condition called: state={}, elapsedTime={}, cancelled={}",
                        instance.getState(),
                        instance.getElapsedTime(),
                        cancelled
                );
                return cancelled;
            })
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
            .setInterruptCondition(instance -> {
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
