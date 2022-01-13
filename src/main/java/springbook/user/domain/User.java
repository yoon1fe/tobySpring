package springbook.user.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

  String id;
  String name;
  String password;

}
