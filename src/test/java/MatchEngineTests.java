import io.github.silverandro._221e.impl.MatchEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MatchEngineTests {
    @BeforeEach
    public void initMatchEngine() {
        MatchEngine.deleteAll();
    }

    @Test
    public void simpleEquals() {
        MatchEngine.register("test", "1");
        Assertions.assertTrue(MatchEngine.isMatch("test:1"));
    }

    @Test
    public void simpleInvert() {
        MatchEngine.register("test", "1");
        Assertions.assertFalse(MatchEngine.isMatch("!test:1"));
    }

    @Test
    public void simpleDoubleInvert() {
        MatchEngine.register("test", "1");
        Assertions.assertTrue(MatchEngine.isMatch("!!test:1"));
    }

    @Test
    public void simpleExplicitAnd() {
        MatchEngine.register("test", "1");
        MatchEngine.register("other", "example");
        Assertions.assertTrue(MatchEngine.isMatch("test:1 & other:example"));
    }

    @Test
    public void simpleExplicitAndWithInvert() {
        MatchEngine.register("test", "1");
        MatchEngine.register("other", "example");
        Assertions.assertFalse(MatchEngine.isMatch("!test:1 & other:example"));
    }

    @Test
    public void simpleOr() {
        MatchEngine.register("test", "1");
        MatchEngine.register("other", "example");
        Assertions.assertTrue(MatchEngine.isMatch("test:1 | other:example"));
    }

    @Test
    public void simpleOrWithInvert() {
        MatchEngine.register("test", "1");
        MatchEngine.register("other", "example");
        Assertions.assertTrue(MatchEngine.isMatch("!test:1 | other:example"));
    }

    @Test
    public void simpleImplicitAnd() {
        MatchEngine.register("test", "1");
        MatchEngine.register("other", "example");
        Assertions.assertTrue(MatchEngine.isMatch("test:1 other:example"));
    }

    @Test
    public void simpleGroup() {
        MatchEngine.register("test", "1");
        MatchEngine.register("other", "example");
        Assertions.assertTrue(MatchEngine.isMatch("(test:1 | other:example)"));
    }

    @Test
    public void invertGroup() {
        MatchEngine.register("test", "1");
        MatchEngine.register("other", "example");
        Assertions.assertFalse(MatchEngine.isMatch("!(test:1 | other:example)"));
    }

    @Test
    public void simpleGreaterThan() {
        MatchEngine.register("version", "1.0.0");
        Assertions.assertTrue(MatchEngine.isMatch("version:>0.1.0"));
    }

    @Test
    public void simpleLessThan() {
        MatchEngine.register("version", "1.0.0");
        Assertions.assertTrue(MatchEngine.isMatch("version:<1.1.0"));
    }

    @Test
    public void rangeCheck() {
        MatchEngine.register("version", "1.0.0");
        Assertions.assertTrue(MatchEngine.isMatch("version:>0.1.0 version:<1.1.0"));
    }

    @Test
    public void isNotGreaterThan() {
        MatchEngine.register("version", "1.0.0");
        Assertions.assertTrue(MatchEngine.isMatch("!version:>1.1.0"));
    }

    @Test
    public void greaterThanOrEqualTo() {
        MatchEngine.register("version", "1.0.0");
        Assertions.assertTrue(MatchEngine.isMatch("version:>=1.0.0"));
    }

    @Test
    public void infiniteReduction() {
        Assertions.assertThrows(IllegalStateException.class, () -> MatchEngine.isMatch("(!)"));
    }
}
