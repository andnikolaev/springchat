package ru.nikolaev.chat.dao.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.nikolaev.chat.dao.EventDao;
import ru.nikolaev.chat.dao.jdbc.mapper.EventRowMapper;
import ru.nikolaev.chat.entity.Event;
import ru.nikolaev.chat.enums.EventType;
import ru.nikolaev.chat.exception.DataBaseAccessFailedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@PropertySource("classpath:queries.properties")
public class JdbcEventDao implements EventDao {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_QUERY_GET_LAST_N_EVENTS = "SELECT * FROM (SELECT EVENT.*, AU_OWNER.NAME OWNER_NAME, AU_ASSIGNEE.NAME ASSIGNEE_NAME, AU_OWNER.ROLE_ID OWNER_ROLE FROM EVENT INNER JOIN ALL_USER AU_OWNER on EVENT.OWNER_ID = AU_OWNER.ID LEFT JOIN ALL_USER AU_ASSIGNEE on AU_ASSIGNEE.ID = EVENT.ASSIGNEE_ID ORDER BY EVENT.TIME_STAMP desc) WHERE (ROWNUM<=?) ORDER BY TIME_STAMP asc";
    private static final String SQL_QUERY_GET_LAST_N_EVENTS_BY_TYPE = "SELECT * FROM (SELECT EVENT.*, AU_OWNER.NAME OWNER_NAME, AU_ASSIGNEE.NAME ASSIGNEE_NAME, AU_OWNER.ROLE_ID OWNER_ROLE FROM EVENT INNER JOIN ALL_USER AU_OWNER on EVENT.OWNER_ID = AU_OWNER.ID LEFT JOIN ALL_USER AU_ASSIGNEE on AU_ASSIGNEE.ID = EVENT.ASSIGNEE_ID WHERE EVENT_TYPE_ID = ? ORDER BY EVENT.TIME_STAMP desc) WHERE (ROWNUM<=?) ORDER BY TIME_STAMP asc";
    private static final String SQL_QUERY_GET_EVENT_BY_ID = "SELECT EVENT.*, AU_OWNER.NAME OWNER_NAME, AU_ASSIGNEE.NAME ASSIGNEE_NAME, AU_OWNER.ROLE_ID OWNER_ROLE FROM EVENT INNER JOIN ALL_USER AU_OWNER on EVENT.OWNER_ID = AU_OWNER.ID LEFT JOIN ALL_USER AU_ASSIGNEE on AU_ASSIGNEE.ID = EVENT.ASSIGNEE_ID  WHERE EVENT.ID = ?";
    private static final String SQL_QUERY_GET_LAST_EVENT_BY_OWNER_ID = "SELECT * FROM (SELECT EVENT.*, AU_OWNER.NAME OWNER_NAME, AU_ASSIGNEE.NAME ASSIGNEE_NAME, AU_OWNER.ROLE_ID OWNER_ROLE FROM EVENT INNER JOIN ALL_USER AU_OWNER on EVENT.OWNER_ID = AU_OWNER.ID LEFT JOIN ALL_USER AU_ASSIGNEE on AU_ASSIGNEE.ID = EVENT.ASSIGNEE_ID WHERE EVENT.OWNER_ID = ? AND EVENT.EVENT_TYPE_ID BETWEEN 1 AND 4 ORDER BY EVENT.TIME_STAMP desc) WHERE (ROWNUM<=1)";
    private static final String SQL_QUERY_GET_LAST_EVENT_BY_ASSIGNEE_ID = "SELECT * FROM (SELECT EVENT.*, AU_OWNER.NAME OWNER_NAME, AU_ASSIGNEE.NAME ASSIGNEE_NAME, AU_OWNER.ROLE_ID OWNER_ROLE FROM EVENT INNER JOIN ALL_USER AU_OWNER on EVENT.OWNER_ID = AU_OWNER.ID LEFT JOIN ALL_USER AU_ASSIGNEE on AU_ASSIGNEE.ID = EVENT.ASSIGNEE_ID WHERE EVENT.ASSIGNEE_ID = ? AND EVENT.EVENT_TYPE_ID BETWEEN 5 AND 7 ORDER BY EVENT.TIME_STAMP desc) WHERE (ROWNUM<=1)";
    private static final String SQL_QUERY_SEND_EVENT = "INSERT INTO EVENT (owner_id,ASSIGNEE_ID,event_type_id,message,ip) VALUES(?,?,?,?,?)";

