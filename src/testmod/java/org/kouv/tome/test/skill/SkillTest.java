package org.kouv.tome.test.skill;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.Vec3d;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.condition.SkillCondition;
import org.kouv.tome.api.skill.registry.SkillRegistries;
import org.kouv.tome.api.skill.state.SkillStateCreationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SkillTest implements ModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SkillTest.class);

    private final Skill<TestState> testSkill = Skill.<TestState>builder()
            .setCondition(context -> {
                LOGGER.info(
                        "skill tested: skill={}, source={}",
                        context.getSkill(),
                        context.getSource()
                );
                return SkillCondition.defaultConditions().test(context);
            })
            .setDurationProvider(context -> {
                LOGGER.info(
                        "skill duration set: skill={}, source={}",
                        context.getSkill(),
                        context.getSource()
                );
                return 50;
            })
            .setStateFactory(context -> {
                LOGGER.info(
                        "skill state set: skill={}, source={}",
                        context.getSkill(),
                        context.getSource()
                );
                return SkillStateCreationResult.ok(new TestState(context.getSource().getPos()));
            })
            .setStartBehavior(instance ->
                    LOGGER.info(
                            "skill started: state={}, duration={}",
                            instance.getState(),
                            instance.getDuration()
                    )
            )
            .setTickBehavior(instance ->
                    LOGGER.info(
                            "skill ticked: state={}, duration={}",
                            instance.getState(),
                            instance.getDuration()
                    )
            )
            .setCompleteBehavior(instance ->
                    LOGGER.info(
                            "skill completed: state={}, duration={}",
                            instance.getState(),
                            instance.getDuration()
                    )
            )
            .setEndBehavior(instance ->
                    LOGGER.info(
                            "skill ended: state={}, duration={}",
                            instance.getState(),
                            instance.getDuration()
                    )
            )
            .setCancelHandler(instance -> {
                LOGGER.info(
                        "skill cancelled: state={}, duration={}",
                        instance.getState(),
                        instance.getDuration()
                );
                return !instance.getSource().isOnGround();
            })
            .setInterruptHandler(instance -> {
                LOGGER.info(
                        "skill interrupted: state={}, duration={}",
                        instance.getState(),
                        instance.getDuration()
                );
                return !instance.getSource().isOnGround();
            })
            .build();

    @Override
    public void onInitialize() {
        Registry.register(SkillRegistries.SKILL, "tome:skill", testSkill);
    }

    private record TestState(Vec3d pos) {
    }
}
