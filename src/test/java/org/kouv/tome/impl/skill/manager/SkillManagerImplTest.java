package org.kouv.tome.impl.skill.manager;

import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillInstance;
import org.kouv.tome.api.skill.SkillResponse;
import org.kouv.tome.api.skill.duration.SkillDurationProvider;
import org.kouv.tome.api.skill.handler.SkillCancelHandler;
import org.kouv.tome.api.skill.handler.SkillInterruptHandler;
import org.kouv.tome.api.skill.state.SkillStateCreationResult;
import org.kouv.tome.api.skill.state.SkillStateFactory;
import org.kouv.tome.test.FabricExtension;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({FabricExtension.class, MockitoExtension.class})
class SkillManagerImplTest {
    @Mock
    private RegistryEntry<? extends Skill<String>> mockEntry;
    @Mock
    private LivingEntity mockSource;

    @Test
    void testIsCasting_whenNotCasting_returnsFalse() {
        // given
        SkillManagerImpl manager = new SkillManagerImpl(mockSource);

        // when & then
        assertFalse(manager.isCasting());
    }

    @Test
    void testIsCasting_whenCasting_returnsTrue() {
        // given
        Skill<String> skill = defaultBuilder()
                .build();

        SkillManagerImpl manager = new SkillManagerImpl(mockSource);
        when(mockEntry.value()).thenAnswer(invocation -> skill);

        // when
        manager.castSkill(mockEntry);

        // then
        assertTrue(manager.isCasting());
    }

    @Test
    void testGetCastingInstance_bySkill_whenNotCasting_returnsNull() {
        // given
        SkillManagerImpl manager = new SkillManagerImpl(mockSource);

        // when
        SkillInstance<String> instance = manager.getCastingInstance(mockEntry);

        // then
        assertNull(instance);
    }

    @Test
    void testGetCastingInstance_bySkill_whenCastingDifferentSkill_returnsNull() {
        // given
        RegistryEntry<? extends Skill<String>> mockEntry2 = mock();

        Skill<String> skill = defaultBuilder()
                .build();

        SkillManagerImpl manager = new SkillManagerImpl(mockSource);
        when(mockEntry.value()).thenAnswer(invocation -> skill);

        manager.castSkill(mockEntry);

        // when
        SkillInstance<String> instance = manager.getCastingInstance(mockEntry2);

        // then
        assertNull(instance);
    }

    @Test
    void testGetCastingInstance_bySkill_whenCastingSameSkill_returnsInstance() {
        // given
        Skill<String> skill = defaultBuilder()
                .build();

        SkillManagerImpl manager = new SkillManagerImpl(mockSource);
        when(mockEntry.value()).thenAnswer(invocation -> skill);

        manager.castSkill(mockEntry);

        // when
        SkillInstance<String> instance = manager.getCastingInstance(mockEntry);

        // then
        assertNotNull(instance);
        assertEquals("test-state", instance.getState());
    }

    @Test
    void testGetCastingInstance_byClass_whenNotCasting_returnsNull() {
        // given
        SkillManagerImpl manager = new SkillManagerImpl(mockSource);

        // when
        SkillInstance<String> instance = manager.getCastingInstance(String.class);

        // then
        assertNull(instance);
    }

    @Test
    void testGetCastingInstance_byClass_whenCastingDifferentType_returnsNull() {
        // given
        Skill<String> skill = defaultBuilder()
                .build();

        SkillManagerImpl manager = new SkillManagerImpl(mockSource);
        when(mockEntry.value()).thenAnswer(invocation -> skill);

        manager.castSkill(mockEntry);

        // when
        SkillInstance<Integer> instance = manager.getCastingInstance(Integer.class);

        // then
        assertNull(instance);
    }

    @Test
    void testGetCastingInstance_byClass_whenCastingSameType_returnsInstance() {
        // given
        Skill<String> skill = defaultBuilder()
                .build();

        SkillManagerImpl manager = new SkillManagerImpl(mockSource);
        when(mockEntry.value()).thenAnswer(invocation -> skill);

        manager.castSkill(mockEntry);

        // when
        SkillInstance<String> instance = manager.getCastingInstance(String.class);

        // then
        assertNotNull(instance);
        assertEquals("test-state", instance.getState());
    }

    @Test
    void testTestSkill_whenNotCasting_andConditionSuccess_returnsSuccess() {
        // given
        Skill<String> skill = defaultBuilder()
                .build();

        SkillManagerImpl manager = new SkillManagerImpl(mockSource);
        when(mockEntry.value()).thenAnswer(invocation -> skill);

        // when
        SkillResponse response = manager.testSkill(mockEntry);

        // then
        assertInstanceOf(SkillResponse.Success.class, response);
    }

