package io.github.mhsscel.bookjavaapi.dto.model.user;

import io.github.mhsscel.bookjavaapi.dto.model.book.BookWithStatusDTO;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends RepresentationModel<UserDTO> {

  private Long    userId;

  @NotNull(message="username cannot be null")
  private String  username;

  @NotNull(message="name cannot be null")
  private String  name;

  @NotNull(message="role cannot be null")
  private String  role;

  private Boolean enabled;
}
