package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

inline fun skillRemoveCallback(crossinline block: SkillContext<*>.() -> Unit): SkillRemoveCallback =
    SkillRemoveCallback { instance -> instance.block() }

fun noOpSkillRemoveCallback(): SkillRemoveCallback = SkillRemoveCallback.noOp()