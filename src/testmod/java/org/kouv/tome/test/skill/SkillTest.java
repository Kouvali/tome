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
            .setDurationProvider(context -> {
                int duration = 50;
                LOGGER.info(
                        "skill duration provider called: skill={}, source={}, duration={}",
                        context.getSkill(),
                        context.getSource(),
                        duration
                );
                return duration;
            })
            .setStateFactory(context -> {
                SkillStateCreationResult<TestState> creationResult =
                        SkillStateCreationResult.ok(new TestState(context.getSource().getEntityPos()));
                LOGGER.info(
                        "skill state factory called: skill={}, source={}, creationResult={}",
                        context.getSkill(),
                        context.getSource(),
                        creationResult
                );
                return creationResult;
            })
            .setStartBehavior(instance ->
                    LOGGER.info(
                            "skill start behavior called: state={}, duration={}",
                            instance.getState(),
                            instance.getDuration()
                    )
            )
            .setTickBehavior(instance ->
                    LOGGER.info(
                            "skill tick behavior called: state={}, duration={}",
                            instance.getState(),
                            instance.getDuration()
                    )
            )
            .setCancelBehavior(instance ->
                    LOGGER.info(
                            "skill cancel behavior called: state={}, duration={}",
                            instance.getState(),
                            instance.getDuration()
                    )
            )
            .setInterruptBehavior(instance ->
                    LOGGER.info(
                            "skill interrupt behavior called: state={}, duration={}",
                            instance.getState(),
                            instance.getDuration()
                    )
            )
            .setCompleteBehavior(instance ->
                    LOGGER.info(
                            "skill complete behavior called: state={}, duration={}",
                            instance.getState(),
                            instance.getDuration()
                    )
            )
            .setEndBehavior(instance ->
                    LOGGER.info(
                            "skill end behavior called: state={}, duration={}",
                            instance.getState(),
                            instance.getDuration()
                    )
            )
            .setCancelHandler(instance -> {
                boolean cancelled = !instance.getSource().isOnGround();
                LOGGER.info(
                        "skill cancel handler called: state={}, duration={}, cancelled={}",
                        instance.getState(),
                        instance.getDuration(),
                        cancelled
                );
                return cancelled;
            })
            .setInterruptHandler(instance -> {
                boolean interrupted = !instance.getSource().isOnGround();
                LOGGER.info(
                        "skill interrupt handler called: state={}, duration={}, interrupted={}",
                        instance.getState(),
                        instance.getDuration(),
                        interrupted
                );
                return interrupted;
            })
            .build();

    @Override
    public void onInitialize() {
        Registry.register(SkillRegistries.SKILL, "tome:test_skill", testSkill);
    }

    private record TestState(Vec3d pos) {
    }
}
