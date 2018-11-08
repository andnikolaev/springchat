package ru.nikolaev.chat.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.nikolaev.chat.dao.EventDao;
import ru.nikolaev.chat.dao.jdbc.mapper.EventRowMapper;
import ru.nikolaev.chat.entity.Event;
import ru.nikolaev.chat.enums.EventType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class JdbcEventDao implements EventDao {

    private static final String SEND_EVENT_SQL =
            "INSERT INTO EVENT (owner_id,ASSIGNEE_ID,event_type_id,message,ip) VALUES(?,?,?,?,?)";

    private static final String GET_EVENT_BY_ID_SQL =
            "SELECT EVENT.*, AU_OWNER.NAME OWNER_NAME, AU_ASSIGNEE.NAME ASSIGNEE_NAME FROM EVENT INNER JOIN ALL_USER AU_OWNER on EVENT.OWNER_ID = AU_OWNER.ID LEFT JOIN ALL_USER AU_ASSIGNEE on AU_ASSIGNEE.ID = EVENT.ASSIGNEE_ID  WHERE EVENT.ID = ?";

    private static final String GET_LAST_N_EVENTS_SQL =
            "SELECT * FROM (SELECT EVENT.*, AU_OWNER.NAME OWNER_NAME, AU_ASSIGNEE.NAME ASSIGNEE_NAME FROM EVENT INNER JOIN ALL_USER AU_OWNER on EVENT.OWNER_ID = AU_OWNER.ID LEFT JOIN ALL_USER AU_ASSIGNEE on AU_ASSIGNEE.ID = EVENT.ASSIGNEE_ID ORDER BY EVENT.TIME_STAMP desc) WHERE (ROWNUM<=20) ORDER BY TIME_STAMP asc";

    private static final String GET_LAST_N_EVENTS_BY_TYPE_SQL =
            "SELECT * FROM (SELECT EVENT.*, AU_OWNER.NAME OWNER_NAME, AU_ASSIGNEE.NAME ASSIGNEE_NAME FROM EVENT INNER JOIN ALL_USER AU_OWNER on EVENT.OWNER_ID = AU_OWNER.ID LEFT JOIN ALL_USER AU_ASSIGNEE on AU_ASSIGNEE.ID = EVENT.ASSIGNEE_ID WHERE EVENT_TYPE_ID = ? ORDER BY EVENT.TIME_STAMP desc) WHERE (ROWNUM<=20) ORDER BY TIME_STAMP asc";

    private static final String GET_LAST_EVENT_BY_USER_ID_SQL =
            "SELECT * FROM (SELECT EVENT.*, AU_OWNER.NAME OWNER_NAME, AU_ASSIGNEE.NAME ASSIGNEE_NAME FROM EVENT INNER JOIN ALL_USER AU_OWNER on EVENT.OWNER_ID = AU_OWNER.ID LEFT JOIN ALL_USER AU_ASSIGNEE on AU_ASSIGNEE.ID = EVENT.ASSIGNEE_ID WHERE EVENT.OWNER_ID = ? ORDER BY EVENT.TIME_STAMP desc) WHERE (ROWNUM<=1)";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Environment env;

    @Override
    public List<Event> getLastEvents(int count) {
        List<Event> events = null;
        try {
            events = jdbcTemplate.query(GET_LAST_N_EVENTS_SQL, new EventRowMapper(), count);
        } catch (DataAccessException e) {

        }
        return events;
    }

    @Override
    public List<Event> getLastEventsByType(EventType eventType, int count) {
        List<Event> events = null;
        try {
            events = jdbcTemplate.query(GET_LAST_N_EVENTS_BY_TYPE_SQL, new EventRowMapper(), eventType.id(), count);
        } catch (DataAccessException e) {

        }
        return events;
    }

    @Override
    public Event getEventById(long id) {
        Event event = null;
        try {
            event = jdbcTemplate.queryForObject(GET_EVENT_BY_ID_SQL, new EventRowMapper(), id);
        } catch (DataAccessException e) {

        }
        return event;
    }

    @Override
    public Event sendEvent(Event event) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new SendEventPreparedStatementCreator(event), keyHolder);
        Event createdEvent = getEventById(keyHolder.getKey().intValue());
        return createdEvent;
    }

    @Override
    public Event getLastEventForUser(long userId) {
        Event event = null;
        try {
            event = jdbcTemplate.queryForObject(GET_LAST_EVENT_BY_USER_ID_SQL, new EventRowMapper(), userId);
        } catch (DataAccessException e) {

        }
        return event;
    }

    class SendEventPreparedStatementCreator implements PreparedStatementCreator {


        private Event event;

        public SendEventPreparedStatementCreator(Event event) {
            this.event = event;
        }


        @Override
        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement ps = con.prepareStatement(SEND_EVENT_SQL, new String[]{"id"});
            if (event.getOwner() != null) {
                ps.setLong(1, event.getOwner().getId());
            } else {
                ps.setLong(1, Types.NULL);
            }
            if (event.getAssignee() != null) {
                ps.setLong(2, event.getAssignee().getId());
            } else {
                ps.setLong(2, Types.NULL);
            }
            ps.setInt(3, event.getEventType().id());
            ps.setString(4, event.getMessage());
            ps.setString(5, event.getIp());
            return ps;
        }
    }
}
