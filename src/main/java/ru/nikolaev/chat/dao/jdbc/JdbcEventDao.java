package ru.nikolaev.chat.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.nikolaev.chat.dao.EventDao;
import ru.nikolaev.chat.entity.Event;
import ru.nikolaev.chat.enums.EventType;
import ru.nikolaev.chat.entity.User;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcEventDao implements EventDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Environment env;


    @Override
    public int sendEvent(User owner, EventType eventType, String message, String ip) {
        return sendEvent(owner, eventType, message, ip, null);
    }

    @Override
    public int sendEvent(User owner, EventType eventType, String message, String ip, User assignee) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("insert into EVENT (owner_id,assigne_id,event_type_id,message,ip) values(?,?,?,?,?)", new String[]{"id"});
            if (owner != null) {
                ps.setLong(1, owner.getId());
            } else {
                ps.setLong(1, Types.NULL);
            }
            if (assignee != null) {
                ps.setLong(2, assignee.getId());
            } else {
                ps.setLong(2, Types.NULL);
            }
            ps.setInt(3, eventType.id());
            ps.setString(4, message);
            ps.setString(5, ip);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public List<Event> getLastEvents(int count) {
        List<Event> events = new ArrayList<>();

        return events;
    }
}