    @Test
    void testTestSkill_whenNotCasting_andConditionFailure_returnsFailure() {
        // given
        Skill<String> skill = defaultBuilder()
                .setCondition(context -> SkillResponse.unavailable())
                .build();

        SkillManagerImpl manager = new SkillManagerImpl(mockSource);
        when(mockEntry.value()).thenAnswer(invocation -> skill);

        // when
        SkillResponse response = manager.testSkill(mockEntry);

        // then
        assertInstanceOf(SkillResponse.Failure.class, response);
    }

    @Test
    void testTestSkill_whenCasting_returnsInProgress() {
        // given
        Skill<String> skill = defaultBuilder()
                .build();

        SkillManagerImpl manager = new SkillManagerImpl(mockSource);
        when(mockEntry.value()).thenAnswer(invocation -> skill);

        manager.castSkill(mockEntry);

        // when
        SkillResponse response = manager.testSkill(mockEntry);

        // then
        assertInstanceOf(SkillResponse.Failure.class, response);
        assertEquals(SkillResponse.inProgress(), response);
    }

    @Test
    void testCastSkill_whenNotCasting_andSuccess_startsCasting() {
        // given
        boolean[] startExecuted = {false};

        Skill<String> skill = defaultBuilder()
                .setStartBehavior(instance -> startExecuted[0] = true)
                .build();

        SkillManagerImpl manager = new SkillManagerImpl(mockSource);
        when(mockEntry.value()).thenAnswer(invocation -> skill);

        // when
        SkillResponse response = manager.castSkill(mockEntry);

        // then
        assertInstanceOf(SkillResponse.Success.class, response);
        assertTrue(manager.isCasting());
        assertTrue(startExecuted[0]);
    }

    @Test
    void testCastSkill_whenNotCasting_andStateCreationError_returnsError() {
        // given
        Skill<String> skill = defaultBuilder()
                .setStateFactory(context ->
                        SkillStateCreationResult.error(SkillResponse.unavailable())
                )
                .build();

        SkillManagerImpl manager = new SkillManagerImpl(mockSource);
        when(mockEntry.value()).thenAnswer(invocation -> skill);

        // when
        SkillResponse response = manager.castSkill(mockEntry);

        // then
        assertInstanceOf(SkillResponse.Failure.class, response);
        assertFalse(manager.isCasting());
    }

    @Test
    void testCastSkill_whenCasting_returnsInProgress() {
        // given
        Skill<String> skill = defaultBuilder()
                .build();

        SkillManagerImpl manager = new SkillManagerImpl(mockSource);
        when(mockEntry.value()).thenAnswer(invocation -> skill);

        manager.castSkill(mockEntry);

        // when
        SkillResponse response = manager.castSkill(mockEntry);

        // then
        assertInstanceOf(SkillResponse.Failure.class, response);
        assertEquals(SkillResponse.inProgress(), response);
    }

    @Test
    void testCastSkill_withZeroDuration_executesCompleteAndEndsImmediately() {
        // given
        boolean[] executed = {false, false, false};

        Skill<String> skill = defaultBuilder()
                .setDurationProvider(SkillDurationProvider.constant(0))
                .setStartBehavior(instance -> executed[0] = true)
                .setCompleteBehavior(instance -> executed[1] = true)
                .setEndBehavior(instance -> executed[2] = true)
                .build();

        SkillManagerImpl manager = new SkillManagerImpl(mockSource);
        when(mockEntry.value()).thenAnswer(invocation -> skill);

        // when
        manager.castSkill(mockEntry);

        // then
        assertFalse(manager.isCasting());
        assertTrue(executed[0]);
        assertTrue(executed[1]);
        assertTrue(executed[2]);
    }

    @Test
    void testUpdate_decrementsDurationAndExecutesTick() {
        // given
        int[] tickCount = {0};

        Skill<String> skill = defaultBuilder()
                .setDurationProvider(SkillDurationProvider.constant(2))
                .setTickBehavior(instance -> tickCount[0]++)
                .build();

        SkillManagerImpl manager = new SkillManagerImpl(mockSource);
        when(mockEntry.value()).thenAnswer(invocation -> skill);

        manager.castSkill(mockEntry);
        SkillInstance<String> instance = manager.getCastingInstance(mockEntry);

        assertNotNull(instance);
        assertEquals(2, instance.getDuration());

        // when
        manager.update();

        // then
        assertEquals(1, instance.getDuration());
        assertEquals(1, tickCount[0]);
        assertTrue(manager.isCasting());

        // when
        manager.update();

        // then
        assertEquals(0, instance.getDuration());
        assertEquals(2, tickCount[0]);
        assertFalse(manager.isCasting());
    }

