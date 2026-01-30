package org.kouv.tome.api.entity.attribute

fun attributeModifierSet(builderAction: AttributeModifierSet.Builder.() -> Unit): AttributeModifierSet =
    AttributeModifierSet.builder().apply(builderAction).build()