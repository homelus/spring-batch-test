package jun.practice.partition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class SharedNos {

    private List<Integer> nos = new ArrayList<>();
    private List<String> checkNos = new CopyOnWriteArrayList<>();
    private AtomicInteger count = new AtomicInteger();

    public AtomicInteger getCount() {
        return count;
    }

    public void setCount(AtomicInteger count) {
        this.count = count;
    }

    public List<Integer> getNos() {
        return nos;
    }

    public void setNos(List<Integer> nos) {
        this.nos = nos;
    }

    public List<String> getCheckNos() {
        return checkNos;
    }

    public void setCheckNos(List<String> checkNos) {
        this.checkNos = checkNos;
    }
}
