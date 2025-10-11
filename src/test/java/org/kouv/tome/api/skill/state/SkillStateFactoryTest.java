package org.kouv.tome.api.skill.state;

import net.minecraft.entity.LivingEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kouv.tome.api.skill.SkillContext;
import org.kouv.tome.api.skill.SkillResponse;
import org.kouv.tome.test.FabricExtension;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({FabricExtension.class, MockitoExtension.class})
class SkillStateFactoryTest {
    @Mock
    private SkillContext<?> mockContext;
    @Mock
    private LivingEntity mockSource;

    @Test
    void testMap_transformsState() {
        // given
        SkillStateFactory<String> baseFactory = context -> SkillStateCreationResult.ok("hello");
        SkillStateFactory<Integer> mappedFactory = baseFactory.map((context, state) -> state.length());

        // when
        SkillStateCreationResult<Integer> result = mappedFactory.create(mockContext);

        // then
        SkillStateCreationResult.Ok<?> ok =
                assertInstanceOf(SkillStateCreationResult.Ok.class, result);
        assertEquals(5, ok.state());
    }

    @Test
    void testMap_usesContextInTransformation() {
        // given
        when(mockContext.getSource()).thenReturn(mockSource);
        when(mockSource.getAir()).thenReturn(32);

        SkillStateFactory<Integer> baseFactory = context -> SkillStateCreationResult.ok(64);
        SkillStateFactory<Integer> mappedFactory = baseFactory.map((context, state) ->
                state + context.getSource().getAir()
        );

        // when
        SkillStateCreationResult<Integer> result = mappedFactory.create(mockContext);

        // then
        SkillStateCreationResult.Ok<?> ok =
                assertInstanceOf(SkillStateCreationResult.Ok.class, result);
        assertEquals(96, ok.state());
    }

    @Test
    void testMap_onError_returnsError() {
        // given
        SkillResponse.Failure failure = SkillResponse.unavailable();
        SkillStateFactory<String> baseFactory = context -> SkillStateCreationResult.error(failure);
        SkillStateFactory<Integer> mappedFactory = baseFactory.map((context, state) -> state.length());

        // when
        SkillStateCreationResult<Integer> result = mappedFactory.create(mockContext);

        // then
        SkillStateCreationResult.Error<?> error =
                assertInstanceOf(SkillStateCreationResult.Error.class, result);
        assertEquals(failure, error.failure());
    }

    @Test
    void testFlatMap_transformsState() {
        // given
        SkillStateFactory<String> baseFactory = context -> SkillStateCreationResult.ok("hello");
        SkillStateFactory<Integer> flatMappedFactory = baseFactory.flatMap((context, state) ->
                SkillStateCreationResult.ok(state.length())
        );

        // when
        SkillStateCreationResult<Integer> result = flatMappedFactory.create(mockContext);

        // then
        SkillStateCreationResult.Ok<?> ok =
                assertInstanceOf(SkillStateCreationResult.Ok.class, result);
        assertEquals(5, ok.state());
    }

    @Test
    void testFlatMap_usesContextInTransformation() {
        // given
        when(mockContext.getSource()).thenReturn(mockSource);
        when(mockSource.getAir()).thenReturn(32);

        SkillStateFactory<Integer> baseFactory = context -> SkillStateCreationResult.ok(64);
        SkillStateFactory<Integer> mappedFactory = baseFactory.flatMap((context, state) ->
                SkillStateCreationResult.ok(state + context.getSource().getAir())
        );

        // when
        SkillStateCreationResult<Integer> result = mappedFactory.create(mockContext);

        // then
        SkillStateCreationResult.Ok<?> ok =
                assertInstanceOf(SkillStateCreationResult.Ok.class, result);
        assertEquals(96, ok.state());
    }

    @Test
    void testFlatMap_returnsError() {
        // given
        SkillResponse.Failure failure = SkillResponse.unavailable();
        SkillStateFactory<String> baseFactory = context -> SkillStateCreationResult.ok("hello");
        SkillStateFactory<Integer> flatMappedFactory = baseFactory.flatMap((context, state) ->
                SkillStateCreationResult.error(failure)
        );

        // when
        SkillStateCreationResult<Integer> result = flatMappedFactory.create(mockContext);

        // then
        SkillStateCreationResult.Error<?> error =
                assertInstanceOf(SkillStateCreationResult.Error.class, result);
        assertEquals(failure, error.failure());
    }

    @Test
    void testFlatMap_onError_returnsError() {
        // given
        SkillResponse.Failure failure = SkillResponse.unavailable();
        SkillStateFactory<String> baseFactory = context -> SkillStateCreationResult.error(failure);
        SkillStateFactory<Integer> flatMappedFactory = baseFactory.flatMap((context, state) ->
                SkillStateCreationResult.ok(state.length())
        );

        // when
        SkillStateCreationResult<Integer> result = flatMappedFactory.create(mockContext);

        // then
        SkillStateCreationResult.Error<?> error =
                assertInstanceOf(SkillStateCreationResult.Error.class, result);
        assertEquals(failure, error.failure());
    }

    @Test
    void testMap_chaining() {
        // given
        SkillStateFactory<String> factory = SkillStateFactory.constant("hello")
                .map((context, state) -> state.toUpperCase())
                .map((context, state) -> state + "!")
                .map((context, state) -> state + " WORLD");

        // when
        SkillStateCreationResult<String> result = factory.create(mockContext);

        // then
        SkillStateCreationResult.Ok<?> ok =
                assertInstanceOf(SkillStateCreationResult.Ok.class, result);
        assertEquals("HELLO! WORLD", ok.state());
    }

    @Test
    void testFlatMap_chaining() {
        // given
        SkillStateFactory<String> factory = SkillStateFactory.constant("hello")
                .flatMap((context, state) -> SkillStateCreationResult.ok(state.toUpperCase()))
                .flatMap((context, state) -> SkillStateCreationResult.ok(state + "!"))
                .flatMap((context, state) -> SkillStateCreationResult.ok(state + " WORLD"));

        // when
        SkillStateCreationResult<String> result = factory.create(mockContext);

        // then
        SkillStateCreationResult.Ok<?> ok =
                assertInstanceOf(SkillStateCreationResult.Ok.class, result);
        assertEquals("HELLO! WORLD", ok.state());
    }

    @Test
    void testMap_and_flatMap_combination() {
        // given
        SkillStateFactory<Integer> factory = SkillStateFactory.constant("hello")
                .map((context, state) -> state.toUpperCase())
                .flatMap((context, state) -> SkillStateCreationResult.ok(state.length()))
                .map((context, length) -> length * 2);

        // when
        SkillStateCreationResult<Integer> result = factory.create(mockContext);

        // then
        SkillStateCreationResult.Ok<?> ok =
                assertInstanceOf(SkillStateCreationResult.Ok.class, result);
        assertEquals(10, ok.state());
    }

    @Test
    void testMap_afterFlatMapError_returnsError() {
        // given
        SkillResponse.Failure failure = SkillResponse.unavailable();
        SkillStateFactory<String> factory = SkillStateFactory.constant("hello")
                .flatMap((context, state) -> SkillStateCreationResult.<String>error(failure))
                .map((context, state) -> state.toUpperCase());

        // when
        SkillStateCreationResult<String> result = factory.create(mockContext);

        // then
        SkillStateCreationResult.Error<?> error =
                assertInstanceOf(SkillStateCreationResult.Error.class, result);
        assertEquals(failure, error.failure());
    }
}