    @Test
    void testCancelCasting_whenNotCasting_returnsFalse() {
        // given
        SkillManagerImpl manager = new SkillManagerImpl(mockSource);

        // when
        boolean result = manager.cancelCasting();

        // then
        assertFalse(result);
    }

    @Test
    void testCancelCasting_whenCasting_andCancelAllowed_returnsTrue() {
        // given
        Skill<String> skill = defaultBuilder()
                .setCancelHandler(SkillCancelHandler.alwaysAllow())
                .build();

        SkillManagerImpl manager = new SkillManagerImpl(mockSource);
        when(mockEntry.value()).thenAnswer(invocation -> skill);

        manager.castSkill(mockEntry);
        assertTrue(manager.isCasting());

        // when
        boolean result = manager.cancelCasting();

        // then
        assertTrue(result);
        assertFalse(manager.isCasting());
    }

    @Test
    void testCancelCasting_whenCasting_andCancelDenied_returnsFalse() {
        // given
        Skill<String> skill = defaultBuilder()
                .setCancelHandler(SkillCancelHandler.alwaysDeny())
                .build();

        SkillManagerImpl manager = new SkillManagerImpl(mockSource);
        when(mockEntry.value()).thenAnswer(invocation -> skill);

        manager.castSkill(mockEntry);
        assertTrue(manager.isCasting());

        // when
        boolean result = manager.cancelCasting();

        // then
        assertFalse(result);
        assertTrue(manager.isCasting());
    }

    @Test
    void testInterruptCasting_whenNotCasting_returnsFalse() {
        // given
        SkillManagerImpl manager = new SkillManagerImpl(mockSource);

        // when
        boolean result = manager.interruptCasting();

        // then
        assertFalse(result);
    }

    @Test
    void testInterruptCasting_whenCasting_andInterruptAllowed_returnsTrue() {
        // given
        Skill<String> skill = defaultBuilder()
                .setInterruptHandler(SkillInterruptHandler.alwaysAllow())
                .build();

        SkillManagerImpl manager = new SkillManagerImpl(mockSource);
        when(mockEntry.value()).thenAnswer(invocation -> skill);

        manager.castSkill(mockEntry);
        assertTrue(manager.isCasting());

        // when
        boolean result = manager.interruptCasting();

        // then
        assertTrue(result);
        assertFalse(manager.isCasting());
    }

    @Test
    void testInterruptCasting_whenCasting_andInterruptDenied_returnsFalse() {
        // given
        Skill<String> skill = defaultBuilder()
                .setInterruptHandler(SkillInterruptHandler.alwaysDeny())
                .build();

        SkillManagerImpl manager = new SkillManagerImpl(mockSource);
        when(mockEntry.value()).thenAnswer(invocation -> skill);

        manager.castSkill(mockEntry);
        assertTrue(manager.isCasting());

        // when
        boolean result = manager.interruptCasting();

        // then
        assertFalse(result);
        assertTrue(manager.isCasting());
    }

    @Test
    void testTerminateCasting_whenNotCasting_returnsFalse() {
        // given
        SkillManagerImpl manager = new SkillManagerImpl(mockSource);

        // when
        boolean result = manager.terminateCasting();

        // then
        assertFalse(result);
    }

    @Test
    void testTerminateCasting_whenCasting_returnsTrue() {
        // given
        boolean[] endExecuted = {false};

        Skill<String> skill = defaultBuilder()
                .setEndBehavior(instance -> endExecuted[0] = true)
                .build();

        SkillManagerImpl manager = new SkillManagerImpl(mockSource);
        when(mockEntry.value()).thenAnswer(invocation -> skill);

        manager.castSkill(mockEntry);
        assertTrue(manager.isCasting());

        // when
        boolean result = manager.terminateCasting();

        // then
        assertTrue(result);
        assertFalse(manager.isCasting());
        assertTrue(endExecuted[0]);
    }

    private Skill.Builder<String> defaultBuilder() {
        return Skill.<String>builder()
                .setCondition(context -> SkillResponse.success())
                .setDurationProvider(
                        SkillDurationProvider.constant(50)
                )
                .setStateFactory(
                        SkillStateFactory.constant("test-state")
                );
    }
}
