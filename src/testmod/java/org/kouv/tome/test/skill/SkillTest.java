package org.kouv.tome.test.skill;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registry;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillResponse;
import org.kouv.tome.api.skill.condition.SkillCondition;
import org.kouv.tome.api.skill.registry.SkillRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SkillTest implements ModInitializer {
    private final Logger LOGGER = LoggerFactory.getLogger(SkillTest.class);

    private final Skill testSkill = Skill.builder()
            .setCondition(context -> {
                SkillResponse response = SkillCondition.defaultConditions().test(context);
                LOGGER.info(
                        "skill condition called: response={}",
                        response
                );
                return response;
            })
            .setAction(context -> {
                SkillResponse response = context.getSource().isOnGround() ?
                        SkillResponse.unavailable() :
                        SkillResponse.success();

                if (response instanceof SkillResponse.Success) {
                    context.getSource().setVelocity(context.getSource().getRotationVector());
                    context.getSource().velocityModified = true;
                }

                LOGGER.info(
                        "skill action called: response={}",
                        response
                );
                return response;
            })
            .build();

    @Override
    public void onInitialize() {
        Registry.register(SkillRegistries.SKILL, "tome-testmod:test", testSkill);
    }
}
