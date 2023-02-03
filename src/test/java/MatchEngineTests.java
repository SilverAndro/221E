import io.github.silverandro._221e.impl._221EMatchEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MatchEngineTests {
    @Test
    public void simpleEquals() {
        _221EMatchEngine.matchMap.clear();
        _221EMatchEngine.matchMap.put("test", "1");
        Assertions.assertTrue(_221EMatchEngine.isMatch("test:1"));
    }

    @Test
    public void simpleInvert() {
        _221EMatchEngine.matchMap.clear();
        _221EMatchEngine.matchMap.put("test", "1");
        Assertions.assertFalse(_221EMatchEngine.isMatch("!test:1"));
    }

    @Test
    public void simpleDoubleInvert() {
        _221EMatchEngine.matchMap.clear();
        _221EMatchEngine.matchMap.put("test", "1");
        Assertions.assertTrue(_221EMatchEngine.isMatch("!!test:1"));
    }

    @Test
    public void simpleExplicitAnd() {
        _221EMatchEngine.matchMap.clear();
        _221EMatchEngine.matchMap.put("test", "1");
        _221EMatchEngine.matchMap.put("other", "example");
        Assertions.assertTrue(_221EMatchEngine.isMatch("test:1 & other:example"));
    }

    @Test
    public void simpleExplicitAndWithInvert() {
        _221EMatchEngine.matchMap.clear();
        _221EMatchEngine.matchMap.put("test", "1");
        _221EMatchEngine.matchMap.put("other", "example");
        Assertions.assertFalse(_221EMatchEngine.isMatch("!test:1 & other:example"));
    }

    @Test
    public void simpleOr() {
        _221EMatchEngine.matchMap.clear();
        _221EMatchEngine.matchMap.put("test", "1");
        _221EMatchEngine.matchMap.put("other", "example");
        Assertions.assertTrue(_221EMatchEngine.isMatch("test:1 | other:example"));
    }

    @Test
    public void simpleOrWithInvert() {
        _221EMatchEngine.matchMap.clear();
        _221EMatchEngine.matchMap.put("test", "1");
        _221EMatchEngine.matchMap.put("other", "example");
        Assertions.assertTrue(_221EMatchEngine.isMatch("!test:1 | other:example"));
    }

    @Test
    public void simpleImplicitAnd() {
        _221EMatchEngine.matchMap.clear();
        _221EMatchEngine.matchMap.put("test", "1");
        _221EMatchEngine.matchMap.put("other", "example");
        Assertions.assertTrue(_221EMatchEngine.isMatch("test:1 other:example"));
    }

    @Test
    public void simpleGroup() {
        _221EMatchEngine.matchMap.clear();
        _221EMatchEngine.matchMap.put("test", "1");
        _221EMatchEngine.matchMap.put("other", "example");
        Assertions.assertTrue(_221EMatchEngine.isMatch("(test:1 | other:example)"));
    }

    @Test
    public void invertGroup() {
        _221EMatchEngine.matchMap.clear();
        _221EMatchEngine.matchMap.put("test", "1");
        _221EMatchEngine.matchMap.put("other", "example");
        Assertions.assertFalse(_221EMatchEngine.isMatch("!(test:1 | other:example)"));
    }

    @Test
    public void simpleGreaterThan() {
        _221EMatchEngine.matchMap.clear();
        _221EMatchEngine.matchMap.put("version", "1.0.0");
        Assertions.assertTrue(_221EMatchEngine.isMatch("version:>0.1.0"));
    }

    @Test
    public void simpleLessThan() {
        _221EMatchEngine.matchMap.clear();
        _221EMatchEngine.matchMap.put("version", "1.0.0");
        Assertions.assertTrue(_221EMatchEngine.isMatch("version:<1.1.0"));
    }

    @Test
    public void rangeCheck() {
        _221EMatchEngine.matchMap.clear();
        _221EMatchEngine.matchMap.put("version", "1.0.0");
        Assertions.assertTrue(_221EMatchEngine.isMatch("version:>0.1.0 version:<1.1.0"));
    }

    @Test
    public void isNotGreaterThan() {
        _221EMatchEngine.matchMap.clear();
        _221EMatchEngine.matchMap.put("version", "1.0.0");
        Assertions.assertTrue(_221EMatchEngine.isMatch("!version:>1.1.0"));
    }

    @Test
    public void greaterThanOrEqualTo() {
        _221EMatchEngine.matchMap.clear();
        _221EMatchEngine.matchMap.put("version", "1.0.0");
        Assertions.assertTrue(_221EMatchEngine.isMatch("version:>=1.0.0"));
    }

    @Test
    public void infiniteReduction() {
        _221EMatchEngine.matchMap.clear();
        Assertions.assertThrows(IllegalStateException.class, () -> _221EMatchEngine.isMatch("(!)"));
    }
}
