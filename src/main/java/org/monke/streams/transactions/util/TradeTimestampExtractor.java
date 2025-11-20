package org.monke.streams.transactions.util;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.LogAndSkipOnInvalidTimestamp;
import org.monke.streams.transactions.model.Trade;

public class TradeTimestampExtractor extends LogAndSkipOnInvalidTimestamp {

    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        Trade trade = (Trade) record.value();

        if (trade != null && trade.transactionDate() != null) {
            return trade.transactionDate();
        }

        return partitionTime; // Fallback.
    }
}
