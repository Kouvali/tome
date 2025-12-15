package org.kouv.tome.api.entity.attribute;

import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public final class AttributeModifiers {
    private static final AttributeModifiers EMPTY = new AttributeModifiers();

    private final Map<? extends RegistryEntry<EntityAttribute>, ? extends List<? extends EntityAttributeModifier>> attributeToModifiers;

    private AttributeModifiers(Map<? extends RegistryEntry<EntityAttribute>, ? extends List<? extends EntityAttributeModifier>> attributeToModifiers) {
        this.attributeToModifiers = Objects.requireNonNull(attributeToModifiers).entrySet()
                .stream()
                .collect(
                        Collectors.toUnmodifiableMap(
                                Map.Entry::getKey,
                                entry -> List.copyOf(entry.getValue())
                        )
                );
    }

    private AttributeModifiers() {
        this.attributeToModifiers = Map.of();
    }

    public static AttributeModifiers empty() {
        return EMPTY;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void applyTemporaryModifiers(AttributeContainer container) {
        Objects.requireNonNull(container);
        processModifiers(
                container,
                (instance, modifier) -> {
                    instance.removeModifier(modifier);
                    instance.addTemporaryModifier(modifier);
                }
        );
    }

    public void applyPersistentModifiers(AttributeContainer container) {
        Objects.requireNonNull(container);
        processModifiers(
                container,
                (instance, modifier) -> {
                    instance.removeModifier(modifier);
                    instance.addPersistentModifier(modifier);
                }
        );
    }

    public void removeModifiers(AttributeContainer container) {
        Objects.requireNonNull(container);
        processModifiers(
                container,
                EntityAttributeInstance::removeModifier
        );
    }

    private void processModifiers(
            AttributeContainer container,
            BiConsumer<? super EntityAttributeInstance, ? super EntityAttributeModifier> consumer
    ) {
        for (var entry : attributeToModifiers.entrySet()) {
            var instance = container.getCustomInstance(entry.getKey());
            if (instance != null) {
                for (EntityAttributeModifier modifier : entry.getValue()) {
                    consumer.accept(instance, modifier);
                }
            }
        }
    }

    public static final class Builder {
        private final Map<RegistryEntry<EntityAttribute>, List<EntityAttributeModifier>> attributeToModifiers = new HashMap<>();

        private Builder() {
        }

        public Builder addModifier(
                RegistryEntry<EntityAttribute> attribute,
                double value,
                EntityAttributeModifier.Operation operation
        ) {
            Objects.requireNonNull(attribute);
            Objects.requireNonNull(operation);
            return addModifier(attribute, Identifier.of("tome", UUID.randomUUID().toString()), value, operation);
        }

        public Builder addModifier(
                RegistryEntry<EntityAttribute> attribute,
                Identifier id,
                double value,
                EntityAttributeModifier.Operation operation
        ) {
            Objects.requireNonNull(attribute);
            Objects.requireNonNull(id);
            Objects.requireNonNull(operation);
            return addModifier(attribute, new EntityAttributeModifier(id, value, operation));
        }

        public Builder addModifier(
                RegistryEntry<EntityAttribute> attribute,
                EntityAttributeModifier modifier
        ) {
            Objects.requireNonNull(attribute);
            Objects.requireNonNull(modifier);
            attributeToModifiers.computeIfAbsent(attribute, key -> new ArrayList<>()).add(modifier);
            return this;
        }

        public AttributeModifiers build() {
            return new AttributeModifiers(attributeToModifiers);
        }
    }
}
