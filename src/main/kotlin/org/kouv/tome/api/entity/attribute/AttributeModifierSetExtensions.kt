package org.kouv.tome.api.entity.attribute

fun AttributeModifierSet(builderAction: AttributeModifierSet.Builder.() -> Unit): AttributeModifierSet =
    AttributeModifierSet.builder().apply(builderAction).build()