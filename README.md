# tome

A barebone library for creating server-side skills. Provides basic frameworks for skill casting, cooldowns, and
containers. Does not include functional features like entity targeting.

> Note: Until version 1.0.0, the API may change without prior notice.

## Gradle Setup

### Kotlin

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    modImplementation("io.github.kouvali:tome:YOUR_VERSION")
}
```

### Groovy

```groovy
repositories {
    mavenCentral()
}

dependencies {
    modImplementation "io.github.kouvali:tome:YOUR_VERSION"
}
```

## Usage

Here's a quick example of skill creation:

### Java

```java
Skill<Vec3> exampleSkill = Skill.<Vec3>builder()
        .setStateFactory(
                SkillStateFactory.alwaysOk(context ->
                        context.getSource().getForward()
                )
        )
        .setTickBehavior(instance -> {
            instance.getSource().setDeltaMovement(instance.getState());
            instance.getSource().hurtMarked = true;
        })
        .setCompleteBehavior(instance -> {
            instance.getSource().setDeltaMovement(Vec3.ZERO);
            instance.getSource().hurtMarked = true;
        })
        .setInterruptPredicate(instance ->
                !instance.getSource().isInvulnerable()
        )
        .setDurationProvider(
                SkillDurationProvider.constant(20)
        )
        .build();
```

### Kotlin

```kotlin
val exampleSkill: Skill<Vec3> = Skill {
    alwaysOkStateFactory {
        source.forward
    }

    tickBehavior {
        source.deltaMovement = state
        source.hurtMarked = true
    }

    completeBehavior {
        source.deltaMovement = Vec3.ZERO
        source.hurtMarked = true
    }

    interruptPredicate {
        !source.isInvulnerable
    }

    constantDurationProvider(20)
}
```