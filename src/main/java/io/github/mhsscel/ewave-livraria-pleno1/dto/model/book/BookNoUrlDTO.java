package io.github.mhsscel.bookjavaapi.dto.model.book;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BookNoUrlDTO extends RepresentationModel<BookNoUrlDTO> {

  private Long id;

  @NotNull(message="title cannot be null")
  private String title;

  @NotNull(message="genre cannot be null")
  private String genre;

  @NotNull(message="author cannot be null")
  private String author;

  @NotNull(message="rating cannot be null")
  private Double rating;
}
