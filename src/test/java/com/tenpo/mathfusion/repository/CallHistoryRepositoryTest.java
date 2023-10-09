package com.tenpo.mathfusion.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;
import com.tenpo.mathfusion.model.CallHistory;
import org.junit.jupiter.api.Test;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class CallHistoryRepositoryTest {

    @Autowired
    private CallHistoryRepository callHistoryRepository;

    @Test
    public void testFindAllByOrderByTimestampDesc() {
        CallHistory call1 = new CallHistory();
        call1.setTimestamp(LocalDateTime.now().minusDays(1));
        call1.setEndpoint("/api/calculate");
        call1.setNumber1(1);
        call1.setNumber2(2);
        call1.setResult(3);

        callHistoryRepository.save(call1);

        CallHistory call2 = new CallHistory();
        call2.setTimestamp(LocalDateTime.now());
        call2.setEndpoint("/api/calculate");
        call2.setNumber1(4);
        call2.setNumber2(5);
        call2.setResult(9);

        callHistoryRepository.save(call2);

        Page<CallHistory> page = callHistoryRepository.findAllByOrderByTimestampDesc(PageRequest.of(0, 10));

        List<CallHistory> callList = page.getContent();
        assertEquals(2, callList.size());
    }
}
