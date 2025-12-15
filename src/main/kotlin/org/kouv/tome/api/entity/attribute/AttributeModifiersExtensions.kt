package org.kouv.tome.api.entity.attribute

inline fun attributeModifiers(builderAction: AttributeModifiers.Builder.() -> Unit): AttributeModifiers =
    AttributeModifiers.builder().apply(builderAction).build()