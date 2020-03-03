package com.board.model;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Notice {
    private int id;
    @Size(min = 10, max = 200)
    private String description;
    private User user;
    private NoticeStatus status;
    private LocalDateTime created;
    private List<Type> types;

    public Notice() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public NoticeStatus getStatus() {
        return status;
    }

    public void setStatus(NoticeStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notice notice = (Notice) o;
        return id == notice.id &&
                Objects.equals(description, notice.description) &&
                Objects.equals(user, notice.user) &&
                status == notice.status &&
                Objects.equals(created, notice.created) &&
                Objects.equals(types, notice.types);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, user, status, created, types);
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", status=" + status +
                ", created=" + created +
                ", types=" + types +
                '}';
    }
}
