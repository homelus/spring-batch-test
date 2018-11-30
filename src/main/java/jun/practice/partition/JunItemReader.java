package jun.practice.partition;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.partition;
import static java.util.stream.Collectors.toList;

public class JunItemReader implements ItemReader<List<String>> {

    @Autowired
    private SharedNos sharedNos;

    private int loopCnt = 0;
    private List<List<Integer>> junNos = new ArrayList<>();

    public JunItemReader(Integer startNo, Integer endNo, SharedNos sharedNos) {

        List<Integer> junAllNos = sharedNos.getNos();
        System.out.println("[" + Thread.currentThread().getName() + "] - READ INITIALIZED -junNo: " + startNo + ", endNo: " + endNo + ", jun All Nos Size: " + junAllNos.size());
        List<Integer> partitionNos = junAllNos.stream().filter(no -> no >= startNo).filter(no -> no <= endNo).collect(Collectors.toList());

        junNos = partition(partitionNos, 10);
    }

    @Override
    public List<String> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (junNos.isEmpty() || junNos.size() <= loopCnt) {
            return null;
        }

        System.out.println("[" + Thread.currentThread().getName() + "] - READ - jun read Nos : " + junNos.get(loopCnt));

        return junNos.get(loopCnt++).stream().map(no -> "JunNo_" + no).collect(toList());
    }
}
