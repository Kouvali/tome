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
Skill<Vec3d> exampleSkill = Skill.<Vec3d>builder()
        .setStateFactory(
                SkillStateFactory.alwaysOk(context ->
                        context.getSource().getRotationVector()
                )
        )
        .setTickBehavior(instance -> {
            instance.getSource().setVelocity(instance.getState());
            instance.getSource().velocityModified = true;
        })
        .setCompleteBehavior(instance -> {
            instance.getSource().setVelocity(Vec3d.ZERO);
            instance.getSource().velocityModified = true;
        })
        .setInterruptPredicate(instance ->
                !instance.getSource().isInvulnerable()
        )
        .setTotalDuration(10)
        .build();
```

### Kotlin

```kotlin
val exampleSkill: Skill<Vec3d> = Skill {
    alwaysOkStateFactory {
        source.rotationVector
    }

    tickBehavior {
        source.velocity = state
        source.velocityModified = true
    }

    completeBehavior {
        source.velocity = Vec3d.ZERO
        source.velocityModified = true
    }

    interruptPredicate {
        !source.isInvulnerable
    }

    totalDuration = 10
}
```