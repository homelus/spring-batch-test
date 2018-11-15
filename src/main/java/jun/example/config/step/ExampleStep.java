package jun.example.config.step;

import org.springframework.batch.core.Step;

public abstract class ExampleStep implements Step {

    @Override
    public String getName() {
        return "stepA";
    }

    @Override
    public boolean isAllowStartIfComplete() {
        return false;
    }

    @Override
    public int getStartLimit() {
        return Integer.MAX_VALUE;
    }

}
