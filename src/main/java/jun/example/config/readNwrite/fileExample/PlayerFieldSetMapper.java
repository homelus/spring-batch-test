package jun.example.config.readNwrite.fileExample;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class PlayerFieldSetMapper implements FieldSetMapper<Player> {
    @Override
    public Player mapFieldSet(FieldSet fieldSet) throws BindException {
        Player player = new Player();
        player.setID(fieldSet.readString("ID"));
        player.setLastName(fieldSet.readString("lastName"));
        player.setFirstName(fieldSet.readString("firstName"));
        player.setPosition(fieldSet.readString("position"));
        player.setBirthYear(fieldSet.readInt("birthYear"));
        player.setDebutYear(fieldSet.readInt("debutYear"));
        return player;
    }
}