    @Override
    public List<Event> getLastEvents(int count) {
        log.debug("Start getLastEvents, count = {}", count);
        List<Event> events;
        try {
            events = jdbcTemplate.query(SQL_QUERY_GET_LAST_N_EVENTS, new EventRowMapper(), count);
        } catch (DataAccessException e) {
            log.error("Error getting last events", e);
            throw new DataBaseAccessFailedException();
        }
        log.trace("Events: {}", events);
        log.debug("End getLastEvents");
        return events;
    }

    @Override
    public List<Event> getLastEventsByType(EventType eventType, int count) {
        log.debug("Start getLastEventsByType, count = {}", count);
        List<Event> events;
        try {
            events = jdbcTemplate.query(SQL_QUERY_GET_LAST_N_EVENTS_BY_TYPE, new EventRowMapper(), eventType.id(), count);
        } catch (DataAccessException e) {
            log.error("Error getting last events", e);
            throw new DataBaseAccessFailedException();
        }
        log.trace("Events: {}", events);
        log.debug("End getLastEventsByType");
        return events;
    }

    @Override
    public Optional<Event> getEventById(long id) {
        log.debug("Start getEventById with id= {}", id);
        Optional<Event> event = Optional.empty();
        try {
            event = Optional.of(jdbcTemplate.queryForObject(SQL_QUERY_GET_EVENT_BY_ID, new EventRowMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            log.trace("EmptyResultDataAccessException", e);
            log.warn("Event with id = {} not exist.", id);
        } catch (DataAccessException e) {
            log.error("Event with id = {} not found.", id, e);
            throw new DataBaseAccessFailedException();
        }
        log.debug("End getEventById, event = ", event);
        return event;
    }

    @Override
    public Event sendEvent(Event event) {
        log.info("Start sendEvent = {}", event);
        Event createdEvent;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(new SendEventPreparedStatementCreator(event), keyHolder);
            createdEvent = getEventById(keyHolder.getKey().intValue()).orElse(null);
        } catch (DataAccessException e) {
            log.error("Error send event", e);
            throw new DataBaseAccessFailedException();
        }

        log.info("End sendEvent = {}", createdEvent);
        return createdEvent;
    }

    @Override
    public Optional<Event> getLastEventForUserByOwnerId(long ownerUserId) {
        log.debug("Start getLastEventForUserByOwnerId with user id = {}", ownerUserId);
        Optional<Event> event = Optional.empty();
        try {
            event = Optional.of(jdbcTemplate.queryForObject(SQL_QUERY_GET_LAST_EVENT_BY_OWNER_ID, new EventRowMapper(), ownerUserId));
        } catch (EmptyResultDataAccessException e) {
            log.debug("EmptyResultDataAccessException", e);
            log.warn("Event for owner user with id = {} not exist.", ownerUserId);
        } catch (DataAccessException e) {
            log.error("Event for owner user with id = {} not found.", ownerUserId, e);
            throw new DataBaseAccessFailedException();
        }
        log.debug("End getLastEventForUserByOwnerId with user id = {}  Event = {}", ownerUserId, event);
        return event;
    }

    @Override
    public Optional<Event> getLastEventForUserByAssigneeId(long assigneeUserId) {
        log.debug("Start getLastEventForUserByAssigneeId with user id = {}", assigneeUserId);
        Optional<Event> event = Optional.empty();
        try {
            event = Optional.of(jdbcTemplate.queryForObject(SQL_QUERY_GET_LAST_EVENT_BY_ASSIGNEE_ID, new EventRowMapper(), assigneeUserId));
        } catch (EmptyResultDataAccessException e) {
            log.debug("EmptyResultDataAccessException", e);
            log.trace("Event for assignee user with id = {} not exist.", assigneeUserId);
        } catch (DataAccessException e) {
            log.error("Event for assignee user with id = {} not found.", assigneeUserId, e);
            throw new DataBaseAccessFailedException();
        }
        log.debug("End getLastEventForUserByAssigneeId with user id = {} Event = {} ", assigneeUserId, event);
        return event;
    }

    class SendEventPreparedStatementCreator implements PreparedStatementCreator {


        private Event event;

        SendEventPreparedStatementCreator(Event event) {
            this.event = event;
        }


        @Override
        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement ps = con.prepareStatement(SQL_QUERY_SEND_EVENT, new String[]{"id"});
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
