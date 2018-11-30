package jun.partition.example;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Person person = new Person();
        person.setFirstName(resultSet.getString("firstName"));
        person.setLastName(resultSet.getString("lastName"));
        person.setCity(resultSet.getString("city"));
        person.setId(resultSet.getInt("id"));
        return person;
    }
}
