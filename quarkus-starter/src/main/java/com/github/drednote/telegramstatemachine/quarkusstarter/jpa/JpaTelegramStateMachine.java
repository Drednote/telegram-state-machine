package com.github.drednote.telegramstatemachine.quarkusstarter.jpa;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class JpaTelegramStateMachine extends PanacheEntityBase {

  @Id
  @Column(name = "id", nullable = false)
  private String id;
  private String state;
  private byte[] context;

  public JpaTelegramStateMachine(String id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    JpaTelegramStateMachine that = (JpaTelegramStateMachine) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
