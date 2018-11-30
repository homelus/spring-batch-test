package jun.practice.partition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SharedNos {

    private List<Integer> nos = new ArrayList<>();
    private List<String> checkNos = new CopyOnWriteArrayList<>();

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
