package org.kouv.tome.api.entity.attribute;

import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public final class AttributeModifierTracker {
    private final AttributeContainer container;
    private final Set<Record> records = new HashSet<>();

    @ApiStatus.Internal
    public AttributeModifierTracker(AttributeContainer container) {
        this.container = Objects.requireNonNull(container);
    }

    public void applyModifier(
            RegistryEntry<EntityAttribute> attribute,
            double value,
            EntityAttributeModifier.Operation operation
    ) {
        Objects.requireNonNull(attribute);
        Objects.requireNonNull(operation);

        applyModifier(attribute, Identifier.of("tome", UUID.randomUUID().toString()), value, operation);
    }

    public void applyModifier(
            RegistryEntry<EntityAttribute> attribute,
            Identifier id,
            double value,
            EntityAttributeModifier.Operation operation
    ) {
        Objects.requireNonNull(attribute);
        Objects.requireNonNull(id);
        Objects.requireNonNull(operation);

        applyModifier(attribute, new EntityAttributeModifier(id, value, operation));
    }

    public void applyModifier(
            RegistryEntry<EntityAttribute> attribute,
            EntityAttributeModifier modifier
    ) {
        Objects.requireNonNull(attribute);
        Objects.requireNonNull(modifier);

        EntityAttributeInstance instance = container.getCustomInstance(attribute);
        if (instance != null) {
            instance.removeModifier(modifier);
            instance.addTemporaryModifier(modifier);

            records.add(new Record(attribute, modifier.id()));
        }
    }

    public void removeModifier(
            RegistryEntry<EntityAttribute> attribute,
            EntityAttributeModifier modifier
    ) {
        Objects.requireNonNull(attribute);
        Objects.requireNonNull(modifier);

        removeModifier(attribute, modifier.id());
    }

    public void removeModifier(
            RegistryEntry<EntityAttribute> attribute,
            Identifier id
    ) {
        Objects.requireNonNull(attribute);
        Objects.requireNonNull(id);

        EntityAttributeInstance instance = container.getCustomInstance(attribute);
        if (instance != null &&
                instance.removeModifier(id)
        ) {
            records.remove(new Record(attribute, id));
        }
    }

    public void clearModifiers() {
        for (Record record : records) {
            var instance = container.getCustomInstance(record.attribute());
            if (instance != null) {
                instance.removeModifier(record.id());
            }
        }

        records.clear();
    }

    private record Record(
            RegistryEntry<EntityAttribute> attribute,
            Identifier id
    ) {
        private Record {
            Objects.requireNonNull(attribute);
            Objects.requireNonNull(id);
        }
    }
}
