package trades.model;

import org.junit.jupiter.api.Test;
import org.monke.streams.trades.model.InitialValues;
import org.monke.streams.trades.model.ShareValue;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class InitialValuesTest {

    @Test
    public void should_deserialize_into_table() {
        Map<String, ShareValue> table = InitialValues.table();

        assertThat(table.containsKey("AAPL")).isTrue();
        assertThat(table.get("AAPL").getClass()).isEqualTo(ShareValue.class);
    }
}
