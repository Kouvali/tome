package org.kouv.tome.api.entity.attribute

inline fun attributeModifierSet(builderAction: AttributeModifierSet.Builder.() -> Unit): AttributeModifierSet =
    AttributeModifierSet.builder().apply(builderAction).build()