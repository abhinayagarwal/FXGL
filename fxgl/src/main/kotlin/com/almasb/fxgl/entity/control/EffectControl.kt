/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package com.almasb.fxgl.entity.control

import com.almasb.fxgl.entity.Control
import com.almasb.fxgl.entity.Effect
import com.almasb.fxgl.entity.Entity
import com.almasb.fxgl.entity.component.TimeComponent

/**
 * Allows starting and ending an effect on an entity.
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
class EffectControl : Control() {

    private val effectTypes = arrayListOf<Class<out Effect>>()
    private val effects: MutableList<Effect> = arrayListOf()

    override fun onUpdate(entity: Entity, tpf: Double) {
        val iterator = effects.iterator()
        while (iterator.hasNext()) {
            val effect = iterator.next()

            // we compute the actual tpf because this entity may be under TimeComponent effect
            // which means tpf might actually be a fraction of TimeComponent, i.e. not real tpf
            // the issue is that then the effect length is shorter / longer than what it should be
            val tpfActual = tpf / entity.getComponentOptional(TimeComponent::class.java)
                    .map { it.value }
                    .orElse(1.0)

            effect.onUpdate(tpfActual)

            if (effect.isFinished) {
                iterator.remove()
                effectTypes.remove(effect.javaClass)
                effect.onEnd(entity)
            }
        }
    }

    /**
     * If the effect class is the same, the new effect overrides
     * the old one.
     */
    fun startEffect(effect: Effect) {
        if (effectTypes.contains(effect.javaClass)) {
            // we know effect is present
            val oldEffect = effects.find { it.javaClass == effect.javaClass }!!
            oldEffect.onEnd(entity)
            effects.remove(oldEffect)
        } else {
            effectTypes.add(effect.javaClass)
        }

        effects.add(effect)
        effect.start(entity)
    }

    fun endEffect(effect: Effect) {
        endEffect(effect.javaClass)
    }

    fun endEffect(effectClass: Class<out Effect>) {
        effectTypes.remove(effectClass)

        // we don't know if effect is present
        effects.find { it.javaClass == effectClass }?.let { effect ->
            effect.onEnd(entity)
            effects.remove(effect)
        }
    }

    fun endAllEffects() {
        effectTypes.clear()
        effects.forEach { it.onEnd(entity) }
        effects.clear()
    }
}