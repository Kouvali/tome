package org.kouv.tome.api.skill.condition;

import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillContext;
import org.kouv.tome.api.skill.SkillResponse;
import org.kouv.tome.api.skill.manager.SkillContainer;
import org.kouv.tome.api.skill.manager.SkillCooldownManager;
import org.kouv.tome.test.FabricExtension;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({FabricExtension.class, MockitoExtension.class})
class SkillConditionTest {
    @Mock
    private RegistryEntry<? extends Skill<?>> mockSkill;
    @Mock
    private SkillContext<?> mockContext;
    @Mock
    private LivingEntity mockSource;
    @Mock
    private SkillContainer mockSkillContainer;
    @Mock
    private SkillCooldownManager mockCooldownManager;

    @Test
    void testRequireLearned_Success() {
        // given
        when(mockContext.getSource()).thenReturn(mockSource);
        when(mockContext.getSkill()).thenAnswer(invocation -> mockSkill);

        when(mockSource.getSkillContainer()).thenReturn(mockSkillContainer);
        when(mockSkillContainer.hasSkill(mockSkill)).thenReturn(true);

        // when
        SkillResponse response = SkillCondition.requireLearned().test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Success.class, response);
    }

    @Test
    void testRequireLearned_Failure() {
        // given
        when(mockContext.getSource()).thenReturn(mockSource);
        when(mockContext.getSkill()).thenAnswer(invocation -> mockSkill);

        when(mockSource.getSkillContainer()).thenReturn(mockSkillContainer);
        when(mockSkillContainer.hasSkill(mockSkill)).thenReturn(false);

        // when
        SkillResponse response = SkillCondition.requireLearned().test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Failure.class, response);
        assertEquals(SkillResponse.notLearned(), response);
    }

    @Test
    void testRequireNoCooldown_Success() {
        // given
        when(mockContext.getSource()).thenReturn(mockSource);
        when(mockContext.getSkill()).thenAnswer(invocation -> mockSkill);

        when(mockSource.getSkillCooldownManager()).thenReturn(mockCooldownManager);
        when(mockCooldownManager.isCoolingDown(mockSkill)).thenReturn(false);

        // when
        SkillResponse response = SkillCondition.requireNoCooldown().test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Success.class, response);
    }

    @Test
    void testRequireNoCooldown_Failure() {
        // given
        when(mockContext.getSource()).thenReturn(mockSource);
        when(mockContext.getSkill()).thenAnswer(invocation -> mockSkill);

        when(mockSource.getSkillCooldownManager()).thenReturn(mockCooldownManager);
        when(mockCooldownManager.isCoolingDown(mockSkill)).thenReturn(true);

        // when
        SkillResponse response = SkillCondition.requireNoCooldown().test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Failure.class, response);
        assertEquals(SkillResponse.cooldown(), response);
    }

    @Test
    void testRequireInGame_Success() {
        // given
        when(mockContext.getSource()).thenReturn(mockSource);
        when(mockSource.isPartOfGame()).thenReturn(true);

        // when
        SkillResponse response = SkillCondition.requireInGame().test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Success.class, response);
    }

    @Test
    void testRequireInGame_Failure() {
        // given
        when(mockContext.getSource()).thenReturn(mockSource);
        when(mockSource.isPartOfGame()).thenReturn(false);

        // when
        SkillResponse response = SkillCondition.requireInGame().test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Failure.class, response);
        assertEquals(SkillResponse.unavailable(), response);
    }

    @Test
    void testDefaultConditions() {
        // given
        when(mockContext.getSource()).thenReturn(mockSource);
        when(mockContext.getSkill()).thenAnswer(invocation -> mockSkill);

        when(mockSource.getSkillContainer()).thenReturn(mockSkillContainer);
        when(mockSource.getSkillCooldownManager()).thenReturn(mockCooldownManager);
        when(mockSource.isPartOfGame()).thenReturn(true);

        when(mockSkillContainer.hasSkill(mockSkill)).thenReturn(true);
        when(mockCooldownManager.isCoolingDown(mockSkill)).thenReturn(false);

        // when
        SkillResponse response = SkillCondition.defaultConditions().test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Success.class, response);
    }

    @Test
    void testAndOperator_BothSuccess() {
        // given
        SkillCondition condition1 = context -> SkillResponse.success();
        SkillCondition condition2 = context -> SkillResponse.success();

        // when
        SkillCondition combined = condition1.and(condition2);
        SkillResponse response = combined.test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Success.class, response);
    }

    @Test
    void testAndOperator_FirstFails() {
        // given
        SkillCondition condition1 = context -> SkillResponse.unavailable();
        SkillCondition condition2 = context -> SkillResponse.success();

        // when
        SkillCondition combined = condition1.and(condition2);
        SkillResponse response = combined.test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Failure.class, response);
        assertEquals(SkillResponse.unavailable(), response);
    }

    @Test
    void testAndOperator_SecondFails() {
        // given
        SkillCondition condition1 = context -> SkillResponse.success();
        SkillCondition condition2 = context -> SkillResponse.unavailable();

        // when
        SkillCondition combined = condition1.and(condition2);
        SkillResponse response = combined.test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Failure.class, response);
        assertEquals(SkillResponse.unavailable(), response);
    }

    @Test
    void testAndOperator_BothFail_ReturnsFirstFailure() {
        // given
        SkillCondition condition1 = context -> SkillResponse.inProgress();
        SkillCondition condition2 = context -> SkillResponse.notLearned();

        // when
        SkillCondition combined = condition1.and(condition2);
        SkillResponse response = combined.test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Failure.class, response);
        assertEquals(SkillResponse.inProgress(), response);
    }

    @Test
    void testOrOperator_BothSuccess() {
        // given
        SkillCondition condition1 = context -> SkillResponse.success();
        SkillCondition condition2 = context -> SkillResponse.success();

        // when
        SkillCondition combined = condition1.or(condition2);
        SkillResponse response = combined.test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Success.class, response);
    }

    @Test
    void testOrOperator_FirstSuccess() {
        // given
        SkillCondition condition1 = context -> SkillResponse.success();
        SkillCondition condition2 = context -> SkillResponse.unavailable();

        // when
        SkillCondition combined = condition1.or(condition2);
        SkillResponse response = combined.test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Success.class, response);
    }

    @Test
    void testOrOperator_SecondSuccess() {
        // given
        SkillCondition condition1 = context -> SkillResponse.unavailable();
        SkillCondition condition2 = context -> SkillResponse.success();

        // when
        SkillCondition combined = condition1.or(condition2);
        SkillResponse response = combined.test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Success.class, response);
    }

    @Test
    void testOrOperator_BothFail() {
        // given
        SkillCondition condition1 = context -> SkillResponse.inProgress();
        SkillCondition condition2 = context -> SkillResponse.notLearned();

        // when
        SkillCondition combined = condition1.or(condition2);
        SkillResponse response = combined.test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Failure.class, response);
        assertEquals(SkillResponse.notLearned(), response);
    }
}
