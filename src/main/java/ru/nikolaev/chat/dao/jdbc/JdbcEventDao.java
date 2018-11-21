package ru.nikolaev.chat.dao.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
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
@Slf4j
@PropertySource("classpath:queries.properties")
public class JdbcEventDao implements EventDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Environment env;

    @Override
    public List<Event> getLastEvents(int count) {
        log.debug("Start getLastEvents, count = " + count);
        List<Event> events = null;
        try {
            String sqlQuery = env.getProperty("event.get.last.n");
            events = jdbcTemplate.query(sqlQuery, new EventRowMapper(), count);
        } catch (DataAccessException e) {
            log.warn("Error getting last events");
        }
        log.trace("Events:" + events);
        log.debug("End getLastEvents");
        return events;
    }

    @Override
    public List<Event> getLastEventsByType(EventType eventType, int count) {
        log.debug("Start getLastEventsByType, count = " + count);
        List<Event> events = null;
        try {
            String sqlQuery = env.getProperty("event.get.last.n.by.type");
            events = jdbcTemplate.query(sqlQuery, new EventRowMapper(), eventType.id(), count);
        } catch (DataAccessException e) {
            log.warn("Error getting last events");
        }
        log.trace("Events:" + events);
        log.debug("End getLastEventsByType");
        return events;
    }

    @Override
    public Event getEventById(long id) {
        log.debug("Start getEventById with id= " + id);
        Event event = null;
        try {
            String sqlQuery = env.getProperty("event.get.by.id");
            event = jdbcTemplate.queryForObject(sqlQuery, new EventRowMapper(), id);
        } catch (DataAccessException e) {
            log.warn("Event with id " + id + " not found.");
        }
        log.debug("End getEventById, result " + event);
        return event;
    }

    @Override
    public Event sendEvent(Event event) {
        log.info("Start sendEvent");
        log.debug("Event: " + event);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new SendEventPreparedStatementCreator(event), keyHolder);
        Event createdEvent = getEventById(keyHolder.getKey().intValue());

        log.debug("Created event: " + createdEvent);
        log.info("End sendEvent");
        return createdEvent;
    }

    @Override
    public Event getLastEventForUserByOwnerId(long ownerUserId) {
        log.debug("Start getLastEventForUserByOwnerId with user id= " + ownerUserId);
        Event event = null;
        try {
            String sqlQuery = env.getProperty("event.get.last.by.owner.id");
            event = jdbcTemplate.queryForObject(sqlQuery, new EventRowMapper(), ownerUserId);
        } catch (DataAccessException e) {
            log.debug("Event for owner user with id " + ownerUserId + " not found.");
        }
        log.debug("End getLastEventForUserByOwnerId with user id = " + ownerUserId + " Event: " + event);
        return event;
    }

    @Override
    public Event getLastEventForUserByAssigneeId(long assigneeUserId) {
        log.debug("Start getLastEventForUserByAssigneeId with user id= " + assigneeUserId);
        Event event = null;
        try {
            String sqlQuery = env.getProperty("event.get.last.by.assignee.id");
            event = jdbcTemplate.queryForObject(sqlQuery, new EventRowMapper(), assigneeUserId);
        } catch (DataAccessException e) {
            log.debug("Event for assignee user with id " + assigneeUserId + " not found.");
        }
        log.debug("End getLastEventForUserByAssigneeId with user id= " + assigneeUserId + " Event: " + event);
        return event;
    }

    class SendEventPreparedStatementCreator implements PreparedStatementCreator {


        private Event event;

        SendEventPreparedStatementCreator(Event event) {
            this.event = event;
        }


        @Override
        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            String sqlQuery = env.getProperty("event.send");
            PreparedStatement ps = con.prepareStatement(sqlQuery, new String[]{"id"});
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
