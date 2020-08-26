package de.failender.opt.slotmanager.persistance.event;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "USER_TO_EVENT")
@IdClass(UserToEventEntity.UserToEventId.class)
public class UserToEventEntity {

    @Enumerated(EnumType.ORDINAL)
    private State state;
    @Id
    private Long eventId;
    @Id
    private Long userId;


    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public static class UserToEventId implements Serializable {
        private Long eventId;
        private Long userId;

        public Long getEventId() {
            return eventId;
        }

        public void setEventId(Long eventId) {
            this.eventId = eventId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserToEventId that = (UserToEventId) o;
            return Objects.equals(eventId, that.eventId) &&
                    Objects.equals(userId, that.userId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(eventId, userId);
        }
    }

    public enum State {
        APPROVED, DECLINED, MAYBE
    }

}
