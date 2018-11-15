package jun.example.config.readNwrite.fileExample;

import org.springframework.batch.item.ItemProcessor;

public class PlayerProcessor implements ItemProcessor<Player, String> {
    @Override
    public String process(Player player) throws Exception {
        player.setLastName(player.getLastName() + "_JUN");
        return player.toString();
    }
}
