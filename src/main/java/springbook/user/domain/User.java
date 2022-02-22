package springbook.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

  String id;
  String name;
  String password;

  Level level;
  int login;
  int recommend;

  public void upgradeLevel() {
    Level nextLevel = this.level.nextLevel();

    if (nextLevel == null) {
      throw new IllegalArgumentException(this.level + "은 업그레이드가 불가능합니다.");
    }

    this.level = nextLevel;
  }

}
