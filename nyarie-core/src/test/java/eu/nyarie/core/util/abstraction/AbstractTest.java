package eu.nyarie.core.util.abstraction;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/// Base abstract class for all tests
@ExtendWith(MockitoExtension.class)
public abstract class AbstractTest extends Assertions {
}
