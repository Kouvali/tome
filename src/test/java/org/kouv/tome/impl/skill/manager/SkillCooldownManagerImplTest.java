package org.kouv.tome.impl.skill.manager;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.test.FabricExtension;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({FabricExtension.class, MockitoExtension.class})
class SkillCooldownManagerImplTest {
    private final Identifier id1 = Identifier.of("test", "skill_1");
    private final Identifier id2 = Identifier.of("test", "skill_2");

    @Mock
    private RegistryEntry<? extends Skill<?>> mockSkill;
    @Mock
    private RegistryKey<? extends Skill<?>> mockKey;

    @Test
    void testIsCoolingDown_byRegistryEntry() {
        // given
        when(mockSkill.getKey()).thenAnswer(invocation -> Optional.of(mockKey));
        when(mockKey.getValue()).thenAnswer(invocation -> id1);

        SkillCooldownManagerImpl manager = new SkillCooldownManagerImpl();
        manager.setCooldown(mockSkill, 100);

        // when
        boolean result = manager.isCoolingDown(mockSkill);

        // then
        assertTrue(result);
    }

    @Test
    void testIsCoolingDown_byIdentifier() {
        // given
        SkillCooldownManagerImpl manager = new SkillCooldownManagerImpl();
        manager.setCooldown(id1, 100);

        // when
        boolean result = manager.isCoolingDown(id1);

        // then
        assertTrue(result);
    }

    @Test
    void testIsCoolingDown_whenNoCooldown_returnsFalse() {
        // given
        SkillCooldownManagerImpl manager = new SkillCooldownManagerImpl();

        // when
        boolean result = manager.isCoolingDown(id1);

        // then
        assertFalse(result);
    }

    @Test
    void testIsCoolingDown_whenCooldownZero_returnsFalse() {
        // given
        SkillCooldownManagerImpl manager = new SkillCooldownManagerImpl();
        manager.setCooldown(id1, 0);

        // when
        boolean result = manager.isCoolingDown(id1);

        // then
        assertFalse(result);
    }

    @Test
    void testGetCooldown_byRegistryEntry() {
        // given
        when(mockSkill.getKey()).thenAnswer(invocation -> Optional.of(mockKey));
        when(mockKey.getValue()).thenAnswer(invocation -> id1);

        SkillCooldownManagerImpl manager = new SkillCooldownManagerImpl();
        manager.setCooldown(mockSkill, 100);

        // when
        int cooldown = manager.getCooldown(mockSkill);

        // then
        assertEquals(100, cooldown);
    }

    @Test
    void testGetCooldown_byIdentifier() {
        // given
        SkillCooldownManagerImpl manager = new SkillCooldownManagerImpl();
        manager.setCooldown(id1, 100);

        // when
        int cooldown = manager.getCooldown(id1);

        // then
        assertEquals(100, cooldown);
    }

    @Test
    void testGetCooldown_whenNoCooldown_returnsZero() {
        // given
        SkillCooldownManagerImpl manager = new SkillCooldownManagerImpl();

        // when
        int cooldown = manager.getCooldown(id1);

        // then
        assertEquals(0, cooldown);
    }

    @Test
    void testSetCooldown_byRegistryEntry() {
        // given
        when(mockSkill.getKey()).thenAnswer(invocation -> Optional.of(mockKey));
        when(mockKey.getValue()).thenAnswer(invocation -> id1);

        SkillCooldownManagerImpl manager = new SkillCooldownManagerImpl();

        // when
        manager.setCooldown(mockSkill, 100);

        // then
        assertEquals(100, manager.getCooldown(id1));
        assertTrue(manager.isCoolingDown(id1));
    }

    @Test
    void testSetCooldown_byIdentifier() {
        // given
        SkillCooldownManagerImpl manager = new SkillCooldownManagerImpl();

        // when
        manager.setCooldown(id1, 100);

        // then
        assertEquals(100, manager.getCooldown(id1));
        assertTrue(manager.isCoolingDown(id1));
    }

    @Test
    void testSetCooldown_zeroRemovesEntry() {
        // given
        SkillCooldownManagerImpl manager = new SkillCooldownManagerImpl();
        manager.setCooldown(id1, 100);
        assertTrue(manager.isCoolingDown(id1));

        // when
        manager.setCooldown(id1, 0);

        // then
        assertEquals(0, manager.getCooldown(id1));
        assertFalse(manager.isCoolingDown(id1));
    }

    @Test
    void testSetCooldown_negativeRemovesEntry() {
        // given
        SkillCooldownManagerImpl manager = new SkillCooldownManagerImpl();
        manager.setCooldown(id1, 100);
        assertTrue(manager.isCoolingDown(id1));

        // when
        manager.setCooldown(id1, -10);

        // then
        assertEquals(0, manager.getCooldown(id1));
        assertFalse(manager.isCoolingDown(id1));
    }

    @Test
    void testUpdate_decrementsCooldowns() {
        // given
        SkillCooldownManagerImpl manager = new SkillCooldownManagerImpl();
        manager.setCooldown(id1, 100);
        manager.setCooldown(id2, 1);

        // when
        manager.update();

        // then
        assertEquals(99, manager.getCooldown(id1));
        assertEquals(0, manager.getCooldown(id2));
        assertTrue(manager.isCoolingDown(id1));
        assertFalse(manager.isCoolingDown(id2));
    }

    @Test
    void testUpdate_multipleUpdates() {
        // given
        SkillCooldownManagerImpl manager = new SkillCooldownManagerImpl();
        manager.setCooldown(id1, 3);

        // when
        manager.update();
        manager.update();
        manager.update();

        // then
        assertEquals(0, manager.getCooldown(id1));
        assertFalse(manager.isCoolingDown(id1));
    }
}
