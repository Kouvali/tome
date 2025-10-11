package org.kouv.tome.impl.skill.manager;

import net.minecraft.registry.entry.RegistryEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.test.FabricExtension;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({FabricExtension.class, MockitoExtension.class})
class SkillContainerImplTest {
    @Mock
    private RegistryEntry<? extends Skill<?>> mockSkill1;
    @Mock
    private RegistryEntry<? extends Skill<?>> mockSkill2;
    @Mock
    private RegistryEntry<? extends Skill<?>> mockSkill3;

    @Test
    void testHasSkill_whenSkillPresent_returnsTrue() {
        // given
        SkillContainerImpl container = new SkillContainerImpl();
        container.addSkill(mockSkill1);

        // when
        boolean result = container.hasSkill(mockSkill1);

        // then
        assertTrue(result);
    }

    @Test
    void testHasSkill_whenSkillNotPresent_returnsFalse() {
        // given
        SkillContainerImpl container = new SkillContainerImpl();

        // when
        boolean result = container.hasSkill(mockSkill1);

        // then
        assertFalse(result);
    }

    @Test
    void testAddSkill_whenSkillNotPresent_returnsTrue() {
        // given
        SkillContainerImpl container = new SkillContainerImpl();

        // when
        boolean result = container.addSkill(mockSkill1);

        // then
        assertTrue(result);
        assertTrue(container.hasSkill(mockSkill1));
    }

    @Test
    void testAddSkill_whenSkillAlreadyPresent_returnsFalse() {
        // given
        SkillContainerImpl container = new SkillContainerImpl();
        container.addSkill(mockSkill1);

        // when
        boolean result = container.addSkill(mockSkill1);

        // then
        assertFalse(result);
        assertEquals(1, container.getSkills().size());
    }

    @Test
    void testRemoveSkill_whenSkillPresent_returnsTrue() {
        // given
        SkillContainerImpl container = new SkillContainerImpl();
        container.addSkill(mockSkill1);

        // when
        boolean result = container.removeSkill(mockSkill1);

        // then
        assertTrue(result);
        assertFalse(container.hasSkill(mockSkill1));
        assertTrue(container.getSkills().isEmpty());
    }

    @Test
    void testRemoveSkill_whenSkillNotPresent_returnsFalse() {
        // given
        SkillContainerImpl container = new SkillContainerImpl();

        // when
        boolean result = container.removeSkill(mockSkill1);

        // then
        assertFalse(result);
    }

    @Test
    void testAddMultipleSkills() {
        // given
        SkillContainerImpl container = new SkillContainerImpl();

        // when
        container.addSkill(mockSkill1);
        container.addSkill(mockSkill2);

        // then
        assertEquals(2, container.getSkills().size());
        assertTrue(container.hasSkill(mockSkill1));
        assertTrue(container.hasSkill(mockSkill2));
        assertFalse(container.hasSkill(mockSkill3));
    }

    @Test
    void testRemoveMultipleSkills() {
        // given
        SkillContainerImpl container = new SkillContainerImpl();
        container.addSkill(mockSkill1);
        container.addSkill(mockSkill2);
        container.addSkill(mockSkill3);

        // when
        container.removeSkill(mockSkill1);
        container.removeSkill(mockSkill2);

        // then
        assertEquals(1, container.getSkills().size());
        assertFalse(container.hasSkill(mockSkill1));
        assertFalse(container.hasSkill(mockSkill2));
        assertTrue(container.hasSkill(mockSkill3));
    }
}
