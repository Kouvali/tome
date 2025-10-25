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

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;
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
    void testRequireLearned_whenSkillLearned_returnsSuccess() {
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
    void testRequireLearned_whenSkillNotLearned_returnsFailure() {
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
    void testRequireLearned_withSkillProvider_whenSkillLearned_returnsSuccess() {
        // given
        when(mockContext.getSource()).thenReturn(mockSource);

        RegistryEntry<? extends Skill<?>> otherSkill = mock();
        Function<? super SkillContext<?>, ? extends RegistryEntry<? extends Skill<?>>> skillProvider =
                context -> otherSkill;

        when(mockSource.getSkillContainer()).thenReturn(mockSkillContainer);
        when(mockSkillContainer.hasSkill(otherSkill)).thenReturn(true);

        // when
        SkillResponse response = SkillCondition.requireLearned(skillProvider).test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Success.class, response);
    }

    @Test
    void testRequireLearned_withSkillProvider_whenSkillNotLearned_returnsFailure() {
        // given
        when(mockContext.getSource()).thenReturn(mockSource);

        RegistryEntry<? extends Skill<?>> otherSkill = mock();
        Function<SkillContext<?>, RegistryEntry<? extends Skill<?>>> skillProvider =
                context -> otherSkill;

        when(mockSource.getSkillContainer()).thenReturn(mockSkillContainer);
        when(mockSkillContainer.hasSkill(otherSkill)).thenReturn(false);

        // when
        SkillResponse response = SkillCondition.requireLearned(skillProvider).test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Failure.class, response);
        assertEquals(SkillResponse.notLearned(), response);
    }

    @Test
    void testRequireLearned_withSkill_whenSkillLearned_returnsSuccess() {
        // given
        when(mockContext.getSource()).thenReturn(mockSource);

        RegistryEntry<? extends Skill<?>> mockSkill = mock();
        when(mockSource.getSkillContainer()).thenReturn(mockSkillContainer);
        when(mockSkillContainer.hasSkill(mockSkill)).thenReturn(true);

        // when
        SkillResponse response = SkillCondition.requireLearned(mockSkill).test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Success.class, response);
    }

    @Test
    void testRequireLearned_withSkill_whenSkillNotLearned_returnsFailure() {
        // given
        when(mockContext.getSource()).thenReturn(mockSource);

        RegistryEntry<? extends Skill<?>> mockSkill = mock();
        when(mockSource.getSkillContainer()).thenReturn(mockSkillContainer);
        when(mockSkillContainer.hasSkill(mockSkill)).thenReturn(false);

        // when
        SkillResponse response = SkillCondition.requireLearned(mockSkill).test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Failure.class, response);
        assertEquals(SkillResponse.notLearned(), response);
    }

    @Test
    void testRequireNoCooldown_whenNoCooldown_returnsSuccess() {
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
    void testRequireNoCooldown_whenOnCooldown_returnsFailure() {
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
    void testRequireNoCooldown_withSkillProvider_whenNoCooldown_returnsSuccess() {
        // given
        when(mockContext.getSource()).thenReturn(mockSource);

        RegistryEntry<? extends Skill<?>> otherSkill = mock();
        Function<SkillContext<?>, RegistryEntry<? extends Skill<?>>> skillProvider =
                context -> otherSkill;

        when(mockSource.getSkillCooldownManager()).thenReturn(mockCooldownManager);
        when(mockCooldownManager.isCoolingDown(otherSkill)).thenReturn(false);

        // when
        SkillResponse response = SkillCondition.requireNoCooldown(skillProvider).test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Success.class, response);
    }

    @Test
    void testRequireNoCooldown_withSkillProvider_whenOnCooldown_returnsFailure() {
        // given
        when(mockContext.getSource()).thenReturn(mockSource);

        RegistryEntry<? extends Skill<?>> otherSkill = mock();
        Function<SkillContext<?>, RegistryEntry<? extends Skill<?>>> skillProvider =
                context -> otherSkill;

        when(mockSource.getSkillCooldownManager()).thenReturn(mockCooldownManager);
        when(mockCooldownManager.isCoolingDown(otherSkill)).thenReturn(true);

        // when
        SkillResponse response = SkillCondition.requireNoCooldown(skillProvider).test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Failure.class, response);
        assertEquals(SkillResponse.cooldown(), response);
    }

    @Test
    void testRequireNoCooldown_withSkill_whenNoCooldown_returnsSuccess() {
        // given
        when(mockContext.getSource()).thenReturn(mockSource);

        RegistryEntry<? extends Skill<?>> mockSkill = mock();
        when(mockSource.getSkillCooldownManager()).thenReturn(mockCooldownManager);
        when(mockCooldownManager.isCoolingDown(mockSkill)).thenReturn(false);

        // when
        SkillResponse response = SkillCondition.requireNoCooldown(mockSkill).test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Success.class, response);
    }

    @Test
    void testRequireNoCooldown_withSkill_whenOnCooldown_returnsFailure() {
        // given
        when(mockContext.getSource()).thenReturn(mockSource);

        RegistryEntry<? extends Skill<?>> mockSkill = mock();
        when(mockSource.getSkillCooldownManager()).thenReturn(mockCooldownManager);
        when(mockCooldownManager.isCoolingDown(mockSkill)).thenReturn(true);

        // when
        SkillResponse response = SkillCondition.requireNoCooldown(mockSkill).test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Failure.class, response);
        assertEquals(SkillResponse.cooldown(), response);
    }

    @Test
    void testRequireInGame_whenInGame_returnsSuccess() {
        // given
        when(mockContext.getSource()).thenReturn(mockSource);
        when(mockSource.isPartOfGame()).thenReturn(true);

        // when
        SkillResponse response = SkillCondition.requireInGame().test(mockContext);

        // then
        assertInstanceOf(SkillResponse.Success.class, response);
    }

    @Test
    void testRequireInGame_whenNotInGame_returnsFailure() {
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
    void testDefaultConditions_whenAllConditionsMet_returnsSuccess() {
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
    void testAndOperator_whenBothConditionsSuccess_returnsSuccess() {
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
    void testAndOperator_whenFirstConditionFails_returnsFailure() {
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
    void testAndOperator_whenSecondConditionFails_returnsFailure() {
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
    void testAndOperator_whenBothConditionsFail_returnsFirstFailure() {
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
    void testOrOperator_whenBothConditionsSuccess_returnsSuccess() {
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
    void testOrOperator_whenFirstConditionSuccess_returnsSuccess() {
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
    void testOrOperator_whenSecondConditionSuccess_returnsSuccess() {
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
    void testOrOperator_whenBothConditionsFail_returnsLastFailure() {
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
