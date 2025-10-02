package org.kouv.tome.api.skill.state;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kouv.tome.api.skill.SkillResponse;
import org.kouv.tome.test.FabricExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(FabricExtension.class)
class SkillStateCreationResultTest {
    @Test
    void testMap_onOk_transformsState() {
        // given
        SkillStateCreationResult.Ok<String> okResult = SkillStateCreationResult.ok("hello");

        // when
        SkillStateCreationResult<Integer> mappedResult = okResult.map(String::length);

        // then
        SkillStateCreationResult.Ok<?> ok =
                assertInstanceOf(SkillStateCreationResult.Ok.class, mappedResult);
        assertEquals(5, ok.state());
    }

    @Test
    void testMap_onError_returnsError() {
        // given
        SkillResponse.Failure failure = SkillResponse.unavailable();
        SkillStateCreationResult.Error<String> errorResult = SkillStateCreationResult.error(failure);

        // when
        SkillStateCreationResult<Integer> mappedResult = errorResult.map(String::length);

        // then
        SkillStateCreationResult.Error<?> error =
                assertInstanceOf(SkillStateCreationResult.Error.class, mappedResult);
        assertEquals(failure, error.failure());
    }

    @Test
    void testFlatMap_onOk_transformsState() {
        // given
        SkillStateCreationResult.Ok<String> okResult = SkillStateCreationResult.ok("hello");

        // when
        SkillStateCreationResult<Integer> flatMappedResult = okResult.flatMap(string ->
                SkillStateCreationResult.ok(string.length())
        );

        // then
        SkillStateCreationResult.Ok<?> ok =
                assertInstanceOf(SkillStateCreationResult.Ok.class, flatMappedResult);
        assertEquals(5, ok.state());
    }

    @Test
    void testFlatMap_onOk_returnsError() {
        // given
        SkillStateCreationResult.Ok<String> okResult = SkillStateCreationResult.ok("hello");
        SkillResponse.Failure failure = SkillResponse.unavailable();

        // when
        SkillStateCreationResult<Integer> flatMappedResult = okResult.flatMap(string ->
                SkillStateCreationResult.error(failure)
        );

        // then
        SkillStateCreationResult.Error<?> error =
                assertInstanceOf(SkillStateCreationResult.Error.class, flatMappedResult);
        assertEquals(failure, error.failure());
    }

    @Test
    void testFlatMap_onError_returnsError() {
        // given
        SkillResponse.Failure failure = SkillResponse.unavailable();
        SkillStateCreationResult.Error<String> errorResult = SkillStateCreationResult.error(failure);

        // when
        SkillStateCreationResult<Integer> flatMappedResult = errorResult.flatMap(string ->
                SkillStateCreationResult.ok(string.length())
        );

        // then
        SkillStateCreationResult.Error<?> error =
                assertInstanceOf(SkillStateCreationResult.Error.class, flatMappedResult);
        assertEquals(failure, error.failure());
    }

    @Test
    void testMap_chaining() {
        // given
        SkillStateCreationResult.Ok<String> okResult = SkillStateCreationResult.ok("hello");

        // when
        SkillStateCreationResult<String> chainedResult = okResult
                .map(String::toUpperCase)
                .map(string -> string + "!")
                .map(string -> string + " WORLD");

        // then
        SkillStateCreationResult.Ok<?> ok =
                assertInstanceOf(SkillStateCreationResult.Ok.class, chainedResult);
        assertEquals("HELLO! WORLD", ok.state());
    }

    @Test
    void testFlatMap_chaining() {
        // given
        SkillStateCreationResult.Ok<String> okResult = SkillStateCreationResult.ok("hello");

        // when
        SkillStateCreationResult<String> chainedResult = okResult
                .flatMap(string -> SkillStateCreationResult.ok(string.toUpperCase()))
                .flatMap(string -> SkillStateCreationResult.ok(string + "!"))
                .flatMap(string -> SkillStateCreationResult.ok(string + " WORLD"));

        // then
        SkillStateCreationResult.Ok<?> ok =
                assertInstanceOf(SkillStateCreationResult.Ok.class, chainedResult);
        assertEquals("HELLO! WORLD", ok.state());
    }

    @Test
    void testMap_and_flatMap_combination() {
        // given
        SkillStateCreationResult.Ok<String> okResult = SkillStateCreationResult.ok("hello");

        // when
        SkillStateCreationResult<Integer> result = okResult
                .map(String::toUpperCase)
                .flatMap(string -> SkillStateCreationResult.ok(string.length()))
                .map(length -> length * 2);

        // then
        SkillStateCreationResult.Ok<?> ok =
                assertInstanceOf(SkillStateCreationResult.Ok.class, result);
        assertEquals(10, ok.state());
    }

    @Test
    void testMap_afterFlatMapError_returnsError() {
        // given
        SkillResponse.Failure failure = SkillResponse.unavailable();
        SkillStateCreationResult.Ok<String> okResult = SkillStateCreationResult.ok("hello");

        // when
        SkillStateCreationResult<Integer> result = okResult
                .flatMap(string -> SkillStateCreationResult.<Integer>error(failure))
                .map(length -> length * 2);

        // then
        SkillStateCreationResult.Error<?> error =
                assertInstanceOf(SkillStateCreationResult.Error.class, result);
        assertEquals(failure, error.failure());
    }
}
